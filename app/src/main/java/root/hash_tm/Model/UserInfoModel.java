package root.hash_tm.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root1 on 2017. 9. 26..
 */

public class UserInfoModel {

    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("books")
    private int books;
    @SerializedName("poems")
    private int poems;

    public int getPoemCountInt(){
        return poems;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name + " 작가";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBooks() {
        return "시집 " + books + "권";
    }

    public void setBooks(int books) {
        this.books = books;
    }

    public String getPoems() {
        return "시 " + poems + "편";
    }

    public void setPoems(int poems) {
        this.poems = poems;
    }
}
