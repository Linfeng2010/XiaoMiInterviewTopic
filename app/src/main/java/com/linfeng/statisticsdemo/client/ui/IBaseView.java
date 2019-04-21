package com.linfeng.statisticsdemo.client.ui;

/**
 * Created by linfeng on 2019/4/21  14:49
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public interface IBaseView {

    void showDialog(String msg);

    void showToast();

    void showLoading(String msg);

    void hideLoading(String msg);
}
