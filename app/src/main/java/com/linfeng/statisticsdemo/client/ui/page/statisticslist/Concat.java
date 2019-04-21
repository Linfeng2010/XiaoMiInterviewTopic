package com.linfeng.statisticsdemo.client.ui.page.statisticslist;

import com.linfeng.statisticsdemo.client.ui.IBaseModule;
import com.linfeng.statisticsdemo.client.ui.IBasePresenter;
import com.linfeng.statisticsdemo.client.ui.IBaseView;
import com.linfeng.statisticsdemo.client.ui.page.profile.entity.AppInfo;

import java.util.List;

/**
 * Created by linfeng on 2019/4/21  15:27
 * Email zhanglinfengdev@163.com
 * Function details...
 */

public class Concat {

    public static abstract class StaPresenter extends IBasePresenter<StaView, StaModule> {

        public abstract AppInfo getAliveAppInfo(int proId);

        public abstract List<AppInfo> getAliveAppInfoList();

        //这里做一层桥接  让Profile 方便一些特殊操作 比如以下权限控制  参数转换  错误检查 Bridge主要用于控制对presenter的访问  下面的Module View类似
//        后面会通过一个抽象的工厂来切换实际的Presenter
        private static class StaPresenterBridge {
            private StaPresenter presenter;

            public StaPresenterBridge(StaPresenter presenter) {
                this.presenter = presenter;
            }

            public AppInfo getAliveAppInfo(int proId) {
                return getAliveAppInfoWithBrige(proId);
            }

            private AppInfo getAliveAppInfoWithBrige(int proId) {
                return presenter != null ? presenter.getAliveAppInfo(proId) : null;
            }


            public List<AppInfo> getAliveAppInfoList() {
                return getAliveAppInfoListWithBrige();
            }

            public List<AppInfo> getAliveAppInfoListWithBrige() {
                return presenter != null ? presenter.getAliveAppInfoList() : null;
            }
        }
    }


    //Module
    public static abstract class StaModule implements IBaseModule {

        private StaModule mStaModule;

        public abstract AppInfo getAliveAppInfo(int proId);


        public abstract List<AppInfo> getAliveAppInfoList();


        public static class StaModuleBridge {
            private Concat.StaModule mStaModule;


            public StaModuleBridge(StaModule mStaModule) {
                this.mStaModule = mStaModule;
            }

            public AppInfo getAliveAppInfo(int proId) {
                return getAliveAppInfoWithBrige(proId);
            }

            private AppInfo getAliveAppInfoWithBrige(int proId) {
                return mStaModule != null ? mStaModule.getAliveAppInfo(proId) : null;
            }


            public List<AppInfo> getAliveAppInfoList() {
                return getAliveAppInfoListWithBrige();
            }

            private List<AppInfo> getAliveAppInfoListWithBrige() {
                return mStaModule != null ? mStaModule.getAliveAppInfoList() : null;
            }
        }

    }

    public static abstract class StaView implements IBaseView {

        public abstract void showAppInfo(AppInfo appInfo);

        public abstract class showAppInfoList {
            private StaView mProfielView;

        }
    }
}

