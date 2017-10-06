package root.hash_tm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import root.hash_tm.Model.PoemModel;
import root.hash_tm.R;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 8. 29..
 */

public class SharePoemActivity extends BaseActivity {

    ImageButton removeButton, editButton;
    TextView titleText, contentText, writerText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_no_out);


        removeButton = (ImageButton)findViewById(R.id.removeButton);
        editButton = (ImageButton)findViewById(R.id.editButton);
        titleText = (TextView)findViewById(R.id.titleText);
        contentText = (TextView)findViewById(R.id.contentText);
        writerText = (TextView)findViewById(R.id.writerText);

        removeButton.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);

        Intent intent = getIntent();
        setData((PoemModel)intent.getSerializableExtra("data"));

    }

    private void setData(PoemModel data){
        titleText.setText(data.getTitle());
        contentText.setText(data.getContent());
        writerText.setText(data.getWriter());

        switch (data.getAlignment()){
            case 3:
                contentText.setGravity(Gravity.LEFT);
                break;
            case 2:
                contentText.setGravity(Gravity.CENTER);
                break;
            case 1:
                contentText.setGravity(Gravity.RIGHT);
                break;
            default:
                break;
        }
    }

}
