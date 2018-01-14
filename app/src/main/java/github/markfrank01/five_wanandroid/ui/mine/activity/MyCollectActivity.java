package github.markfrank01.five_wanandroid.ui.mine.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import github.markfrank01.five_wanandroid.R;
import github.markfrank01.five_wanandroid.base.activity.BaseRootActivity;
import github.markfrank01.five_wanandroid.contract.mine.CollectContract;
import github.markfrank01.five_wanandroid.data.main.HomePageArticleBean;
import github.markfrank01.five_wanandroid.model.constant.Constant;
import github.markfrank01.five_wanandroid.presenter.mine.CollectPresenter;
import github.markfrank01.five_wanandroid.ui.main.activity.ArticleDetailsActivity;
import github.markfrank01.five_wanandroid.ui.mine.adapter.CollectAdapter;
import github.markfrank01.five_wanandroid.until.app.ToastUtil;

/**
 * Created by WJC on 2018/9/29.
 */
public class MyCollectActivity extends BaseRootActivity<CollectPresenter> implements CollectContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.toolbar_collect)
    Toolbar mToolBarCollect;

    private List<HomePageArticleBean.DatasBean> collectList;
    private CollectAdapter mAdapter;
    private int poi;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolBarCollect);
        getSupportActionBar().setTitle(getString(R.string.my_collect));
        mToolBarCollect.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        mRv.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    protected void initData() {
        setRefresh();
        collectList = new ArrayList<>();
        mPresenter.getCollectList(0);
        mAdapter = new CollectAdapter(R.layout.item_homepage, collectList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void getCollectListOK(HomePageArticleBean articleBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            collectList.addAll(articleBean.getDatas());
            mAdapter.replaceData(articleBean.getDatas());
        } else {
            collectList.addAll(articleBean.getDatas());
            mAdapter.addData(articleBean.getDatas());
        }
        showNormal();
    }

    @Override
    public void getCollectListErr(String info) {
        showError();
    }

    @Override
    public void cancelCollectOk() {
        ToastUtil.show(activity,getString(R.string.cancel_collect_success));
        mAdapter.remove(poi);
    }

    @Override
    public void cancelCollectErr(String info) {
        ToastUtil.show(activity,info);
    }

    @Override
    public void reload() {
        showLoading();
        mPresenter.getCollectList(0);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ARTICLE_TITLE,mAdapter.getData().get(position).getTitle());
        bundle.putString(Constant.ARTICLE_LINK,mAdapter.getData().get(position).getLink());
        bundle.putInt(Constant.ARTICLE_ID,mAdapter.getData().get(position).getId());
        bundle.putBoolean(Constant.ARTICLE_IS_COLLECT,Constant.TRUE);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity,view,getString(R.string.share_view));
        //jump to ArticleDetail
        startActivity(new Intent(activity,ArticleDetailsActivity.class).putExtras(bundle),options.toBundle());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        poi = position;
        mPresenter.cancelCollect(mAdapter.getData().get(position).getId());
    }

    /**
     * SmartRefreshLayout refresh Loading
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
}
