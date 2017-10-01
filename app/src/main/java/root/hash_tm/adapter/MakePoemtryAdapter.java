package root.hash_tm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import root.hash_tm.Model.PoemListModel;
import root.hash_tm.R;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 10. 1..
 */

public class MakePoemtryAdapter extends RecyclerView.Adapter<MakePoemtryAdapter.MakePoemViewHolder> {

    private ArrayList<PoemListModel> data = new ArrayList<>();

    public ArrayList<PoemListModel> getData() {
        return data;
    }

    private BaseActivity activity;

    public MakePoemtryAdapter(BaseActivity activity) {
        this.activity = activity;
    }

    public void setData(ArrayList<PoemListModel> data) {
        this.data = data;
    }

    @Override
    public MakePoemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_mypage_linear, parent, false);
        return new MakePoemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MakePoemViewHolder holder, int position) {
        holder.titleText.setText(data.get(position).getTitle());
        holder.contentText.setText(data.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MakePoemViewHolder extends RecyclerView.ViewHolder{
        TextView titleText, contentText;

        public MakePoemViewHolder(final View itemView) {
            super(itemView);
            titleText = (TextView)itemView.findViewById(R.id.titleText);
            contentText = (TextView)itemView.findViewById(R.id.contentText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RelativeLayout rootView = (RelativeLayout)view;
                    View checkView = new View(activity);
                    checkView.setBackgroundColor(R.color.colorAlphaBlack);
                    rootView.addView(checkView);
                }
            });
        }
    }
}
