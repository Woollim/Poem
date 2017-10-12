package root.hash_tm.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.R;
import root.hash_tm.Connect.RetrofitClass;
import root.hash_tm.Util.BaseActivity;

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

        if(!getPreferences().getString("cookie", "").isEmpty()){
            goNextActivity(MainActivity.class, null);
            finish();
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getText(idEdit).isEmpty() && getText(pwEdit).isEmpty()){
                    showSnack("아이디와 비빌번호를 입력하세요");
                }else{
                    RetrofitClass.getInstance().apiInterface.signIn(getText(idEdit), getText(pwEdit))
                            .enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.code() == 200){
                                        Log.d("xxx", "cookie : " + response.headers().get("Poem-Session-Key"));
                                        saveData("cookie", response.headers().get("Poem-Session-Key"));
                                        goNextActivity(MainActivity.class, null);
                                        finish();
                                    }else if(response.code() == 400){
                                        showSnack("로그인에 실패하였습니다.");
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
