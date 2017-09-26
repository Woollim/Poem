package root.hash_tm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import root.hash_tm.R;
import root.hash_tm.connect.RetrofitClass;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 9. 4..
 */

public class WritePoemActivity extends BaseActivity {

    EditText contentEdit, titleEdit;
    ImageView backButton, sendButton;
    FloatingActionButton setGravityButton;

    private int gravityInt = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_poem);

        contentEdit = (EditText)findViewById(R.id.contentEdit);
        titleEdit = (EditText)findViewById(R.id.titleEdit);

        backButton = (ImageButton)findViewById(R.id.backButton);
        sendButton = (ImageButton)findViewById(R.id.sendButton);

        setGravityButton = (FloatingActionButton)findViewById(R.id.setGravityButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty()){
                    showToast("값을 다 입력하세요.");
                }else{
                    RetrofitClass.getInstance()
                            .apiInterface
                            .uploadPoem(getPreferences().getString("cookie",""),
                                    getText(titleEdit), getText(contentEdit), gravityInt);
                }
            }
        });

        setGravityButton
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(gravityInt > 2){
                            gravityInt = 1;
                        }else{
                            gravityInt++;
                        }

                        setGravity();
                    }
                });
    }

    private void setGravity(){
        switch (gravityInt){
            case 1:
                contentEdit.setGravity(Gravity.LEFT);
                break;
            case 2:
                contentEdit.setGravity(Gravity.CENTER);
                break;
            case 3:
                contentEdit.setGravity(Gravity.RIGHT);
                break;
            default:
                break;
        }
    }

    private String getText(EditText editText){
        return editText.getText().toString();
    }

    private boolean isEmpty(){
        if(titleEdit.getText().toString().isEmpty() || contentEdit.getText().toString().isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}
