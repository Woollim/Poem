package root.hash_tm.adapter;

import android.content.Intent;
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

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.Model.IntentModel;
import root.hash_tm.Model.PoemListModel;
import root.hash_tm.Model.PoemModel;
import root.hash_tm.R;
import root.hash_tm.activity.MyPageActivity;
import root.hash_tm.activity.NoOutPoemActivity;
import root.hash_tm.activity.SharePoemActivity;
import root.hash_tm.connect.RetrofitClass;

/**
 * Created by root1 on 2017. 9. 24..
 */

public class MyPageLinearAdapter extends RecyclerView.Adapter {

    private List<PoemListModel> data = new ArrayList<>();
    private List<PoemModel> shareData = new ArrayList<>();
    private MyPageActivity activity;
    private String cookie;

    private boolean isShare = false;

    public MyPageLinearAdapter(String title, String cookie, MyPageActivity activity) {
        this.activity = activity;
        this.cookie = cookie;
        if(cookie.isEmpty()){

        }else{
            if(title.equals("시 원고")){
                isShare = false;
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
                isShare = true;

                Realm.init(activity);
                Realm realm = Realm.getDefaultInstance();
                RealmResults<PoemModel> data = realm.where(PoemModel.class).findAll();
                List<PoemModel> data1 = new ArrayList<>();

                for(int i = 0; i <data.size(); i ++){
                    Log.e("i", data.get(i).toString());
                    data1.add(data.get(i));
                }

                this.shareData = data1;
                notifyDataSetChanged();
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
        if(isShare){
            myViewHolder.titleText.setText(shareData.get(position).getTitle());
            myViewHolder.contentText.setText(shareData.get(position).getContent());
            myViewHolder.data = shareData.get(position);
        }else{
            myViewHolder.titleText.setText(data.get(position).getTitle());
            myViewHolder.contentText.setText(data.get(position).getContent());
            myViewHolder.id = data.get(position).getId();
        }
    }

    @Override
    public int getItemCount() {
        if(isShare){
            return shareData.size();
        }else{
            return data.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleText, contentText;
        PoemModel data;
        int id;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView)itemView.findViewById(R.id.titleText);
            contentText = (TextView)itemView.findViewById(R.id.contentText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isShare){
                        Intent intent = new Intent(activity, SharePoemActivity.class);
                        intent.putExtra("title", data.getTitle());
                        intent.putExtra("content", data.getContent());
                        intent.putExtra("writer", data.getWriter());
                        intent.putExtra("alignment", data.getAlignment());
                        activity.startActivity(intent);
                    }else{
                        ArrayList<IntentModel> data = new ArrayList<>();
                        data.add(new IntentModel("poemId", id + ""));
                        data.add(new IntentModel("cookie", cookie));
                        activity.goNextActivity(NoOutPoemActivity.class, data);
                    }
                }
            });
        }
    }
}
