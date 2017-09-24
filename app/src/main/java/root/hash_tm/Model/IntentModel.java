package root.hash_tm.Model;

/**
 * Created by root1 on 2017. 9. 24..
 */

public class IntentModel {
    private String key;
    private String value;

    public IntentModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
