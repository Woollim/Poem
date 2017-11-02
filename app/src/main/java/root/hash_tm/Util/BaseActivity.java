package root.hash_tm.Util;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

    public void showSnack(String msg){
//        Snackbar.make(getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_SHORT).show();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void goNextActivity(Class nextClass, @Nullable ArrayList<IntentModel> datas){
        Intent intent = new Intent(this, nextClass);
        if(datas != null){
            for(IntentModel data : datas){
                intent.putExtra(data.getKey(), data.getValue());
            }
        }
        startActivity(intent);
    }

    public SharedPreferences getPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref;
    }

    public void saveData(String key, String value){
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.remove(key);
        editor.putString(key, value);
        editor.commit();
    }
}
