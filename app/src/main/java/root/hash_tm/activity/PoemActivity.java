package root.hash_tm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.Manager.TTSManager;
import root.hash_tm.Model.PoemIndexModel;
import root.hash_tm.Model.PoemIndexSendData;
import root.hash_tm.Model.PoemModel;
import root.hash_tm.R;
import root.hash_tm.connect.RetrofitClass;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 8. 29..
 */

public class PoemActivity extends BaseActivity {

    ImageButton actionButton, beforeButton, afterButton, likeButton, backButton;
    TextView titleText, contentText, writerText, bookTitle, countText;

    TTSManager ttsManager;

    int position = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem);

        ttsManager = new TTSManager(this);

        Intent intent = getIntent();

        String booktitleText = intent.getStringExtra("bookTitle");
        position = intent.getIntExtra("index", 0);
        final List<PoemIndexModel> data = ((PoemIndexSendData)intent.getSerializableExtra("data")).getIdArray();
        final String cookie = getPreferences().getString("cookie", "");

        bookTitle = (TextView)findViewById(R.id.bookTitle);
        bookTitle.setText(booktitleText);

        actionButton = (ImageButton)findViewById(R.id.actionButton);
        beforeButton = (ImageButton)findViewById(R.id.beforeButton);
        afterButton = (ImageButton)findViewById(R.id.afterButton);
        likeButton = (ImageButton)findViewById(R.id.likeButton);
        backButton = (ImageButton)findViewById(R.id.backButton);

        titleText = (TextView)findViewById(R.id.titleText);
        contentText = (TextView)findViewById(R.id.contentText);
        writerText = (TextView)findViewById(R.id.writerText);
        countText = (TextView)findViewById(R.id.countText);

        getData(data.get(position).getId(), cookie);

        actionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    ttsManager.readTTS(contentText.getText().toString());
                    }
                }
        );

        countText.setText(position + 1 + "/" + data.size());

        afterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position >= data.size() - 1){
                    showSnack("마지막 시입니다.");
                }else{
                    getData(data.get(++position).getId(), cookie);
                    countText.setText(position + 1 + "/" + data.size());
                }
            }
        });

        beforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(0 >= position){
                    showSnack("첫번째 시입니다.");
                }else{
                    getData(data.get(--position).getId(), cookie);
                    countText.setText(position + 1 + "/" + data.size());
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void getData(String poemId, String cookie){
        RetrofitClass.getInstance().apiInterface.getPoem(poemId, cookie)
                .enqueue(new Callback<PoemModel>() {
                    @Override
                    public void onResponse(Call<PoemModel> call, Response<PoemModel> response) {
                        if(response.code() == 200){
                            titleText.setText(response.body().getTitle());
                            contentText.setText(response.body().getContent());
                            writerText.setText(response.body().getWriter());
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
    protected void onDestroy() {
        super.onDestroy();
        ttsManager.shutDownTTS();
    }
}
