package root.hash_tm.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.Connect.RetrofitClass;
import root.hash_tm.Fragment.PoemFragment;
import root.hash_tm.Manager.TTSManager;
import root.hash_tm.Model.PoemIndexModel;
import root.hash_tm.Model.PoemIndexSendData;
import root.hash_tm.R;
import root.hash_tm.Util.BaseActivity;

/**
 * Created by root1 on 2017. 8. 29..
 */

public class PoemActivity extends BaseActivity {

    ImageButton beforeButton, afterButton, likeButton, backButton;
    TextView bookTitle, countText;

    TTSManager ttsManager;
    ViewPager viewPager;

    int position = 0;

    String bookId;
    String cookie;
    String writer;

    boolean isLike = false;

    RelativeLayout top, bottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem);

//        top = (RelativeLayout)findViewById(R.id.top);
//        bottom = (RelativeLayout)findViewById(R.id.bottom);

        ttsManager = new TTSManager(this);

        Intent intent = getIntent();

        bookId = intent.getStringExtra("bookId");
        String booktitleText = intent.getStringExtra("bookTitle");
        position = intent.getIntExtra("index", 0);
        writer = intent.getStringExtra("writer");

        final List<PoemIndexModel> data = ((PoemIndexSendData)intent.getSerializableExtra("data")).getIdArray();
        cookie = getPreferences().getString("cookie", "");

        bookTitle = (TextView)findViewById(R.id.bookTitle);
        bookTitle.setText(booktitleText);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String countStr = position + 1 + "/" + data.size();
                countText.setText(countStr);
                if(position == data.size() - 1){
                    afterButton.setVisibility(View.GONE);
                }else if(position == 0){
                    beforeButton.setVisibility(View.GONE);
                }else{
                    afterButton.setVisibility(View.VISIBLE);
                    beforeButton.setVisibility(View.VISIBLE);
                }
                PoemActivity.this.position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setOffscreenPageLimit(data.size() + 1);
        viewPager.setAdapter(new PoemViewPagerAdapter(getSupportFragmentManager(), data));

        beforeButton = (ImageButton)findViewById(R.id.beforeButton);
        afterButton = (ImageButton)findViewById(R.id.afterButton);
        likeButton = (ImageButton)findViewById(R.id.likeButton);
        backButton = (ImageButton)findViewById(R.id.backButton);

        countText = (TextView)findViewById(R.id.countText);

        checkLike();

        countText.setText(position + 1 + "/" + data.size());

        viewPager.setCurrentItem(position);

        afterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ttsManager.stopTTS();
                if(position < data.size() - 1){
                    viewPager.setCurrentItem(++position);
                }
            }
        });

        beforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ttsManager.stopTTS();
                if(position > 0){
                    viewPager.setCurrentItem(--position);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLikeButton();
            }
        });
    }

    private void clickLikeButton(){
        RetrofitClass.getInstance()
                .apiInterface
                .putLike(cookie, bookId, !isLike)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200){
                            isLike = !isLike;
                            setLikeImage();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void setLikeImage(){
        if(isLike){
            likeButton.setImageResource(R.drawable.ico_like);
        }else{
            likeButton.setImageResource(R.drawable.ico_unlike);
        }
    }

    private void checkLike(){
        RetrofitClass.getInstance().apiInterface
                .checkHeart(cookie, bookId)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200) {
                            isLike = true;
                        }else{
                            isLike = false;
                        }
                        setLikeImage();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ttsManager.shutDownTTS();
    }

    private class PoemViewPagerAdapter extends FragmentStatePagerAdapter{

        List<PoemIndexModel> data = new ArrayList<>();

        public PoemViewPagerAdapter(FragmentManager fm, List<PoemIndexModel> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public Fragment getItem(int position) {
            return new PoemFragment(cookie, data.get(position).getId(), ttsManager);
        }

        @Override
        public int getCount() {
            return data.size();
        }
    }
}
