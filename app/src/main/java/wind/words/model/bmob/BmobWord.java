package wind.words.model.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by wind on 2015/9/12.
 */
public class BmobWord extends BmobObject {

    private String word;

    private String by;

    public BmobWord(String word, String by) {
        this.word = word;
        this.by = by;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }
}
