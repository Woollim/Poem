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
import root.hash_tm.Manager.TTSManager;
import root.hash_tm.Model.IntentModel;
import root.hash_tm.Model.PoemModel;
import root.hash_tm.R;
import root.hash_tm.connect.RetrofitClass;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 8. 29..
 */

public class NoOutPoemActivity extends BaseActivity {

    ImageButton speakButton, editButton;
    TextView titleText, contentText, writerText;

    TTSManager ttsManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_no_out);


        Intent intent = getIntent();
        final String poemId = intent.getStringExtra("poemId");

        ttsManager = new TTSManager(this);

        speakButton = (ImageButton)findViewById(R.id.speakButton);
        editButton = (ImageButton)findViewById(R.id.editButton);
        titleText = (TextView)findViewById(R.id.titleText);
        contentText = (TextView)findViewById(R.id.contentText);
        writerText = (TextView)findViewById(R.id.writerText);

        speakButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ttsManager.readTTS(titleText.getText().toString());
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
                            titleText.setText(response.body().getTitle());
                            contentText.setText(response.body().getContent());
                            writerText.setText(response.body().getWriter());

                            title = response.body().getTitle();
                            content = response.body().getContent();
                            aligment = response.body().getAlignment();

                            switch (response.body().getAlignment()){
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
                    }

                    @Override
                    public void onFailure(Call<PoemModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        goNextActivity(MyPageActivity.class, null);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ttsManager.shutDownTTS();
    }
}
