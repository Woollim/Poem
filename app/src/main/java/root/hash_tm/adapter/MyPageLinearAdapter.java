package root.hash_tm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import root.hash_tm.R;

/**
 * Created by root1 on 2017. 9. 24..
 */

public class MyPageLinearAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_mypage_linear, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleText, contentText;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView)itemView.findViewById(R.id.titleText);
            contentText = (TextView)itemView.findViewById(R.id.contentText);
        }
    }
}
