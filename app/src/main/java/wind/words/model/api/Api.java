package wind.words.model.api;

import java.util.List;

import rx.Observable;
import wind.words.model.data.Word;

/**
 * Created by wind on 2015/9/12.
 */
public interface Api {

    Observable<List<Word>> listWords();

    Observable<Word> postWord(String word, String by);
}
