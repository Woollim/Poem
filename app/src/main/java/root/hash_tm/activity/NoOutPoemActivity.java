package root.hash_tm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.Model.IntentModel;
import root.hash_tm.Model.PoemModel;
import root.hash_tm.R;
import root.hash_tm.connect.RetrofitClass;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 8. 29..
 */

public class NoOutPoemActivity extends BaseActivity {

    ImageButton removeButton, editButton;
    TextView titleText, contentText, writerText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_no_out);


        Intent intent = getIntent();
        final String poemId = intent.getStringExtra("poemId");


        removeButton = (ImageButton)findViewById(R.id.removeButton);
        editButton = (ImageButton)findViewById(R.id.editButton);
        titleText = (TextView)findViewById(R.id.titleText);
        contentText = (TextView)findViewById(R.id.contentText);
        writerText = (TextView)findViewById(R.id.writerText);

        removeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    RetrofitClass.getInstance().apiInterface
                            .removePoem(getPreferences().getString("cookie", ""), poemId)
                            .enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.code() == 200){
                                        showSnack("시를 삭제했습니다.");
                                    }else{
                                        showSnack("시를 삭제하지 못했습니다.");
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                    }
                }
        );

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!title.isEmpty()){
                    ArrayList<IntentModel> data = new ArrayList<>();
                    data.add(new IntentModel("title", title));
                    data.add(new IntentModel("content", content));
                    data.add(new IntentModel("poemId", poemId));
                    data.add(new IntentModel("aligment", aligment + ""));
                    goNextActivity(WritePoemActivity.class, data);
                }else{
                    showSnack("잠시만 기다려 주세요");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        final String poemId = intent.getStringExtra("poemId");
        String cookie = intent.getStringExtra("cookie");
        getData(poemId, cookie);
    }

    private String title = "", content = "";
    private int aligment = 0;

    private void getData(String poemId, String cookie){
        RetrofitClass.getInstance().apiInterface.getPoem(poemId, cookie)
                .enqueue(new Callback<PoemModel>() {
                    @Override
                    public void onResponse(Call<PoemModel> call, Response<PoemModel> response) {
                        if(response.code() == 200){
                            setData(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<PoemModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void setData(PoemModel data){
        titleText.setText(data.getTitle());
        contentText.setText(data.getContent());
        writerText.setText(data.getWriter());

        title = data.getTitle();
        content = data.getContent();
        aligment = data.getAlignment();

        switch (data.getAlignment()){
            case 3:
                contentText.setGravity(Gravity.LEFT);
                break;
            case 2:
                contentText.setGravity(Gravity.CENTER);
                break;
            case 1:
                contentText.setGravity(Gravity.RIGHT);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
