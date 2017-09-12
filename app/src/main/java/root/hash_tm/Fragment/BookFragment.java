package root.hash_tm.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import root.hash_tm.R;
import root.hash_tm.activity.PoemtryActivity;

/**
 * Created by root1 on 2017. 8. 29..
 */

@SuppressLint("ValidFragment")
public class BookFragment extends Fragment {

    private Context context;

    public BookFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_book,container,false);
        //TextView titleText = (TextView) view.findViewById(R.id.titleText);
        //TextView writerText = (TextView) view.findViewById(R.id.writerText);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PoemtryActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
