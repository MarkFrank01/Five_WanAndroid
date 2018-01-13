package github.markfrank01.five_wanandroid.ui.main.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import github.markfrank01.five_wanandroid.R;
import github.markfrank01.five_wanandroid.base.activity.BaseRootActivity;
import github.markfrank01.five_wanandroid.contract.main.ArticleDetailContact;
import github.markfrank01.five_wanandroid.model.constant.Constant;
import github.markfrank01.five_wanandroid.presenter.main.ArticleDetailPresenter;

/**
 * Created by WJC on 2018/9/30.
 */
@SuppressLint("SetJavaScriptEnabled")
public class ArticleDetailsActivity extends BaseRootActivity<ArticleDetailPresenter> implements ArticleDetailContact.View {

    @BindView(R.id.article_detail_web_view)
    FrameLayout mWebContent;
    @BindView(R.id.article_toolbar)
    Toolbar mArticleToolbar;

    private String title;
    private String articleLink;
    private int articleId;
    private boolean isCollect;
    private AgentWeb mAgentWeb;
    private int collectCode = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_details;
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initUI() {
        getBundleData();
        setSupportActionBar(mArticleToolbar);
        getSupportActionBar().setTitle(title);
        mArticleToolbar.setNavigationOnClickListener(v->onBackPressedSupport());
    }

    private void getBundleData(){
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString(Constant.ARTICLE_TITLE);
        articleLink = bundle.getString(Constant.ARTICLE_LINK);
        articleId = bundle.getShort(Constant.ARTICLE_ID);
        isCollect = bundle.getBoolean(Constant.ARTICLE_IS_COLLECT);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void collectArticleOK(String info) {

    }

    @Override
    public void collectArticleErr(String info) {

    }

    @Override
    public void cancelCollectArticleOK(String info) {

    }

    @Override
    public void cancelCollectArticleErr(String info) {

    }
}
