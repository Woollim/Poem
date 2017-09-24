package root.hash_tm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import root.hash_tm.R;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 9. 4..
 */

public class PoemtryIndexActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poemtry_index);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView titleText = (TextView)findViewById(R.id.titleText);
        TextView poemCountText = (TextView)findViewById(R.id.poemCountText);
        TextView writerText = (TextView)findViewById(R.id.writerText);
    }

}
