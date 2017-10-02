package root.hash_tm.connect;

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

    private String url = "http://52.43.254.152:80";

    private RetrofitClass(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

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
