package root.hash_tm.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.Fragment.BookFragment;
import root.hash_tm.Fragment.MoreInfoViewFragment;
import root.hash_tm.Model.BookModel;
import root.hash_tm.R;
import root.hash_tm.Connect.RetrofitClass;
import root.hash_tm.Util.BaseActivity;

public class MainActivity extends BaseActivity {

    ViewPager viewPager;
    MainViewPagerAdapter adapter;
    Button logoutButton;

    int currentCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        logoutButton = (Button)findViewById(R.id.logout);
        adapter = new MainViewPagerAdapter(getSupportFragmentManager());

        getData();
        viewPager.setOffscreenPageLimit(5);

        viewPager.setAdapter(adapter);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNextActivity(SignInActivity.class, null);
                saveData("cookie", "");
                finish();
            }
        });
        FloatingActionButton writeButton = (FloatingActionButton) findViewById(R.id.writeButton);
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

    private void getData(){
        RetrofitClass.getInstance().apiInterface.getPopularBooks(currentCount, 4)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.code() == 200){
                            JsonElement data = response.body().get("data");
                            Gson gson = new Gson();
                            BookModel[] bookModelArr = gson.fromJson(data, BookModel[].class);
                            adapter.setData(bookModelArr);
                            adapter.notifyDataSetChanged();
                            viewPager.setCurrentItem(0);
                        }else{
                            showSnack("시가 없습니다.");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private class MainViewPagerAdapter extends FragmentStatePagerAdapter {

        private BookModel[] data = new BookModel[0];

        public void setData(BookModel[] data) {
            this.data = data;
            for(BookModel data1 : data){
                Log.e("data", data1.getTitle());
            }
        }

        public MainViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == data.length){
                MoreInfoViewFragment fragment = new MoreInfoViewFragment();
                fragment.setMoreInfo(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentCount++;
                        getData();
                    }
                });
                return fragment;
            }else{
                BookFragment bookFragment = new BookFragment(MainActivity.this, data[position]);
                return bookFragment;
            }
        }

        @Override
        public int getCount() {
            if(data.length == 0){
                return 0;
            }else{
                return data.length + 1;
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
