package root.hash_tm.connect;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import root.hash_tm.Model.PoemIndexInfoModel;
import root.hash_tm.Model.PoemModel;
import root.hash_tm.Model.UserInfoModel;

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
    Call<JsonObject> getPopularBooks(@Query("page")int page, @Query("length")int length);

    @GET("/member/books")
    Call<JsonObject> getMyPoemtry(@Header("Poem-Session-Key")String cookie);

    @GET("/member/books/hearted")
    Call<JsonObject> getHeartPomtry(@Header("Poem-Session-Key")String cookie);

    @GET("/member/poems")
    Call<JsonObject> getMyPoem(@Header("Poem-Session-Key")String cookie);

    @GET("/poem/{poemId}")
    Call<PoemModel> getPoem(@Path("poemId") String poemId, @Header("Poem-Session-Key")String cookie);

    @GET("/book/{bookId}")
    Call<PoemIndexInfoModel> getIndexInfo(@Path("bookId") String bookId);

    @GET("/book/{bookId}/poems")
    Call<JsonObject> getIndexContent(@Path("bookId")String bookId);

    @POST("/poem")
    @FormUrlEncoded
    Call<Void> uploadPoem(@Header("Poem-Session-Key")String cookie, @Field("title")String title, @Field("content")String content, @Field("alignment")int gravity);

    @PUT("/poem/{poemId}")
    @FormUrlEncoded
    Call<Void> editPoem(@Header("Poem-Session-Key")String cookie, @Path("poemId") int poemId, @Field("title")String title, @Field("content")String content, @Field("alignment")int gravity);

    @GET("/member")
    Call<UserInfoModel> getMyInfo(@Header("Poem-Session-Key")String cookie);

    @POST("/book")
    Call<Void> uploadPoemtry(@Header("Poem-Session-Key")String cookie, @Field("title")String title, @Field("poems")JsonArray poems);




}
