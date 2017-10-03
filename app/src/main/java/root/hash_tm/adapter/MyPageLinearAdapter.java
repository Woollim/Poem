package root.hash_tm.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.Model.IntentModel;
import root.hash_tm.Model.PoemListModel;
import root.hash_tm.R;
import root.hash_tm.activity.NoOutPoemActivity;
import root.hash_tm.connect.RetrofitClass;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 9. 24..
 */

public class MyPageLinearAdapter extends RecyclerView.Adapter {

    private List<PoemListModel> data = new ArrayList<>();
    private BaseActivity activity;
    private String cookie;

    public MyPageLinearAdapter(String title, String cookie, BaseActivity activity) {
        this.activity = activity;
        Log.d("xxx", "hello world");
        this.cookie = cookie;
        if(cookie.isEmpty()){

        }else{
            if(title.equals("시 원고")){
                RetrofitClass.getInstance().apiInterface
                        .getMyPoem(cookie)
                        .enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if(response.code() == 200){
                                    if(response.code() == 200){
                                        JsonElement temp = response.body().get("data");
                                        Gson gson = new Gson();
                                        PoemListModel[] arrData = gson.fromJson(temp, PoemListModel[].class);
                                        data = Arrays.asList(arrData);
                                        notifyDataSetChanged();
                                    }else{

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
            }else{

            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_mypage_linear, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyPageLinearAdapter.MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.titleText.setText(data.get(position).getTitle());
        myViewHolder.contentText.setText(data.get(position).getContent());
        myViewHolder.id = data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleText, contentText;
        int id;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView)itemView.findViewById(R.id.titleText);
            contentText = (TextView)itemView.findViewById(R.id.contentText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<IntentModel> data = new ArrayList<>();
                    data.add(new IntentModel("poemId", id + ""));
                    data.add(new IntentModel("cookie", cookie));
                    activity.goNextActivity(NoOutPoemActivity.class, data);
                    activity.finish();
                }
            });
        }
    }
}
