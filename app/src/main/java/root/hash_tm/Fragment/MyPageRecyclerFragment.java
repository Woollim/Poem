package root.hash_tm.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by root1 on 2017. 9. 24..
 */

public class MyPageRecyclerFragment extends Fragment {

    private RecyclerView.Adapter adapter;
    private boolean isGrid;

    public void setAdapter(RecyclerView.Adapter adapter, boolean isGrid) {
        this.adapter = adapter;
        this.isGrid = isGrid;
    }

    public void reloadData(){
        adapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = new LinearLayout(container.getContext());
        RecyclerView recyclerView = new RecyclerView(container.getContext());
        layout.addView(recyclerView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        recyclerView.setAdapter(adapter);

        if(isGrid){
            recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 2));
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        }

        return layout;
    }
}
