package com.linfeng.statisticsdemo.client.ui.page.profile;

import com.linfeng.saatisticsdemo.R;
import com.linfeng.statisticsdemo.client.ui.BaseActivity;
import com.linfeng.statisticsdemo.client.ui.IBasePresenter;
import com.linfeng.statisticsdemo.client.ui.MVPFactory;
import com.linfeng.statisticsdemo.client.ui.page.profile.entity.AppInfo;
import com.linfeng.statisticsdemo.client.ui.page.profile.factory.ProFactory_Default;

/**
 * Created by linfeng on 2019/4/21  14:48
 * Email zhanglinfengdev@163.com
 * Function 这是一个首页  显示当前正在检测到的信息
 */
public class ProfileActivity extends BaseActivity<Concat.ProfilePresenter> implements Concat.ProfileView {

    private MVPFactory mvpFactory;
    private Concat.ProfilePresenter profilePresenterBridge;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initContentView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected Concat.ProfilePresenter createPresenter() {
        //这里可用通过 切换工厂的形式来切换当前页面的Preseter
        mvpFactory = new ProFactory_Default();
        final IBasePresenter presenter = mvpFactory.getPresenter();
        profilePresenterBridge = new Concat.ProfilePresenter.ProfilePresenterBridge(((Concat.ProfilePresenter) presenter));
        //这的的Activity最为一个使用的客户端 BaseActivity最终拿到的是一个桥接类 实现了Presenter的分离 并且程序运行期间可以动态的改变实际的Presenter
        //这里本质就是一个装饰者  Activity可以更具自己的需求对这个装饰者做一些增强
        return profilePresenterBridge;
    }


    @Override
    public void showAppInfo(AppInfo appInfo) {

    }
}
