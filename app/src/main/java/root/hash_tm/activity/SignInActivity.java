package root.hash_tm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import root.hash_tm.Model.IntentModel;
import root.hash_tm.R;
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
                    ArrayList<IntentModel> data = new ArrayList<IntentModel>();
                    data.add(new IntentModel("id", getText(idEdit)));
                    data.add(new IntentModel("pw", getText(pwEdit)));
                    goNextActivity(MainActivity.class, null);
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
