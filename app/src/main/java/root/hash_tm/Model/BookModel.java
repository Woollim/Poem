package root.hash_tm.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root1 on 2017. 9. 24..
 */

public class BookModel {

    @SerializedName("title")
    private String title;
    @SerializedName("writer")
    private String writer;
    @SerializedName("id")
    private int id;
    @SerializedName("hearts")
    private int hearts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer + " 지음";
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public BookModel(String title, String writer) {
        this.title = title;
        this.writer = writer;
    }
}
