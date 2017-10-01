package root.hash_tm.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.Model.PoemListModel;
import root.hash_tm.R;
import root.hash_tm.adapter.MakePoemtryAdapter;
import root.hash_tm.connect.RetrofitClass;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 10. 1..
 */

public class MakePoemtryActivity extends BaseActivity {

    private String title = "";
    private static int GET_PICTURE_URL = 100;

    ImageView bookCard;

    List<PoemListModel> data;

    MakePoemtryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_poemtry);

        getData();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        EditText titleEdit = (EditText)findViewById(R.id.titleEdit);
        bookCard = (ImageView) findViewById(R.id.bookCover);

        bookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_PICTURE_URL);
            }
        });

        FloatingActionButton sendButton = (FloatingActionButton)findViewById(R.id.sendButton);

        adapter = new MakePoemtryAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        RetrofitClass.getInstance().apiInterface
                .getMyPoem(getPreferences().getString("cookie",""))
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.code() == 200){
                            if(response.code() == 200){
                                JsonElement temp = response.body().get("data");
                                Gson gson = new Gson();
                                PoemListModel[] arrData = gson.fromJson(temp, PoemListModel[].class);
                                data = Arrays.asList(arrData);
                                adapter.setData((ArrayList<PoemListModel>) data);
                                adapter.notifyDataSetChanged();
                            }else{

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GET_PICTURE_URL){
            if(resultCode == Activity.RESULT_OK){
                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                    bookCard.setImageDrawable((Drawable)bitmapDrawable);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    }
}
