package root.hash_tm.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import root.hash_tm.Fragment.BookFragment;
import root.hash_tm.R;
import root.hash_tm.util.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager()));
    }

    private class MainViewPagerAdapter extends FragmentPagerAdapter{

        public MainViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new BookFragment(MainActivity.this);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
