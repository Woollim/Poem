package root.hash_tm.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import root.hash_tm.R;

/**
 * Created by root1 on 2017. 9. 12..
 */

public class MoreInfoViewFragment extends Fragment{

    private Button getInfoButton;

    private View.OnClickListener moreInfo;

    public void setMoreInfo(View.OnClickListener moreInfo) {
        this.moreInfo = moreInfo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_main_more_info,container,false);
        getInfoButton = (Button)view.findViewById(R.id.getInfoButton);
        getInfoButton.setOnClickListener(moreInfo);
        return view;
    }

}
