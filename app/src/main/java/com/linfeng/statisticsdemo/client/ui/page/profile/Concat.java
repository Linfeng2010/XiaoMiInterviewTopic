package com.linfeng.statisticsdemo.client.ui.page.profile;

import com.linfeng.statisticsdemo.client.ui.IBaseModule;
import com.linfeng.statisticsdemo.client.ui.IBasePresenter;
import com.linfeng.statisticsdemo.client.ui.IBaseView;
import com.linfeng.statisticsdemo.client.ui.page.profile.entity.AppInfo;

import java.util.List;

/**
 * Created by linfeng on 2019/4/21  15:23
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class Concat {

    public static abstract class ProfilePresenter extends IBasePresenter<ProfileView, ProfileModule> {

        public abstract AppInfo getAliveAppInfo(int proId);

        public abstract List<AppInfo> getAliveAppInfoList();

        //这里做一层桥接  让Profile 方便一些特殊操作 比如以下权限控制  参数转换  错误检查 Bridge主要用于控制对presenter的访问  下面的Module View类似
//        后面会通过一个抽象的工厂来切换实际的Presenter
        public static class ProfilePresenterBridge extends ProfilePresenter {
            private ProfilePresenter presenter;

            public ProfilePresenterBridge(ProfilePresenter presenter) {
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

            private List<AppInfo> getAliveAppInfoListWithBrige() {
                return presenter != null ? presenter.getAliveAppInfoList() : null;
            }

            @Override
            protected ProfileModule createModule() {
                return presenter != null ? presenter.createModule() : null;
            }

            @Override
            protected void start() {
                if (presenter != null) {
                    presenter.start();
                }
            }
        }
    }


    //Module
    public static abstract class ProfileModule implements IBaseModule {

        private ProfileModule mProfileModule;

        public abstract AppInfo getAliveAppInfo(int proId);


        public abstract List<AppInfo> getAliveAppInfoList();


        public static class ProfileModuleBridge extends ProfileModule {
            private ProfileModule mProfileModule;


            public ProfileModuleBridge(ProfileModule mProfileModule) {
                this.mProfileModule = mProfileModule;
            }

            public AppInfo getAliveAppInfo(int proId) {
                return getAliveAppInfoWithBrige(proId);
            }

            private AppInfo getAliveAppInfoWithBrige(int proId) {
                return mProfileModule != null ? mProfileModule.getAliveAppInfo(proId) : null;
            }


            public List<AppInfo> getAliveAppInfoList() {
                return getAliveAppInfoListWithBrige();
            }

            private List<AppInfo> getAliveAppInfoListWithBrige() {
                return mProfileModule != null ? mProfileModule.getAliveAppInfoList() : null;
            }
        }

    }

    public interface ProfileView extends IBaseView {

        public abstract void showAppInfo(AppInfo appInfo);

//        public abstract class showAppInfoList {
//            private ProfileView mProfielView;
//
//        }
    }
}
