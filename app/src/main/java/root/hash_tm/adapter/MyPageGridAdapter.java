package root.hash_tm.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import root.hash_tm.Model.BookModel;
import root.hash_tm.R;
import root.hash_tm.connect.RetrofitClass;

/**
 * Created by root1 on 2017. 9. 24..
 */

public class MyPageGridAdapter extends RecyclerView.Adapter {

    private String cookie;private List<BookModel> data = new ArrayList<>();

    public MyPageGridAdapter(String title, String cookie) {
        if(cookie.isEmpty()){

        }

        if(title.equals("출간 완료 시집")){
            RetrofitClass.getInstance().apiInterface
                    .getMyPoemtry(cookie).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.code() == 200){
                        JsonElement tempData = response.body().get("data");
                        Gson gson = new Gson();
                        data = Arrays.asList(gson.fromJson(tempData, BookModel[].class));
                        notifyDataSetChanged();
                    }else{

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }else{
            RetrofitClass.getInstance().apiInterface
                    .getHeartPomtry(cookie).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_mypage_grid, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder)holder;
        myViewHolder.titleText.setText(data.get(position).getTitle());
        myViewHolder.writerText.setText(data.get(position).getWriter());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CardView bookCard;
        TextView titleText, writerText;

        public MyViewHolder(View itemView) {
            super(itemView);
            bookCard = (CardView)itemView.findViewById(R.id.bookCard);
            titleText = (TextView)itemView.findViewById(R.id.titleText);
            writerText = (TextView)itemView.findViewById(R.id.writerText);
        }
    }
}
