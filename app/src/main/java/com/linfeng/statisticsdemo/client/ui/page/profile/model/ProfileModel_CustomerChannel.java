package com.linfeng.statisticsdemo.client.ui.page.profile.model;

import com.linfeng.statisticsdemo.client.ui.page.profile.Concat;
import com.linfeng.statisticsdemo.client.ui.page.profile.entity.AppInfo;

import java.util.List;

/**
 * Created by linfeng on 2019/4/21  16:14
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class ProfileModel_CustomerChannel extends Concat.ProfileModule {

    //    这是一个特殊定制的Module  这里主要是让Module符合开闭原则  增强扩展性。 这里也可以组合原来的Module实现代码复用
    @Override
    public AppInfo getAliveAppInfo(int proId) {
        return null;
    }

    @Override
    public List<AppInfo> getAliveAppInfoList() {
        return null;
    }
}
