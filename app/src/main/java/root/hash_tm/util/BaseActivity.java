package root.hash_tm.util;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import root.hash_tm.Model.IntentModel;

/**
 * Created by root1 on 2017. 8. 28..
 */

public class BaseActivity extends AppCompatActivity {

    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showSnack(String msg, @Nullable String actionName, @Nullable View.OnClickListener action){
        if(actionName == null){
            Snackbar.make(getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_SHORT).show();
        }else{
            Snackbar.make(getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_LONG).setAction(actionName, action).show();
        }
    }

    public void goNextActivity(Class nextClass, @Nullable ArrayList<IntentModel> datas){
        Intent intent = new Intent(this, nextClass);
        if(datas != null){
            for(IntentModel data : datas){
                intent.putExtra(data.getKey(), data.getValue());
            }
        }
        startActivity(intent);
        this.finish();
    }


}
