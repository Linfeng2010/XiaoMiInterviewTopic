package com.linfeng.statisticsdemo.client.ui;

import android.view.View;

/**
 * Created by linfeng on 2019/4/21  16:41
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public interface ViewHelper {
    void setText(int id, String msg);

    void setImage(int id, int msg);

    void gone(int id);

    void visible(int id);

    void invisible(int id);

    View findVWithC(int id);
}
