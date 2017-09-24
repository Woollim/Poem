package root.hash_tm.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import root.hash_tm.Model.BookModel;
import root.hash_tm.R;
import root.hash_tm.activity.PoemtryIndexActivity;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 8. 29..
 */

@SuppressLint("ValidFragment")
public class BookFragment extends Fragment {

    private BaseActivity activity;
    private BookModel data;

    public BookFragment(BaseActivity context, BookModel data) {
        this.activity = context;
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_main_book,container,false);
        TextView titleText = (TextView) view.findViewById(R.id.titleText);
        TextView writerText = (TextView) view.findViewById(R.id.writerText);

        titleText.setText(data.getTitle());
        writerText.setText(data.getWriter());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, PoemtryIndexActivity.class);
                activity.startActivity(intent);
            }
        });

        return view;
    }
}
