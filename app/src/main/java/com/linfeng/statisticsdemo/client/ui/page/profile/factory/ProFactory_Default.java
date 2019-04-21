package com.linfeng.statisticsdemo.client.ui.page.profile.factory;

import com.linfeng.statisticsdemo.client.ui.IBaseModule;
import com.linfeng.statisticsdemo.client.ui.IBasePresenter;
import com.linfeng.statisticsdemo.client.ui.IBaseView;
import com.linfeng.statisticsdemo.client.ui.MVPFactory;
import com.linfeng.statisticsdemo.client.ui.page.profile.model.ProfileModel_Default;
import com.linfeng.statisticsdemo.client.ui.page.profile.presenter.ProfilePreseter_Default;

/**
 * Created by linfeng on 2019/4/21  15:48
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class ProFactory_Default implements MVPFactory {

    @Override
    public IBasePresenter getPresenter() {
        return new ProfilePreseter_Default();
    }

    @Override
    public IBaseModule getMpdule() {
        return new ProfileModel_Default();
    }

    @Override
    public IBaseView getView() {
        return null;
    }
}
