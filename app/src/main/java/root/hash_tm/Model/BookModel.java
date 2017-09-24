package root.hash_tm.Model;

/**
 * Created by root1 on 2017. 9. 24..
 */

public class BookModel {
    private String title, writer;

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
