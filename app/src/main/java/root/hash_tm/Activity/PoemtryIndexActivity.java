package root.hash_tm.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.Adapter.PoemtryIndexAdapter;
import root.hash_tm.Connect.RetrofitClass;
import root.hash_tm.Model.PoemIndexInfoModel;
import root.hash_tm.R;
import root.hash_tm.Util.BaseActivity;

/**
 * Created by root1 on 2017. 9. 4..
 */

public class PoemtryIndexActivity extends BaseActivity {

    TextView titleText, poemCountText, writerText;
    ImageButton reportButton;

    PoemtryIndexAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poemtry_index);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        final String bookId = intent.getStringExtra("bookId");


        reportButton = (ImageButton)findViewById(R.id.reportButton);
        titleText = (TextView)findViewById(R.id.titleText);
        poemCountText = (TextView)findViewById(R.id.poemCountText);
        writerText = (TextView)findViewById(R.id.writerText);

        getData(bookId);
        adapter = new PoemtryIndexAdapter(this, bookId);
        recyclerView.setAdapter(adapter);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClass.getInstance().apiInterface
                        .report(getPreferences().getString("cookie",""), bookId)
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.code() == 200){
                                    showSnack("신고 완료하였습니다.");
                                }else{
                                    showSnack("이미 신고 된 시집입니다.");
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
            }
        });
    }

    private void getData(String bookId){
        RetrofitClass.getInstance().apiInterface.getIndexInfo(bookId)
                .enqueue(new Callback<PoemIndexInfoModel>() {
                    @Override
                    public void onResponse(Call<PoemIndexInfoModel> call, Response<PoemIndexInfoModel> response) {
                        if(response.code() == 200){
                            titleText.setText(response.body().getTitle());
                            writerText.setText(response.body().getWriter());
                            poemCountText.setText(response.body().getPoems());
                            adapter.setBookTitleText(response.body().getTitle(), response.body().getWriter());
                        }else{
                            showSnack("데이터를 로드하는데 실패하였습니다.");
                        }
                    }

                    @Override
                    public void onFailure(Call<PoemIndexInfoModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

}
