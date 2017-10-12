package root.hash_tm.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.hash_tm.Connect.RetrofitClass;
import root.hash_tm.Manager.TTSManager;
import root.hash_tm.Model.PoemModel;
import root.hash_tm.R;

/**
 * Created by root1 on 2017. 10. 10..
 */

public class PoemFragment extends Fragment {

    private String cookie, poemId;
    private TTSManager ttsManager;

    public PoemFragment(String cookie, String poemId, TTSManager ttsManager) {
        this.cookie = cookie;
        this.poemId = poemId;
        this.ttsManager = ttsManager;
        getData();
    }

    private void getData(){
        RetrofitClass.getInstance().apiInterface.getPoem(poemId, cookie)
                .enqueue(new Callback<PoemModel>() {
                    @Override
                    public void onResponse(Call<PoemModel> call, Response<PoemModel> response) {
                        if(response.code() == 200){
                            titleText.setText(response.body().getTitle());
                            contentText.setText(response.body().getContent());
                            writerText.setText(response.body().getWriter());
                            switch (response.body().getAlignment()){
                                case 3:
                                    contentText.setGravity(Gravity.LEFT);
                                    break;
                                case 2:
                                    contentText.setGravity(Gravity.CENTER);
                                    break;
                                case 1:
                                    contentText.setGravity(Gravity.RIGHT);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PoemModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    ImageButton speakButton;
    TextView titleText, contentText, writerText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_poem, container, false);
        titleText = (TextView)view.findViewById(R.id.titleText);
        contentText = (TextView)view.findViewById(R.id.contentText);
        writerText = (TextView)view.findViewById(R.id.writerText);
        speakButton = (ImageButton)view.findViewById(R.id.speakButton);

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ttsManager.isSpeaking()){
                    speakButton.setImageResource(R.drawable.ico_stop);
                    ttsManager.stopTTS();
                }else{
                    speakButton.setImageResource(R.drawable.ico_tts);
                    String readStr = titleText.getText().toString() + "\n" + contentText.getText().toString();
                    ttsManager.readTTS(readStr);
                }
            }
        });
        return view;
    }
}
