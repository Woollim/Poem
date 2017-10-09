package root.hash_tm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import root.hash_tm.Model.PoemListModel;
import root.hash_tm.R;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 10. 1..
 */

public class SelectPoemtryAdapter extends RecyclerView.Adapter<SelectPoemtryAdapter.MakePoemViewHolder> {

    private List<PoemListModel> data = new ArrayList<>();

    private BaseActivity activity;

    private Set<Integer> selectData = new HashSet<>();

    public Set<Integer> getSelectData() {
        return selectData;
    }

    public SelectPoemtryAdapter(BaseActivity activity) {
        this.activity = activity;
    }

    public void setData(List<PoemListModel> data) {
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
        holder.poemId = data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public int getSelectDataSize(){
        return selectData.size();
    }

    class MakePoemViewHolder extends RecyclerView.ViewHolder{
        TextView titleText, contentText;
        int poemId;

        public MakePoemViewHolder(final View itemView) {
            super(itemView);
            titleText = (TextView)itemView.findViewById(R.id.titleText);
            contentText = (TextView)itemView.findViewById(R.id.contentText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RelativeLayout rootView = (RelativeLayout)view;
                    if(rootView.findViewWithTag("check") == null){
                        View checkView = LayoutInflater.from(activity).inflate(R.layout.view_selected, (ViewGroup) itemView.getParent(), false);
                        checkView.setTag("check");
                        rootView.addView(checkView);
                        selectData.add(poemId);
                    }else{
                        View checkView = (View) rootView.findViewWithTag("check");
                        rootView.removeView(checkView);
                        selectData.remove(poemId);
                    }
                }
            });
        }
    }
}
