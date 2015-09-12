package wind.words.model.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

import wind.words.model.bmob.BmobWord;

/**
 * Created by wind on 2015/9/12.
 */
@Table(name = Word.TABLE_NAME)
public class Word extends Model {

    public static final String TABLE_NAME = "words";
    public static final String COL_ID = "id";
    public static final String COL_WORD = "word";
    public static final String COL_BY = "by";
    public static final String COL_CREATE_AT = "create_at";
    public static final String COL_UPDATE_AT = "update_at";

    @Column(name = COL_ID)
    public String id;

    @Column(name = COL_WORD)
    public String word;

    @Column(name = COL_BY)
    public String by;

    @Column(name = COL_CREATE_AT)
    public String createdAt;

    @Column(name = COL_UPDATE_AT)
    public String updateAt;

    public Word(BmobWord bmobWord) {
        id = bmobWord.getObjectId();
        word = bmobWord.getWord();
        by = bmobWord.getBy();
        createdAt = bmobWord.getCreatedAt();
        updateAt = bmobWord.getUpdatedAt();
    }

    public BmobWord toBmobWord() {
        BmobWord bmobWord = new BmobWord();
        bmobWord.setWord(word);
        bmobWord.setBy(by);
        return bmobWord;
    }

}
