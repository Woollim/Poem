package root.hash_tm.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import root.hash_tm.R;

/**
 * Created by root1 on 2017. 10. 10..
 */

public class LastBookFragment extends Fragment {

    private String writer, title;
    private boolean isLike = false;
    private TextView titleText, writerText;

    public LastBookFragment(String title, String writer, boolean isLike) {
        this.writer = writer;
        this.title = title;
        this.isLike = isLike;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_poem_last, container, false);
        titleText = (TextView)view.findViewById(R.id.titleText);
        writerText = (TextView)view.findViewById(R.id.writerText);
        titleText.setText(title);
        writerText.setText(writer);
        return view;
    }
}
