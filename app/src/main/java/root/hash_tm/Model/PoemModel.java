package root.hash_tm.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by root1 on 2017. 9. 26..
 */

public class PoemModel extends RealmObject implements Serializable{

    public PoemModel() {
    }

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("alignment")
    private int alignment;

    @SerializedName("writer")
    private String writer;

    @SerializedName("book")
    private int bookId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
