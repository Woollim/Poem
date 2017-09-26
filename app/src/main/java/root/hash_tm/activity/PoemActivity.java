package root.hash_tm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.Model.PoemModel;
import root.hash_tm.R;
import root.hash_tm.connect.RetrofitClass;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 8. 29..
 */

public class PoemActivity extends BaseActivity {

    ImageButton actionButton;
    TextView titleText, contentText, writerText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem);

        Intent intent = getIntent();
        String poemId = intent.getStringExtra("poemId");

        actionButton = (ImageButton)findViewById(R.id.actionButton);
        titleText = (TextView)findViewById(R.id.titleText);
        contentText = (TextView)findViewById(R.id.contentText);
        writerText = (TextView)findViewById(R.id.writerText);
        getData(poemId);

    }

    private void getData(String poemId){
        RetrofitClass.getInstance().apiInterface.getPoem(poemId)
                .enqueue(new Callback<PoemModel>() {
                    @Override
                    public void onResponse(Call<PoemModel> call, Response<PoemModel> response) {
                        if(response.code() == 200){
                            titleText.setText(response.body().getTitle());
                            contentText.setText(response.body().getContent());
                            writerText.setText(response.body().getWriter());
                            switch (response.body().getAlignment()){
                                case 1:
                                    contentText.setGravity(Gravity.LEFT);
                                    break;
                                case 2:
                                    contentText.setGravity(Gravity.CENTER);
                                    break;
                                case 3:
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
}
