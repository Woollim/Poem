package root.hash_tm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.Model.PoemIndexInfoModel;
import root.hash_tm.R;
import root.hash_tm.adapter.PoemtryIndexAdapter;
import root.hash_tm.connect.RetrofitClass;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 9. 4..
 */

public class PoemtryIndexActivity extends BaseActivity {

    TextView titleText, poemCountText, writerText;

    PoemtryIndexAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poemtry_index);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String bookId = intent.getStringExtra("bookId");


        titleText = (TextView)findViewById(R.id.titleText);
        poemCountText = (TextView)findViewById(R.id.poemCountText);
        writerText = (TextView)findViewById(R.id.writerText);

        getData(bookId);
        adapter = new PoemtryIndexAdapter(this, bookId);
        recyclerView.setAdapter(adapter);
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
                            adapter.setBookTitleText(response.body().getTitle());
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
