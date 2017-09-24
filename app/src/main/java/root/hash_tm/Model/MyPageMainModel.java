package root.hash_tm.Model;

/**
 * Created by root1 on 2017. 9. 24..
 */

public class MyPageMainModel {
    private String name, poemCount, poemtryCount;


    public MyPageMainModel(String name, String poemCount, String poemtryCount) {
        this.name = name;
        this.poemCount = poemCount;
        this.poemtryCount = poemtryCount;
    }

    public String getName() {
        return name + " 작가";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoemCount() {
        return "시 " + poemCount + "편";
    }

    public void setPoemCount(String poemCount) {
        this.poemCount = poemCount;
    }

    public String getPoemtryCount() {
        return "책 " + poemtryCount + "권";
    }

    public void setPoemtryCount(String poemtryCount) {
        this.poemtryCount = poemtryCount;
    }
}
