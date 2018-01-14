package github.markfrank01.five_wanandroid.ui.knowledge.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import github.markfrank01.five_wanandroid.R;
import github.markfrank01.five_wanandroid.base.activity.BaseActivity;
import github.markfrank01.five_wanandroid.base.adapter.SimpleFragmentStateAdapter;
import github.markfrank01.five_wanandroid.data.knowledge.KnowledgeListBean;
import github.markfrank01.five_wanandroid.model.constant.Constant;
import github.markfrank01.five_wanandroid.model.constant.EventConstant;
import github.markfrank01.five_wanandroid.model.constant.MessageEvent;

/**
 * Created by WJC on 2018/10/7.
 */
public class KnowledgeClassifyActivity extends BaseActivity {

    @BindView(R.id.knowledge_toolbar)
    Toolbar mKnowledgeToolbar;
    @BindView(R.id.know_tab_layout)
    SlidingTabLayout mKnowledgeTabLayout;
    @BindView(R.id.knowledge_viewpager)
    ViewPager mKnowledgeViewpager;
    @BindView(R.id.float_button)
    FloatingActionButton mFloatButton;

    private List<String> titles;
    private List<Fragment> fragments;
    private SimpleFragmentStateAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge_classify;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mKnowledgeToolbar);
        mKnowledgeToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        switch (event.getCode()) {
            case EventConstant.KNOWLEDGELOADERR:
                showError();
                break;
        }
    }


    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
        adapter = new SimpleFragmentStateAdapter(getSupportFragmentManager(), fragments);
        if (getIntent().getBooleanExtra(Constant.HOMEPAGE_TAG, false)) {
            getHomepageBundleData();
        } else {
            getKnowledgeBundleData();
        }
    }

    /**
     * by knowledge list
     */
    private void getKnowledgeBundleData() {
        KnowledgeListBean knowledgeListBean = (KnowledgeListBean) getIntent().getSerializableExtra(Constant.KNOWLEDGE);
        if (knowledgeListBean != null) {
            fragments.clear();
            getSupportActionBar().setTitle(knowledgeListBean.getName());
            for (KnowledgeListBean.ChildrenBean childrenBean : knowledgeListBean.getChildren()) {
                titles.add(childrenBean.getName());

            }
        }
    }

    /**
     * by homepage list
     */
    private void getHomepageBundleData(){

    }

}
