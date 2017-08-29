package root.hash_tm.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by root1 on 2017. 8. 29..
 */

public class UtilClass {

    private UtilClass(){

    }

    private static UtilClass utilClass = new UtilClass();

    public Context context;

    public static UtilClass getInstance(){
        return utilClass;
    }

    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void goNextActivity(Class nextClass){
        Intent intent = new Intent(context, nextClass);
        context.startActivity(intent);
    }
}
