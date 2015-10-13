package wind.words.model.api;

import android.util.Log;

import com.activeandroid.ActiveAndroid;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import wind.words.WordsApp;
import wind.words.model.bmob.BmobWord;
import wind.words.model.data.Word;

/**
 * Created by wind on 2015/9/12.
 */
public class ApiImpl implements Api {

    private static final String TAG = ApiImpl.class.getSimpleName();

    @Override
    public Observable<List<Word>> listWords() {
        return Observable.create(new Observable.OnSubscribe<List<BmobWord>>() {
            @Override
            public void call(final Subscriber<? super List<BmobWord>> subscriber) {
                final CountDownLatch latch = new CountDownLatch(1);
                BmobQuery<BmobWord> query = new BmobQuery<>();
                query.findObjects(WordsApp.getContext(), new FindListener<BmobWord>() {
                            @Override
                            public void onSuccess(List<BmobWord> list) {
                                Log.i(TAG, "bmobWords:" + list);
                                subscriber.onNext(list);
                                subscriber.onCompleted();
                                latch.countDown();
                            }

                            @Override
                            public void onError(int i, String s) {
                                subscriber.onError(new ApiError(i, s));
                                latch.countDown();
                            }
                        }
                );
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).map(new Func1<List<BmobWord>, List<Word>>() {
            @Override
            public List<Word> call(List<BmobWord> bmobWords) {
                List<Word> words = new ArrayList<>();
                try {
                    ActiveAndroid.beginTransaction();
                    for (BmobWord bmobWord : bmobWords) {
                        Word word = new Word(bmobWord);
                        word.save();
                        words.add(word);
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
                return words;
            }
        });
    }

    @Override
    public Observable<Word> postWord(final String word, final String by) {
        return Observable.create(new Observable.OnSubscribe<Word>() {
            @Override
            public void call(final Subscriber<? super Word> subscriber) {
                final CountDownLatch latch = new CountDownLatch(1);
                final BmobWord bmobWord = new BmobWord(word, by);
                bmobWord.save(WordsApp.getContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Word word = new Word(bmobWord);
                        word.save();
                        subscriber.onNext(word);
                        subscriber.onCompleted();
                        latch.countDown();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        subscriber.onError(new ApiError(i, s));
                        latch.countDown();
                    }
                });
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
