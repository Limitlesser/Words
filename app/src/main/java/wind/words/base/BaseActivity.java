package wind.words.base;

import android.annotation.TargetApi;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by wind on 2015/9/12.
 */
public class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected T binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(Color.parseColor("#2196F3"));
//        SlidrConfig config = new SlidrConfig.Builder()
//                .position(SlidrPosition.TOP)
//                .primaryColor(Color.parseColor("#2196F3"))
//                .secondaryColor(Color.parseColor("#2196F3"))
//                .build();
//        Slidr.attach(this, config);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setTranslucentStatus(boolean on) {
        if (on) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

}
