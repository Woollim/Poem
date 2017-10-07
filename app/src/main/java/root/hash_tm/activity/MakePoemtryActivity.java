package root.hash_tm.activity;

import android.Manifest;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

public class MakePoemtryActivity extends BaseActivity implements View.OnClickListener {

    private String title = "";
    private static int GET_PICTURE_URL = 100;

    ImageView bookCard;

    RecyclerView recyclerView;
    List<PoemListModel> data;

    MakePoemtryAdapter adapter;

    @Override
    public void onClick(View view) {
        Set<Integer> tempData = adapter.getSelectData();
        JSONArray array = new JSONArray();

        for(int i : tempData){
            array.put(i);
        }

        bookCard.setClipToOutline(true);
        showSnack("시 원고를 보내고 있습니다.");

        RetrofitClass.getInstance().apiInterface
                .uploadPoemtry(getPreferences().getString("cookie",""), titleEdit.getText().toString(), array)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.e("xxx", "" + imagePath);
                        if(response.code() == 200){
                            if(!imagePath.isEmpty()){
                                showSnack("시 표지를 보내고 있습니다.");
                                RetrofitClass.getInstance()
                                        .apiInterface
                                        .uploadImage(getPreferences().getString("cookie",""), response.body().get("id").getAsInt(), getImage())
                                        .enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                if(response.code() == 201){
                                                    showSnack("시집을 출간하였습니다.");
                                                }else{
                                                    showSnack("시집의 표지가 날라갔습니다.");
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                t.printStackTrace();
                                            }
                                        });
                            }else{
                                showSnack("시집을 출간하였습니다.");
                            }
                        }else{
                            showSnack("시집을 출간하지 못했습니다.");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
    }

    EditText titleEdit;

    private MultipartBody.Part getImage(){
        File file = new File(imagePath);
        RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), body);
        return part;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_poemtry);

        getData();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        titleEdit = (EditText)findViewById(R.id.titleEdit);
        bookCard = (ImageView) findViewById(R.id.bookCover);

        bookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TedPermission.with(MakePoemtryActivity.this)
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, GET_PICTURE_URL);
                            }

                            @Override
                            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                            }
                        }).setDeniedMessage("권한을 받지 않으면 이미지를 불러오지 못합니다.")
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();
            }
        });

        FloatingActionButton sendButton = (FloatingActionButton)findViewById(R.id.sendButton);

        sendButton.setOnClickListener(this);

        adapter = new MakePoemtryAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                                adapter.setData(data);
                                adapter.notifyDataSetChanged();
                            }else{
                                showSnack("데이터를 불러오지 못했습니다.");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private String imagePath = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GET_PICTURE_URL){
            if(resultCode == Activity.RESULT_OK){
                try{
                    getPath(data.getData());
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                    bookCard.setImageDrawable((Drawable)bitmapDrawable);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    private void getPath(Uri uri){
        CursorLoader cursorLoader = new CursorLoader(this, uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        imagePath = cursor.getString(column_index);
    }
}
