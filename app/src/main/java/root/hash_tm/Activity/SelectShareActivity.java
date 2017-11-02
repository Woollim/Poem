package root.hash_tm.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import root.hash_tm.R;

/**
 * Created by root1 on 2017. 10. 11..
 */

public class SelectShareActivity extends Activity implements View.OnClickListener {


    Button shareButton, sharedButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.dialog_select_share);

        shareButton = (Button)findViewById(R.id.shareButton);
        sharedButton = (Button)findViewById(R.id.sharedButton);

        shareButton.setOnClickListener(this);
        sharedButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, BluetoothShareActivity.class);
        boolean isShare = false;

        if(((Button)view) == shareButton){
            isShare = true;
        }

        intent.putExtra("isShare", isShare);
        startActivity(intent);
        finish();
    }
}
