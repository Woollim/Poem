package root.hash_tm.Manager;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by root1 on 2017. 9. 1..
 */

public class TTSManager {
    public TextToSpeech tts;

    public TTSManager(Context context) {
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });

        //tts.setSpeechRate(0.8f);
    }

    public void shutDownTTS(){
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
    }

    public boolean isSpeaking(){
        return tts.isSpeaking();
    }

    public void stopTTS(){
        tts.stop();
    }

    public void readTTS(String text){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
