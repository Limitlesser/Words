package wind.words.model.api;

import com.activeandroid.ActiveAndroid;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import wind.words.WordsApp;
import wind.words.model.bmob.BmobWord;
import wind.words.model.data.Word;

/**
 * Created by wind on 2015/9/12.
 */
public class ApiImpl implements Api {
    @Override
    public Observable<List<Word>> listWords() {
        return Observable.create(new Observable.OnSubscribe<List<BmobWord>>() {
            @Override
            public void call(final Subscriber<? super List<BmobWord>> subscriber) {
                BmobQuery<BmobWord> query = new BmobQuery<>();
                query.findObjects(WordsApp.getContext(), new FindListener<BmobWord>() {
                    @Override
                    public void onSuccess(List<BmobWord> list) {
                        subscriber.onNext(list);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(int i, String s) {
                        subscriber.onError(new ApiError(i, s));
                    }
                });
            }
        }).map(new Func1<List<BmobWord>, List<Word>>() {
            @Override
            public List<Word> call(List<BmobWord> bmobWords) {
                List<Word> words = new ArrayList<>();
                for (BmobWord bmobWord : bmobWords) {
                    Word word = new Word(bmobWord);
                    words.add(word);
                }
                return words;
            }
        }).doOnNext(new Action1<List<Word>>() {
            @Override
            public void call(List<Word> words) {
                try {
                    ActiveAndroid.beginTransaction();
                    for (Word word : words) {
                        word.save();
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }

            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Word> postWord(final String word, final String by) {
        return Observable.create(new Observable.OnSubscribe<Word>() {
            @Override
            public void call(final Subscriber<? super Word> subscriber) {
                final BmobWord bmobWord = new BmobWord(word, by);
                bmobWord.save(WordsApp.getContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Word wordM = new Word(bmobWord);
                        wordM.save();
                        subscriber.onNext(wordM);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        subscriber.onError(new ApiError(i, s));
                    }
                });
            }
        }).subscribeOn(Schedulers.io());
    }
}
