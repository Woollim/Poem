package root.hash_tm.Connect;

        import java.util.concurrent.TimeUnit;

        import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by geni on 2017. 8. 31..
 */

public class RetrofitClass {
    private static RetrofitClass retrofitClass = new RetrofitClass();

    private Retrofit retrofit;

    public ConnectInterface apiInterface;

    // 원래 서버(http://52.43.254.152:80)가 사라져서 로컬 목 서버(backend/server.py)를 사용
    // 에뮬레이터: 10.0.2.2 = 호스트 맥. 실기기에서는 맥의 LAN IP로 변경할 것
    public static final String BASE_URL = "http://10.0.2.2:8081";

    private String url = BASE_URL;

    private RetrofitClass(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        apiInterface = retrofit.create(ConnectInterface.class);
    }

    public static RetrofitClass getInstance() {
        return retrofitClass;
    }
}
