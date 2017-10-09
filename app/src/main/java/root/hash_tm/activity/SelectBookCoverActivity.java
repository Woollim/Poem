package root.hash_tm.activity;

import android.Manifest;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.R;
import root.hash_tm.connect.RetrofitClass;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 10. 1..
 */

public class SelectBookCoverActivity extends BaseActivity implements View.OnClickListener{
    private static int GET_PICTURE_URL = 100;

    ImageView bookCover;


    @Override
    public void onClick(View view) {
        if(imagePath.isEmpty()){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("확인").setMessage("표지없이 시집을 출간하시겠습니까?")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            builder.create().cancel();
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    builder.create().cancel();
                }
            }).create().show();
        }else{
            RetrofitClass.getInstance()
                    .apiInterface
                    .uploadImage(getPreferences().getString("cookie",""), bookId, getImage())
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
        }
    }

    private MultipartBody.Part getImage(){
        File file = new File(imagePath);
        RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), body);
        return part;
    }

    private String bookId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_book_cover);

        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");

        bookCover = (ImageView) findViewById(R.id.bookCover);

        bookCover.setClipToOutline(true);

        bookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TedPermission.with(SelectBookCoverActivity.this)
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
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
                    bookCover.setImageDrawable((Drawable)bitmapDrawable);
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
