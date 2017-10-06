package root.hash_tm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.Model.PoemListModel;
import root.hash_tm.R;
import root.hash_tm.activity.BluetoothShareActivity;
import root.hash_tm.connect.RetrofitClass;

/**
 * Created by root1 on 2017. 10. 6..
 */

public class PoemListAdapter extends RecyclerView.Adapter<PoemListAdapter.PoemViewHolder> {

    private List<PoemListModel> data = new ArrayList<>();
    private BluetoothShareActivity activity;
    private String cookie;

    public PoemListAdapter(String cookie, final BluetoothShareActivity activity){
        this.activity = activity;
        this.cookie = cookie;

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
                                activity.showSnack("보낼 시가 없습니다.");
                                try {
                                    activity.outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
    }

    @Override
    public PoemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_bluetooth_share_device,parent,false);
        return new PoemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PoemViewHolder holder, int position) {
        holder.nameText.setText(data.get(position).getTitle());
        holder.poemId = data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class PoemViewHolder extends RecyclerView.ViewHolder{
        TextView nameText;
        int poemId;


        public PoemViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView)itemView.findViewById(R.id.nameText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sendStr = cookie + "=" + poemId;
                    activity.sendData(sendStr);

                }
            });
        }
    }
}
