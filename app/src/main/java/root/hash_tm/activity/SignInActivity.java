package root.hash_tm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.R;
import root.hash_tm.connect.RetrofitClass;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 9. 24..
 */

public class SignInActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        final EditText idEdit = (EditText)findViewById(R.id.idEdit);
        final EditText pwEdit = (EditText)findViewById(R.id.pwEdit);

        Button signInButton = (Button)findViewById(R.id.signInButton);
        Button signUpButton = (Button)findViewById(R.id.signUpButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getText(idEdit).isEmpty() && getText(pwEdit).isEmpty()){
                    showToast("아이디와 비빌번호를 입력하세요");
                }else{
                    RetrofitClass.getInstance().apiInterface.signIn(getText(idEdit), getText(pwEdit))
                            .enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.code() == 200){
                                        saveData("cookie", response.headers().get("Set-Cookie"));
                                        goNextActivity(MainActivity.class, null);
                                    }else if(response.code() == 400){
                                        showToast("로그인에 실패하였습니다.");
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNextActivity(SignUpActivity.class, null);
            }
        });
    }

    private String getText(EditText editText){
        return editText.getText().toString();
    }
}
