package root.hash_tm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageButton;
import android.widget.TextView;

import root.hash_tm.R;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 8. 29..
 */

public class PoemActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem);

        ImageButton actionButton = (ImageButton)findViewById(R.id.actionButton);
        TextView titleText = (TextView)findViewById(R.id.titleText);
        TextView contentText = (TextView)findViewById(R.id.contentText);
        TextView writerText = (TextView)findViewById(R.id.writerText);


    }
}
