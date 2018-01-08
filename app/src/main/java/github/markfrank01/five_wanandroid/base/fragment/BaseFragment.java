package github.markfrank01.five_wanandroid.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import github.markfrank01.five_wanandroid.base.app.MyApplication;
import github.markfrank01.five_wanandroid.base.presenter.AbsPresenter;
import github.markfrank01.five_wanandroid.base.view.AbstractView;
import github.markfrank01.five_wanandroid.di.component.DaggerFragmentComponent;
import github.markfrank01.five_wanandroid.di.component.FragmentComponent;
import github.markfrank01.five_wanandroid.di.module.FragmentModule;
import github.markfrank01.five_wanandroid.model.constant.MessageEvent;
import github.markfrank01.five_wanandroid.until.network.NetUtils;
import github.markfrank01.five_wanandroid.until.network.NetworkBroadcastReceiver;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by MarkFrank01
 * description :
 */

public abstract class BaseFragment <T extends AbsPresenter> extends SupportFragment implements AbstractView,NetworkBroadcastReceiver.NetEvent{

    public View rootView;
    protected Activity activity;
    protected MyApplication context;
    protected FragmentComponent mFragmentComponent;
    private int netMobile;

    public BaseFragment(){}

    @Inject
    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResID(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        activity = getActivity();
        context = MyApplication.getInstance();
        initFragmentComponent();
        initInjector();
        onViewCreated();
        initBind(view);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initUI();
        initData();
    }

    /**
     * 获取当前界面的UI布局
     */
    public abstract int getLayoutResID();


    private void initFragmentComponent(){
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(MyApplication.getInstance().getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    /**
     * dagger初始化
     */
    protected void initInjector() {
    }

    protected void onViewCreated(){
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
    }

    public void initBind(View view) {
        ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        NetUtils.init(MyApplication.getInstance());
    }

    /**
     * 初始化界面
     */
    protected abstract void initUI();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onDestroy() {
        if (mPresenter!=null){
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }

    /**
     * 判断有无网络
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == NetUtils.NETWORK_WIFI) {
            return true;
        } else if (netMobile == NetUtils.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetUtils.NETWORK_NONE) {
            return false;
        }
        return false;
    }

    /**
     * 设置可见
     */
    @Override
    public void setVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置隐藏
     */
    @Override
    public void setInVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置不可见
     */
    @Override
    public void setGone(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showNeteaseLoading() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void reload() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){

    }
}
