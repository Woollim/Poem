package root.hash_tm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.Fragment.MyPageRecyclerFragment;
import root.hash_tm.Model.UserInfoModel;
import root.hash_tm.R;
import root.hash_tm.adapter.MyPageGridAdapter;
import root.hash_tm.adapter.MyPageLinearAdapter;
import root.hash_tm.connect.RetrofitClass;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 8. 29..
 */

public class MyPageActivity extends BaseActivity {

    ViewPager viewPager;
    TextView titleText;

    TextView nameText, poemCountText, poemtryCountText;

    private int poemCount = 0;

    private int position = 0;

    private String titleArr[] = new String[]{"시 원고", "출간 완료 시집", "선물 받은 시", "좋아요 한 시집"};

    private FloatingActionButton actionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        nameText = (TextView)findViewById(R.id.nameText);
        poemCountText = (TextView)findViewById(R.id.poemCountText);
        poemtryCountText = (TextView)findViewById(R.id.poemtryCountText);

        titleText = (TextView)findViewById(R.id.titleText);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                titleText.setText(titleArr[position]);
                actionButton.setVisibility(View.VISIBLE);

                MyPageActivity.this.position = position;

                switch (position){
                    case 0:
                        actionButton.setImageResource(R.drawable.icon_main_pen);
                        actionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            goNextActivity(WritePoemActivity.class, null);
                        }
                    });
                        break;
                    case 1:
                        actionButton.setImageResource(R.drawable.ic_book);
                        actionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(poemCount >= 3){
                                goNextActivity(MakePoemtryActivity.class, null);
                            }else{
                                showSnack("출간하기에 시가 충분치 않습니다.");
                            }
                        }
                    });
                        break;
                    case 2:
                        actionButton.setImageResource(R.drawable.ic_bluetooth_share);
                        actionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            goNextActivity(BluetoothShareActivity.class, null);
                        }
                    });
                        break;
                    case 3: actionButton.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setOffscreenPageLimit(4);


        actionButton = (FloatingActionButton)findViewById(R.id.writeButton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNextActivity(WritePoemActivity.class, null);
            }
        });

    }

    MyPageViewAdapter adapter;


    private void getData(){
        RetrofitClass.getInstance().apiInterface
                .getMyInfo(getPreferences().getString("cookie",""))
                .enqueue(new Callback<UserInfoModel>() {
                    @Override
                    public void onResponse(Call<UserInfoModel> call, Response<UserInfoModel> response) {
                        if(response.code() == 200){
                            nameText.setText(response.body().getName());
                            poemCountText.setText(response.body().getPoems());
                            poemtryCountText.setText(response.body().getBooks());
                            poemCount = response.body().getPoemCountInt();
                        }else{
                            showSnack("데이터 수신 오류");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfoModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        getData();

        adapter = new MyPageViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        viewPager.setCurrentItem(position);
    }

    class MyPageViewAdapter extends FragmentStatePagerAdapter{

        public MyPageViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            MyPageRecyclerFragment fragment = new MyPageRecyclerFragment();

            if(position % 2 == 1){
                fragment.setAdapter(new MyPageGridAdapter(titleArr[position], getPreferences().getString("cookie", ""), MyPageActivity.this), true);
            }else{
                fragment.setAdapter(new MyPageLinearAdapter(titleArr[position], getPreferences().getString("cookie", ""), MyPageActivity.this), false);
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
