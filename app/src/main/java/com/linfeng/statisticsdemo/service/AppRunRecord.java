package com.linfeng.statisticsdemo.service;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmModule;
import io.realm.annotations.Required;

/**
 * Created by linfeng on 2019/4/22  01:17
 * Email zhanglinfengdev@163.com
 * Function details...
 */
@RealmModule
public class AppRunRecord implements RealmModel {
    @Required
    @PrimaryKey
    public long pId;
    @Required
    public String appName;

    public int proState;//0 不活跃死的  1 正在运行的活跃的

    public long startTime;//启动时间
    public long stopTime;//推出时间

    //进程是否跨天
    public boolean strideDay;

    //    进程创建的日期
    public int year;
    public int month;
    public int dayOfMonth;

    //    进程创建的日期
    public int yearStop;
    public int monthStop;
    public int dayOfMonthStop;

    public long getConsumeTime() {
        return Math.abs(startTime - stopTime);
    }

    public boolean isStrideDay() {
        boolean year = this.year == this.yearStop;
        boolean month = this.month == this.monthStop;
        boolean day = this.dayOfMonth == this.dayOfMonthStop;
        strideDay = year && month && day;
        return strideDay;
    }


}
