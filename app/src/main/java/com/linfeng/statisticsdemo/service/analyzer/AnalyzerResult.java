package com.linfeng.statisticsdemo.service.analyzer;

import com.linfeng.statisticsdemo.service.AppRunRecord;

/**
 * Created by linfeng on 2019/4/22  09:50
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class AnalyzerResult {
    public AppRunRecord data;
    public int action;

    public interface ACTION {
        int KILL = 1;
        int NO_START_TODAY = 2;
    }
}
