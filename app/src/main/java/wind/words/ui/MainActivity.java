package wind.words.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import wind.words.BR;
import wind.words.R;
import wind.words.base.BaseActivity;
import wind.words.databinding.ActivityMainBinding;
import wind.words.model.api.Api;
import wind.words.model.api.ApiError;
import wind.words.model.api.ApiService;
import wind.words.model.bmob.BmobWord;
import wind.words.model.data.Word;


/**
 * Created by wind on 2015/9/12.
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private static final String TAG = MainActivity.class.getSimpleName();

    WordsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WordsAdapter();
        binding.recyclerView.setAdapter(mAdapter);
        binding.ptrFrame.setKeepHeaderWhenRefresh(true);
        StoreHouseHeader header = new StoreHouseHeader(this);
        header.setPadding(0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()), 0, 0);
        header.initWithString("Words");
        header.setTextColor(Color.RED);
        header.setLineWidth(5);
        binding.ptrFrame.setHeaderView(header);
        binding.ptrFrame.addPtrUIHandler(header);
        binding.ptrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                getWords();
            }
        });
        // the following are default settings
        binding.ptrFrame.setResistance(1.7f);
        binding.ptrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        binding.ptrFrame.setDurationToClose(200);
        binding.ptrFrame.setDurationToCloseHeader(1000);
        binding.ptrFrame.setPullToRefresh(false);
        binding.ptrFrame.autoRefresh();
    }

    private void getWords() {
        ApiService.getApi().listWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Word>>() {
                    @Override
                    public void call(List<Word> words) {
                        mAdapter.set(words);
                        binding.ptrFrame.refreshComplete();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    public void addWord(View v) {

    }

    class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {

        List<Word> words = new ArrayList<>();

        public void set(List<Word> words) {
            this.words = words;
            notifyDataSetChanged();
        }

        public void add(Word word) {
            words.add(word);
            notifyItemRangeInserted(words.size() - 1, 1);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_word, parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.binding.setVariable(BR.word, words.get(position));
            holder.binding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return words.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ViewDataBinding binding;

            public ViewHolder(ViewDataBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
