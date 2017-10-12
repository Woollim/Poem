package root.hash_tm.Util;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import root.hash_tm.R;

/**
 * Created by root1 on 2017. 8. 29..
 */

public class UtilClass {

    private UtilClass(){}

    private static UtilClass utilClass = new UtilClass();

    public static UtilClass getInstance(){
        return utilClass;
    }

    public void setImage(BaseActivity activity, String bookId, ImageView imageView){
        Log.e("xxx image", "id" + bookId);
        Glide.with(activity)
                .load("http://52.43.254.152:80/book/"+bookId+"/image")
                .placeholder(R.drawable.main_shape_view_book)
                .override(237, 344)
                .error(R.drawable.main_shape_view_book)
                .into(imageView);
    }

}
