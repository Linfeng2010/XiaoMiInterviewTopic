package com.linfeng.statisticsdemo.isystem;

/**
 * Created by linfeng on 2019/4/22  20:13
 * Email zhanglinfengdev@163.com
 * Function details...  系统远程代理类 暴露一些专用接口
 */
public interface SystemProxy {
    void killPro(int pId);

    void banStarted(String packageName,long time);
}
