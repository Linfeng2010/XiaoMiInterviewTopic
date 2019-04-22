package com.linfeng.statisticsdemo.service.analyzer;

import com.linfeng.statisticsdemo.isystem.AppRunStateEvent;
import com.linfeng.statisticsdemo.service.AppRunRecord;

/**
 * Created by linfeng on 2019/4/22  02:46
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public interface IEventAnalyzer {
    void analyzer(AppRunStateEvent event);

    interface AnalyzerCallback {
        void onAnanyzerResult(AnalyzerResult result);
    }
}
