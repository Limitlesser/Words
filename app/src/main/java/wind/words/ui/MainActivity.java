package wind.words.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import wind.words.R;
import wind.words.base.BaseActivity;

/**
 * Created by wind on 2015/9/12.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
}
