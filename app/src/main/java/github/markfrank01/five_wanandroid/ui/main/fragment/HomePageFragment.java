package github.markfrank01.five_wanandroid.ui.main.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.just.agentweb.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import github.markfrank01.five_wanandroid.R;
import github.markfrank01.five_wanandroid.base.fragment.BaseRootFragment;
import github.markfrank01.five_wanandroid.contract.main.HomePageContract;
import github.markfrank01.five_wanandroid.data.login.UserInfo;
import github.markfrank01.five_wanandroid.data.main.BannerBean;
import github.markfrank01.five_wanandroid.data.main.HomePageArticleBean;
import github.markfrank01.five_wanandroid.model.constant.Constant;
import github.markfrank01.five_wanandroid.model.constant.EventConstant;
import github.markfrank01.five_wanandroid.model.constant.MessageEvent;
import github.markfrank01.five_wanandroid.presenter.main.HomePagePresenter;
import github.markfrank01.five_wanandroid.ui.login.LoginActivity;
import github.markfrank01.five_wanandroid.ui.main.activity.ArticleDetailsActivity;
import github.markfrank01.five_wanandroid.ui.main.adapter.HomePageAdapter;
import github.markfrank01.five_wanandroid.until.app.JumpUtil;
import github.markfrank01.five_wanandroid.until.app.LogUtil;
import github.markfrank01.five_wanandroid.until.app.SharedPreferenceUtil;
import github.markfrank01.five_wanandroid.until.app.ToastUtil;
import github.markfrank01.five_wanandroid.until.glide.GlideImageLoader;

/**
 * Created by MarkFrank01
 * description :
 */

