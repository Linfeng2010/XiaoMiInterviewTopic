package com.linfeng.statisticsdemo.client.ui;

/**
 * Created by linfeng on 2019/4/21  15:45
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public interface MVPFactory {
    public IBasePresenter getPresenter();

    public IBaseModule getMpdule();

    public IBaseView getView();

}
