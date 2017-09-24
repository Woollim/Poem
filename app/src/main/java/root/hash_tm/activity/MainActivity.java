package root.hash_tm.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import root.hash_tm.Fragment.BookFragment;
import root.hash_tm.Fragment.MoreInfoViewFragment;
import root.hash_tm.Model.BookModel;
import root.hash_tm.R;
import root.hash_tm.util.BaseActivity;

public class MainActivity extends BaseActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<BookModel> data = new ArrayList<>();
        data.add(new BookModel("안녕하세요", "이병찬"));
        data.add(new BookModel("안녕하세요", "이병찬"));
        data.add(new BookModel("안녕하세요", "이병찬"));
        data.add(new BookModel("안녕하세요", "이병찬"));
        data.add(new BookModel("안녕하세요", "이병찬"));

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(), data));
        viewPager.setOffscreenPageLimit(data.size() + 1);

        RelativeLayout writeButton = (RelativeLayout)findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNextActivity(WritePoemActivity.class, null);
            }
        });

        ImageButton myPageButton = (ImageButton)findViewById(R.id.myPageButton);
        myPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNextActivity(MyPageActivity.class, null);
            }
        });

    }

    private class MainViewPagerAdapter extends FragmentPagerAdapter{

        ArrayList<BookModel> data;

        public MainViewPagerAdapter(FragmentManager fm, ArrayList<BookModel> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == data.size()){
                MoreInfoViewFragment fragment = new MoreInfoViewFragment();
                fragment.setMoreInfo(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(0);
                    }
                });
                return fragment;
            }else{
                return new BookFragment(MainActivity.this, data.get(position));
            }
        }

        @Override
        public int getCount() {
            return data.size() + 1;
        }
    }
}
