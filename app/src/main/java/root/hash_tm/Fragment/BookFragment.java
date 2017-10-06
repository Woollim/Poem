package root.hash_tm.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import root.hash_tm.Model.BookModel;
import root.hash_tm.Model.IntentModel;
import root.hash_tm.R;
import root.hash_tm.activity.PoemtryIndexActivity;
import root.hash_tm.util.BaseActivity;
import root.hash_tm.util.UtilClass;

/**
 * Created by root1 on 2017. 9. 26..
 */

public class BookFragment extends Fragment {

    private BookModel data;
    private BaseActivity activity;

    public BookFragment(BaseActivity activity, BookModel data) {
        this.data = data;
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_main_book, container, false);
        ImageView bookImage = (ImageView) view.findViewById(R.id.bookImage);
        TextView writerText = (TextView) view.findViewById(R.id.writerText);
        TextView titleText = (TextView) view.findViewById(R.id.titleText);

        titleText.setText(data.getTitle());
        writerText.setText(data.getWriter());

        UtilClass.getInstance().setImage(activity, data.getId()+"", bookImage);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<IntentModel> sendData = new ArrayList<IntentModel>();
                sendData.add(new IntentModel("bookId", data.getId()+""));
                activity.goNextActivity(PoemtryIndexActivity.class, sendData);
            }
        });

        return view;
    }
}
