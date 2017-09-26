package root.hash_tm.connect;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import root.hash_tm.Model.PoemIndexInfoModel;
import root.hash_tm.Model.PoemModel;

/**
 * Created by root1 on 2017. 8. 28..
 */

public interface ConnectInterface {
    @POST("/join/email/check")
    @FormUrlEncoded
    Call<Void> checkEmailTwice(@Field("email") String email);

    @POST("/join")
    @FormUrlEncoded
    Call<Void> signUp(@Field("email") String email, @Field("password") String password, @Field("name") String name);

    @POST("/login")
    @FormUrlEncoded
    Call<Void> signIn(@Field("email") String email, @Field("password") String password);

    @GET("/books/popular")
    Call<JsonObject> getPopularBooks();

    @GET("/member/books")
    Call<JsonObject> getMyPoemtry(@Header("Set-Cookie")String cookie);

    @GET("/member/books/hearted")
    Call<JsonObject> getHeartPomtry(@Header("Set-Cookie")String cookie);

    @GET("/member/poems")
    Call<JsonObject> getMyPoem(@Header("Set-Cookie")String cookie);

    @GET("/poem/{poemId}")
    Call<PoemModel> getPoem(@Query("poemId")String poemId);

    @GET("/book/{bookId}")
    Call<PoemIndexInfoModel> getIndexInfo(@Path("bookId") String bookId);

    @GET("/book/{bookId}/poem")
    Call<JsonObject> getIndexContent(@Path("bookId")String bookId);

    @POST("/poem")
    @FormUrlEncoded
    Call<Void> uploadPoem(@Header("Set-Cookie")String cookie, @Field("title")String title, @Field("content")String content, @Field("alignment")int gravity);




}
