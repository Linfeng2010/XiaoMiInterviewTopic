package com.linfeng.statisticsdemo.client.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linfeng.saatisticsdemo.R;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linfeng on 2019/4/21  14:49
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity implements IBaseView, ViewHelper, SwallowUIUpdateExceptionHelper {

    public Map<Integer, View> mViewCache;

    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setupContentView();
        initContentView();
        initVMapCache();
    }

    protected abstract void initContentView();

    private void setupContentView() {
        final ViewGroup content = (ViewGroup) findVWithC(R.id.fl_base_content);
        final int layoutId = getLayoutId();
        if (layoutId > 0) {
            getLayoutInflater().inflate(layoutId, content, true);
        } else {
            content.addView(getDefaultContentView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    private View getDefaultContentView() {
        final TextView textView = new TextView(this);
        textView.setText("我是一个默认的View");
        return textView;
    }

    protected abstract int getLayoutId();

    private void initVMapCache() {
        mViewCache = new HashMap<>();
        this.mPresenter = createPresenter();
        this.mPresenter.attachView(this);
    }

    protected abstract P createPresenter();

    @Override
    public void setText(int id, String msg) {
        final View v = findVWithC(id);
        itray(() -> ((TextView) v).setText(msg));
    }

    @Override
    public View findVWithC(int id) {
        if (mViewCache.containsKey(id)) {
            final View viewById = findViewById(id);
            mViewCache.put(id, viewById);
        }
        return mViewCache.get(id);
    }

    @Override
    public void setImage(int id, int resId) {
        final View v = findVWithC(id);
        itray(() -> ((ImageView) v).setImageResource(resId));
    }

    @Override
    public void gone(int id) {
        final View vWithC = findVWithC(id);
        itray(() -> vWithC.setVisibility(View.GONE));
    }

    @Override
    public void visible(int id) {
        final View vWithC = findVWithC(id);
        itray(() -> vWithC.setVisibility(View.VISIBLE));
    }

    @Override
    public void invisible(int id) {
        final View vWithC = findVWithC(id);
        itray(() -> vWithC.setVisibility(View.INVISIBLE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseViewCache();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected final void releaseViewCache() {
        if (mViewCache != null) {
            mViewCache.clear();
            mViewCache = null;
        }
    }

    //    这里主要是避免一些因为服务端数据原因导致的APP崩溃问题   这里做成几个策略运行时动态选择
    @Override
    public void itray(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            boolean IS_DEBUG = true;
            if (IS_DEBUG) {
                Toast.makeText(this, MessageFormat.format("UIException:{1}", e.getMessage()), Toast.LENGTH_SHORT).show();
            } else {
                //这里可做上报
            }
        }
    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void showToast() {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading(String msg) {

    }
}
