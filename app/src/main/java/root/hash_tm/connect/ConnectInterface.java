package root.hash_tm.connect;

import com.google.gson.JsonObject;

import org.json.JSONArray;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import root.hash_tm.Model.PoemIndexInfoModel;
import root.hash_tm.Model.PoemModel;
import root.hash_tm.Model.UserInfoModel;

/**
 * Created by root1 on 2017. 8. 28..
 */

public interface ConnectInterface {

    @DELETE("/poem/{poemId}")
    Call<Void> removePoem(@Header("Poem-Session-Key")String cookie, @Path("poemId")String poemId);

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
    Call<Void> createPoem(@Header("Poem-Session-Key")String cookie, @Field("title")String title, @Field("content")String content, @Field("alignment")int gravity);

    @PUT("/poem/{poemId}")
    @FormUrlEncoded
    Call<Void> editPoem(@Header("Poem-Session-Key")String cookie, @Path("poemId") int poemId, @Field("title")String title, @Field("content")String content, @Field("alignment")int gravity);

    @GET("/member")
    Call<UserInfoModel> getMyInfo(@Header("Poem-Session-Key")String cookie);

    @POST("/book")
    @FormUrlEncoded
    Call<JsonObject> uploadPoemtry(@Header("Poem-Session-Key")String cookie, @Field("title")String title, @Field("poems")JSONArray poems);

    @POST("/book/{bookId}/image")
    @Multipart
    Call<Void> uploadImage(@Header("Poem-Session-Key")String cookie, @Path("bookId")int bookId, @Part MultipartBody.Part img);

    @GET("/book/{bookId}/heart")
    Call<Void> checkHeart(@Header("Poem-Session-Key")String cookie, @Path("bookId")String bookId);

    @PUT("/book/{bookId}/heart")
    @FormUrlEncoded
    Call<Void> putLike(@Header("Poem-Session-Key")String cookie, @Path("bookId")String bookId,@Field("heart")boolean heart);

}
