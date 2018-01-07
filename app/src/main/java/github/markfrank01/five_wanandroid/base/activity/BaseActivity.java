package github.markfrank01.five_wanandroid.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import github.markfrank01.five_wanandroid.base.app.MyApplication;
import github.markfrank01.five_wanandroid.base.presenter.AbsPresenter;
import github.markfrank01.five_wanandroid.base.view.AbstractView;
import github.markfrank01.five_wanandroid.di.component.ActivityComponent;
import github.markfrank01.five_wanandroid.di.component.DaggerActivityComponent;
import github.markfrank01.five_wanandroid.di.component.DaggerApplicationComponent;
import github.markfrank01.five_wanandroid.model.constant.MessageEvent;
import github.markfrank01.five_wanandroid.until.network.NetUtils;
import github.markfrank01.five_wanandroid.until.network.NetworkBroadcastReceiver;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by MarkFrank01
 * description : BaseActivity
 */

public abstract class BaseActivity<T extends AbsPresenter> extends SupportActivity implements AbstractView, NetworkBroadcastReceiver.NetEvent {

    protected MyApplication context;
    protected BaseActivity activity;
    protected ActivityComponent mActivityComponent;
    private int netMobile;

    public static NetworkBroadcastReceiver.NetEvent eventActivity;

    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        context = MyApplication.getInstance();
        activity = this;
        eventActivity = this;
        initActivityComponent();
        initBind();
        initInject();
        onViewCreated();
        initToolbar();
        initUI();
        initData();
    }

    /**
     * get Ui layout
     */
    protected abstract int getLayoutId();

    /**
     * init ActivityComponent
     */
    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(MyApplication.getInstance().getApplicationComponent())
                .build();
    }

    /**
     * init ButterKnife
     */
    public void initBind() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        NetUtils.init(MyApplication.getInstance());
    }

    /**
     * init dagger
     */
    protected void initInject() {

    }

    /**
     * import view
     */
    protected void onViewCreated() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * init Toolbar
     */
    protected void initToolbar() {

    }

    /**
     * init UI
     */
    protected abstract void initUI();

    /**
     * init Data
     */
    protected abstract void initData();

    //work in Ui thread
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //判断网络变化后类型
    @Override
    public void onNetChange(int newMobile) {
        this.netMobile = newMobile;
        isNetConnect();
    }

    //判断是否有网络
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

    @Override
    public void setVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setInVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.INVISIBLE);
        }
    }

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
}

