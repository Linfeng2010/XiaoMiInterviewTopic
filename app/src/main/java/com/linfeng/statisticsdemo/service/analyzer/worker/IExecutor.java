package com.linfeng.statisticsdemo.service.analyzer.worker;

import com.linfeng.statisticsdemo.service.analyzer.IEventAnalyzer;

/**
 * Created by linfeng on 2019/4/22  03:01
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public interface IExecutor {
    void execute(Block runnable);
}
