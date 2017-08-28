package root.hash_tm.util;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by root1 on 2017. 8. 28..
 */

public class BaseActivity extends AppCompatActivity {

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void showSnack(String msg, @Nullable String actionName, @Nullable View.OnClickListener action){
        if(actionName == null){
            Snackbar.make(getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_SHORT).show();
        }else{
            Snackbar.make(getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_LONG).setAction(actionName, action).show();
        }
    }

    private void goNextActivity(Class nextClass){
        Intent intent = new Intent(this, nextClass);
        startActivity(intent);
    }


}
