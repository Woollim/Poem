package root.hash_tm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.Model.IntentModel;
import root.hash_tm.Model.PoemListModel;
import root.hash_tm.R;
import root.hash_tm.adapter.SelectPoemtryAdapter;
import root.hash_tm.connect.RetrofitClass;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 10. 1..
 */

public class SelectPoemtryActivity extends BaseActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    List<PoemListModel> data;

    SelectPoemtryAdapter adapter;

    EditText titleEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_poem);

        getData();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        titleEdit = (EditText)findViewById(R.id.titleEdit);

        FloatingActionButton sendButton = (FloatingActionButton)findViewById(R.id.sendButton);

        sendButton.setOnClickListener(this);

        adapter = new SelectPoemtryAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onClick(View view) {
        if(adapter.getSelectDataSize() >= 3 && !titleEdit.getText().toString().isEmpty()){
            RetrofitClass.getInstance().apiInterface
                    .uploadPoemtry(getPreferences().getString("cookie", ""), titleEdit.getText().toString(), getArrayData())
                    .enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.code() == 200){
                                showSnack("시집을 구성할 시를 보냈습니다.");
                                ArrayList<IntentModel> data = new ArrayList<>();
                                data.add(new IntentModel("bookId", response.body().get("id").getAsInt() + ""));
                                goNextActivity(SelectBookCoverActivity.class, data);
                                finish();
                            }else{
                                showSnack("시를 보내지 못했습니다.");
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }else{
            showSnack("정보를 다 입력하세요");
        }
    }

    private JSONArray getArrayData(){
        Set<Integer> tempData = adapter.getSelectData();
        JSONArray array = new JSONArray();

        for(int i : tempData){
            array.put(i);
        }

        return array;
    }

    private void getData(){
        RetrofitClass.getInstance().apiInterface
                .getMyPoem(getPreferences().getString("cookie",""))
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.code() == 200){
                            if(response.code() == 200){
                                JsonElement temp = response.body().get("data");
                                Gson gson = new Gson();
                                PoemListModel[] arrData = gson.fromJson(temp, PoemListModel[].class);
                                data = Arrays.asList(arrData);
                                adapter.setData(data);
                                adapter.notifyDataSetChanged();
                            }else{
                                showSnack("데이터를 불러오지 못했습니다.");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

}
