package wind.words.model.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

import wind.words.model.bmob.BmobWord;

/**
 * Created by wind on 2015/9/12.
 */
@Table(name = Word.TABLE_NAME)
public class Word extends Model {

    public static final String TABLE_NAME = "words";
    public static final String COL_ID = "_id";
    public static final String COL_WORD = "word";
    public static final String COL_BY = "by";
    public static final String COL_CREATE_AT = "create_at";
    public static final String COL_UPDATE_AT = "update_at";

    @Column(name = COL_ID, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String id;

    @Column(name = COL_WORD)
    public String word;

    @Column(name = COL_BY)
    public String by;

    @Column(name = COL_CREATE_AT)
    public String createdAt;

    @Column(name = COL_UPDATE_AT)
    public String updateAt;

    public Word() {

    }

    public Word(BmobWord bmobWord) {
        id = bmobWord.getObjectId();
        word = bmobWord.getWord();
        by = bmobWord.getBy();
        createdAt = bmobWord.getCreatedAt();
        updateAt = bmobWord.getUpdatedAt();
    }

    public BmobWord toBmobWord() {
        return new BmobWord(word, by);
    }

    public static List<Word> list() {
        return new Select().from(Word.class).execute();
    }

    @Override
    public String toString() {
        return "Word{" +
                "id='" + id + '\'' +
                ", word='" + word + '\'' +
                ", by='" + by + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                '}';
    }
}
