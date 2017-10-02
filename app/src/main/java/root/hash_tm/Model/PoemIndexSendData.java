package root.hash_tm.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root1 on 2017. 10. 2..
 */

@SuppressWarnings("serial")
public class PoemIndexSendData implements Serializable {
    @SerializedName("bookTitleText")
    private String bookTitleText;
    @SerializedName("idArray")
    private List<PoemIndexModel> idArray;

    public PoemIndexSendData(String bookTitleText, List<PoemIndexModel> idArray) {
        this.bookTitleText = bookTitleText;
        this.idArray = idArray;
    }

    public String getBookTitleText() {
        return bookTitleText;
    }

    public List<PoemIndexModel> getIdArray() {
        return idArray;
    }
}
