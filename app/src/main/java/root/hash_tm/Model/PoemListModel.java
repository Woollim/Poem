package root.hash_tm.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root1 on 2017. 9. 27..
 */

public class PoemListModel {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("writer")
    private String writer;

    @SerializedName("book")
    private int bookId;

    @SerializedName("content")
    private String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
