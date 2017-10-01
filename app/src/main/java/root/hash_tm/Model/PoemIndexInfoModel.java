package root.hash_tm.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root1 on 2017. 9. 26..
 */

public class PoemIndexInfoModel {

    @SerializedName("title")
    private String title;

    @SerializedName("writer")
    private String writer;

    @SerializedName("hearts")
    private int hearts;

    @SerializedName("poems")
    private int poems;

    public String getPoems() {
        return "총 " + poems + "편";
    }

    public void setPoems(int poems) {
        this.poems = poems;
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

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }
}
