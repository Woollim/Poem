package root.hash_tm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import root.hash_tm.Fragment.MyPageRecyclerFragment;
import root.hash_tm.Model.MyPageMainModel;
import root.hash_tm.R;
import root.hash_tm.adapter.MyPageGridAdapter;
import root.hash_tm.adapter.MyPageLinearAdapter;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 8. 29..
 */

public class MyPageActivity extends BaseActivity {

    ViewPager viewPager;
    TextView titleText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        MyPageMainModel data = new MyPageMainModel("이병찬", "12", "4");

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        TextView nameText = (TextView)findViewById(R.id.nameText);
        TextView poemCountText = (TextView)findViewById(R.id.poemCountText);
        TextView poemtryCountText = (TextView)findViewById(R.id.poemtryCountText);

        nameText.setText(data.getName());
        poemCountText.setText(data.getPoemCount());
        poemtryCountText.setText(data.getPoemtryCount());

        titleText = (TextView)findViewById(R.id.titleText);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String titleArr[] = new String[]{"출간 완료 시집", "시 원고", "좋아요 한 시집", "선물 받은 시"};
                titleText.setText(titleArr[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(new MyPageViewAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(4);

        FloatingActionButton writeButton = (FloatingActionButton)findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNextActivity(WritePoemActivity.class, null);
            }
        });

    }

    class MyPageViewAdapter extends FragmentPagerAdapter{
        public MyPageViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MyPageRecyclerFragment fragment = new MyPageRecyclerFragment();
            if(position / 2 == 0){
                fragment.setAdapter(new MyPageGridAdapter(), true);
            }else{
                fragment.setAdapter(new MyPageLinearAdapter(), false);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
