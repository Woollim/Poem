package root.hash_tm.util;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import root.hash_tm.R;

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

    public void setImage(BaseActivity activity, String bookId, ImageView imageView){
        Glide.with(activity)
                .load("http://52.43.254.152:80/book/"+bookId+"/image")
                .placeholder(R.drawable.back_mypage_grid)
                .error(R.drawable.back_mypage_grid)
                .into(imageView);
    }

}
