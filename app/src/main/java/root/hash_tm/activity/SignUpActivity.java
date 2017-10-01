package root.hash_tm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
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

public class SignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText idEdit, pwEdit, nickEdit;
        idEdit = (EditText)findViewById(R.id.idEdit);
        pwEdit = (EditText)findViewById(R.id.pwEdit);
        nickEdit = (EditText)findViewById(R.id.nickEdit);

        findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getText(idEdit).isEmpty() || getText(pwEdit).isEmpty() || getText(nickEdit).isEmpty()){

                }else{
                    RetrofitClass.getInstance().apiInterface.signUp(getText(idEdit), getText(pwEdit), getText(nickEdit))
                            .enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.code() == 201){
                                        Log.d("xxx", "cookie : " + response.headers().get("Poem-Session-Key"));
                                        saveData("cookie", response.headers().get("Poem-Session-Key"));
                                        showToast("환영합니다.");
                                        goNextActivity(MainActivity.class, null);
                                        finish();
                                    }else if(response.code() == 400){
                                        showToast("회원가입에 실패하였습니다.");
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
    }

    private String getText(EditText editText){
        return editText.getText().toString();
    }
}
