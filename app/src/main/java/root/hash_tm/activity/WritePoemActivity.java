package root.hash_tm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    private boolean isEdit = false;

    private String poemId;

    @Override
    public void finishActivity(int requestCode) {
        super.finishActivity(requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_poem);

        contentEdit = (EditText)findViewById(R.id.contentEdit);
        titleEdit = (EditText)findViewById(R.id.titleEdit);

        backButton = (ImageButton)findViewById(R.id.backButton);
        sendButton = (ImageButton)findViewById(R.id.sendButton);

        setGravityButton = (FloatingActionButton)findViewById(R.id.setGravityButton);

        Intent intent = getIntent();

        if(!(intent.getStringExtra("title") == null)){
            isEdit = true;
            Log.e("xxx", "" + intent.getStringExtra("title"));
            titleEdit.setText(intent.getStringExtra("title"));
            contentEdit.setText(intent.getStringExtra("content"));
            gravityInt = Integer.parseInt(intent.getStringExtra("aligment"));
            poemId = intent.getStringExtra("poemId");
            setGravity();
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBack();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty()){
                    showToast("값을 다 입력하세요.");
                }else{
                    if(isEdit){
                        RetrofitClass.getInstance()
                                .apiInterface
                                .editPoem(getPreferences().getString("cookie",""), Integer.parseInt(poemId), getText(titleEdit), getText(contentEdit), gravityInt)
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.code() == 201){
                                            showToast("시 등록을 성공하였습니다.");
                                            finish();
                                        }else{
                                            showToast("시 등록을 실패하였습니다.");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });

                    }else{
                        RetrofitClass.getInstance()
                                .apiInterface
                                .uploadPoem(getPreferences().getString("cookie",""),
                                        getText(titleEdit), getText(contentEdit), gravityInt)
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.code() == 200){
                                            showToast("시를 편집했습니다.");
                                            finish();
                                        }else{
                                            showToast("시 등록을 실패하였습니다.");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                    }
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

    @Override
    public void onBackPressed() {
        pressBack();
    }

    private void pressBack(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("시 편집이 종료됩니다.").setMessage("기존에 편집한 내용은 사라집니다.\n그래도 괜찮으시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.create().cancel();
                finish();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.create().cancel();
            }
        });
        builder.create().show();
    }

    private void setGravity(){
        switch (gravityInt){
            case 1:
                contentEdit.setGravity(Gravity.RIGHT);
                break;
            case 2:
                contentEdit.setGravity(Gravity.CENTER_HORIZONTAL);
                break;
            case 3:
                contentEdit.setGravity(Gravity.LEFT);
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
