package root.hash_tm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import root.hash_tm.Manager.TTSManager;
import root.hash_tm.R;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 9. 4..
 */

public class PoemtryActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poemtry);
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(new PoemtryViewAdapter(getSupportFragmentManager(), PoemtryActivity.this));
    }

    class PoemtryViewAdapter extends FragmentPagerAdapter{

        private Context context;
        public PoemtryViewAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return new PoemFragment(context);
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}

class PoemFragment extends Fragment{

    TTSManager tts;

    public PoemFragment(Context context) {
        tts = new TTSManager(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_poem, container, false);
        ImageButton speakButton = (ImageButton)view.findViewById(R.id.speakButton);
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.readTTS("hello world");
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tts.shutDownTTS();
    }
}
