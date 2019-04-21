package com.linfeng.statisticsdemo.client.ui.page.profile.model;

import com.linfeng.statisticsdemo.client.ui.page.profile.Concat;
import com.linfeng.statisticsdemo.client.ui.page.profile.entity.AppInfo;

import java.util.List;

/**
 * Created by linfeng on 2019/4/21  16:14
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class ProfileModel_Default extends Concat.ProfileModule {

    //    设施一个默认module
    @Override
    public AppInfo getAliveAppInfo(int proId) {
        return null;
    }

    @Override
    public List<AppInfo> getAliveAppInfoList() {
        return null;
    }
}
