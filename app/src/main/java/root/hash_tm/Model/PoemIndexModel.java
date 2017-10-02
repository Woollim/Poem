package root.hash_tm.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by root1 on 2017. 9. 26..
 */

public class PoemIndexModel implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
