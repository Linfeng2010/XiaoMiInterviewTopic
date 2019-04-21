package com.linfeng.statisticsdemo.client.ui;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by linfeng on 2019/4/21  14:49
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public abstract class IBasePresenter<V extends IBaseView, M extends IBaseModule> {
    protected IBaseView mViewProxy;
    protected M mModule;
    private ViewInvocationHandler viewInvocationHandler;


    public void attachView(V view) {
        viewInvocationHandler = new ViewInvocationHandler();
        this.mViewProxy = viewInvocationHandler.bind(view);
        if (this.mModule == null) {
            this.mModule = createModule();
        }
    }

    public void detachView() {
        if (viewInvocationHandler != null)
            viewInvocationHandler.clear();
        if (mModule != null)
            mModule = null;
    }

    protected abstract M createModule();


    protected abstract void start();


    //    代理View 防止presenter引起View的内存泄漏
    public class ViewInvocationHandler implements InvocationHandler {

        private WeakReference<IBaseView> sBaseViewRef;

        public IBaseView bind(IBaseView view) {
            sBaseViewRef = new WeakReference<>(view);
            final IBaseView o = (IBaseView) Proxy.newProxyInstance(IBaseView.class.getClassLoader(), IBaseView.class.getInterfaces(), this);
            return o;
        }


        private IBaseView get() {
            return sBaseViewRef.get();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isViewAttached()) {
                method.invoke(get(), args);
            }
            return null;
        }

        private boolean isViewAttached() {
            return get() != null;
        }

        public void clear() {
            if (sBaseViewRef != null) {
                sBaseViewRef.clear();
            }
        }
    }


}
