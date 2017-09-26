package root.hash_tm.adapter;

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
import root.hash_tm.Model.IntentModel;
import root.hash_tm.Model.PoemIndexModel;
import root.hash_tm.R;
import root.hash_tm.activity.PoemActivity;
import root.hash_tm.connect.RetrofitClass;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 9. 24..
 */

public class PoemtryIndexAdapter extends RecyclerView.Adapter {

    private BaseActivity activity;

    private List<PoemIndexModel> data = new ArrayList<>();

    public PoemtryIndexAdapter(final BaseActivity activity, String bookId) {
        this.activity = activity;
        RetrofitClass.getInstance().apiInterface
                .getIndexContent(bookId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.code() == 200){
                    JsonElement tempData = response.body().get("data");
                    Gson gson = new Gson();
                    data = Arrays.asList(gson.fromJson(tempData, PoemIndexModel[].class));
                    notifyDataSetChanged();
                }else{

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_poemtry_index, parent, false);
        return new PoemtryIndexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PoemtryIndexViewHolder poemtryIndexViewHolder = (PoemtryIndexViewHolder)holder;
        poemtryIndexViewHolder.titleText.setText(data.get(position).getTitle());
        poemtryIndexViewHolder.id = data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PoemtryIndexViewHolder extends RecyclerView.ViewHolder{
        TextView titleText;
        String id;

        public PoemtryIndexViewHolder(final View itemView) {
            super(itemView);

            titleText = (TextView)itemView.findViewById(R.id.titleText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<IntentModel> sendData = new ArrayList<IntentModel>();
                    sendData.add(new IntentModel("poemId", id));
                    activity.goNextActivity(PoemActivity.class, sendData);
                    activity.finish();
                }
            });
        }
    }
}