public class HomePageFragment extends BaseRootFragment<HomePagePresenter> implements HomePageContract.View, HomePageAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<HomePageArticleBean.DatasBean> articleList;
    private List<String> linkList;
    private List<String> imageList;
    private List<String> titleList;
    private HomePageAdapter mAdapter;
    private Banner banner;
    private LinearLayout bannerView;
    private int clickPosition;

    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        mRv.setLayoutManager(new LinearLayoutManager(context));
        bannerView = (LinearLayout) getLayoutInflater().inflate(R.layout.view_banner, null);
        banner = bannerView.findViewById(R.id.banner);
        bannerView.removeView(banner);
        bannerView.addView(banner);
    }

    @Override
    protected void initData() {
        setRefresh();
        articleList = new ArrayList<>();
        linkList = new ArrayList<>();
        imageList = new ArrayList<>();
        titleList = new ArrayList<>();
        if (SharedPreferenceUtil.get(activity, Constant.USERNAME, Constant.DEFAULT).equals(Constant.DEFAULT)) {
            mPresenter.getBanner();
            mPresenter.getHomepageList(0);
        } else {
            mPresenter.loginAndLoad();
        }

        mAdapter = new HomePageAdapter(R.layout.item_homepage, articleList);
        mAdapter.addHeaderView(bannerView);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRv.setAdapter(mAdapter);
    }

    public void onMessageEvent(MessageEvent event) {
        switch (event.getCode()) {
            case EventConstant.MAINSCROLLTOTOP:
                mRv.smoothScrollToPosition(0);
                break;
            case EventConstant.REFRESHHOMEPAGE:
                mPresenter.getHomepageList(0);
                break;
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    @Override
    public void getHomepageListOk(HomePageArticleBean dataBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            articleList = dataBean.getDatas();
            mAdapter.replaceData(dataBean.getDatas());
        } else {
            articleList.addAll(dataBean.getDatas());
            mAdapter.addData(dataBean.getDatas());
        }
        showNormal();
    }

    @Override
    public void getHomepageListErr(String info) {
        showError();
    }

    @Override
    public void getBannerOk(List<BannerBean> bannerBean) {
        titleList.clear();
        imageList.clear();
        for (BannerBean bean : bannerBean) {
            linkList.add(bean.getUrl());
            titleList.add(bean.getTitle());
            imageList.add(bean.getImagePath());
        }

        if (!activity.isDestroyed()) {
            activity.runOnUiThread(() -> banner.setImageLoader(new GlideImageLoader())
                    .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                    .setImages(imageList)
                    .setBannerAnimation(Transformer.DepthPage)
                    .setBannerTitles(titleList)
                    .isAutoPlay(true)
                    .setDelayTime(5000)
                    .setIndicatorGravity(BannerConfig.RIGHT)
                    .start());
        }
        banner.setOnBannerListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.ARTICLE_TITLE, titleList.get(position));
            bundle.putString(Constant.ARTICLE_LINK, linkList.get(position));
            if (!TextUtils.isEmpty(linkList.get(position))) {
                //jump to ArticleDetails
                JumpUtil.overlay(context,ArticleDetailsActivity.class,bundle);
            }
        });


    }

    @Override
    public void getBannerErr(String info) {
        LogUtil.e(info);
    }

    @Override
    public void loginSuccess(UserInfo userInfo) {
        ToastUtil.show(activity, getString(R.string.auto_login_ok));
        SharedPreferenceUtil.put(activity, Constant.ISLOGIN, true);
        EventBus.getDefault().post(new MessageEvent(EventConstant.LOGINSUCCESS, ""));

    }

    @Override
    public void loginErr(String info) {
        ToastUtil.show(activity, info);
    }

    @Override
    public void collectArticleOK(String info) {
        if (mAdapter != null && mAdapter.getData().size() > clickPosition) {
            ToastUtil.show(activity,getString(R.string.collect_success));
            mAdapter.getData().get(clickPosition).setCollect(true);
            mAdapter.setData(clickPosition,mAdapter.getData().get(clickPosition));
        }
    }

    @Override
    public void collectArticleErr(String info) {
        ToastUtil.show(activity,getString(R.string.please_login));
        //jump to login
        JumpUtil.overlay(activity,LoginActivity.class);

    }

    @Override
    public void cancelCollectArticleOK(String info) {

        if (mAdapter!=null&&mAdapter.getData().size()>clickPosition){
            ToastUtil.show(activity,getString(R.string.cancel_collect_success));
            mAdapter.getData().get(clickPosition).setCollect(false);
            mAdapter.setData(clickPosition,mAdapter.getData().get(clickPosition));
        }
    }

    @Override
    public void cancelCollectArticleErr(String info) {
        ToastUtil.show(activity, info);
    }

    @Override
    public void reload() {
        showLoading();
        mPresenter.getBanner();
        mPresenter.autoRefresh();
    }

    /**
     * SmartRefreshLayout Fresh Load
     */
    private void setRefresh() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.autoRefresh();
            refreshLayout.finishRefresh(1000);
        });

        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore(1000);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ARTICLE_TITLE, mAdapter.getData().get(position).getTitle());
        bundle.putString(Constant.ARTICLE_LINK, mAdapter.getData().get(position).getLink());
        bundle.putInt(Constant.ARTICLE_ID, mAdapter.getData().get(position).getId());
        bundle.putBoolean(Constant.ARTICLE_IS_COLLECT, mAdapter.getData().get(position).isCollect());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.share_view));
        //jump to ArticleDetails
        startActivity(new Intent(activity,ArticleDetailsActivity.class).putExtras(bundle),options.toBundle());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        clickPosition = position;
        switch (view.getId()){
            case R.id.image_collect:
                if ((Boolean) SharedPreferenceUtil.get(context, Constant.ISLOGIN, Constant.FALSE)) {
                    if (mAdapter.getData().get(clickPosition).isCollect()) {
                        mPresenter.cancelCollectArticle(mAdapter.getData().get(clickPosition).getId());
                    } else {
                        mPresenter.collectArticle(mAdapter.getData().get(clickPosition).getId());
                    }
                } else {
                    ToastUtil.show(activity, getString(R.string.please_login));
                    //jump to login
                    JumpUtil.overlay(activity,LoginActivity.class);
                }
                break;
            case R.id.tv_type:
                //jump to Knowledge
                break;
        }
    }
}
