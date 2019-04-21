package com.linfeng.statisticsdemo.service;

/**
 * Created by linfeng on 2019/4/21  19:57
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class IServiceWrapper  {
    private IStatistics mIStatistics;

    public IServiceWrapper(IStatistics mIStatistics) {
        this.mIStatistics = mIStatistics;
    }
}
