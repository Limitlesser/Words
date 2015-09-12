package wind.words;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by wind on 2015/9/12.
 */
public class WordsApp extends Application {

    private static WordsApp app;

    public static WordsApp getContext() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Bmob.initialize(this, Constants.BombAppId);
    }
}
