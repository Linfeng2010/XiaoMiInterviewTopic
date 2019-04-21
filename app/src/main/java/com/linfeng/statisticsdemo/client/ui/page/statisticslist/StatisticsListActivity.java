package com.linfeng.statisticsdemo.client.ui.page.statisticslist;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.linfeng.statisticsdemo.client.ui.BaseActivity;

/**
 * Created by linfeng on 2019/4/21  14:47
 * Email zhanglinfengdev@163.com
 * Function 这是一个查看统计数据库的一个界面
 */
public class StatisticsListActivity extends BaseActivity<Concat.StaPresenter> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initContentView() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected Concat.StaPresenter createPresenter() {

        return null;
    }
}
