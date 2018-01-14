package github.markfrank01.five_wanandroid.ui.knowledge.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import github.markfrank01.five_wanandroid.R;
import github.markfrank01.five_wanandroid.base.fragment.BaseRootFragment;
import github.markfrank01.five_wanandroid.contract.knowledge.KnowledgeClassifyContract;
import github.markfrank01.five_wanandroid.data.knowledge.KnowledgeClassifyListBean;
import github.markfrank01.five_wanandroid.model.constant.Constant;
import github.markfrank01.five_wanandroid.presenter.knowledge.KnowledgeClassifyPresenter;
import github.markfrank01.five_wanandroid.ui.knowledge.adapter.KnowledgeClassifyAdapter;

/**
 * Created by WJC on 2018/10/7.
 */
public class KnowledgeListFragment extends BaseRootFragment<KnowledgeClassifyPresenter> implements KnowledgeClassifyContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private int cid,clickPosition;
    private List<KnowledgeClassifyListBean.DatasBean> knowledgeList;
    private KnowledgeClassifyAdapter mAdapter;

    public static KnowledgeListFragment getInstance(int cid){
        KnowledgeListFragment fragment = new KnowledgeListFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.KNOWLEDGE_CID,cid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_knowledge_list;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        mRv.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    protected void initData() {
        setRefresh();
        knowledgeList = new ArrayList<>();
        if (getArguments()!=null){
            cid = getArguments().getInt(Constant.KNOWLEDGE_CID);
            mPresenter.getKnowledgeClassifyList(0,cid);
        }
        mAdapter = new KnowledgeClassifyAdapter(R.layout.item_homepage,knowledgeList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRv.setAdapter(mAdapter);
    }

    private void setRefresh(){

    }

    @Override
    public void getKnowledgeClassifyListOk(KnowledgeClassifyListBean knowledgeClassifyListBean, boolean isRefresh) {

    }

    @Override
    public void getKnowledgeClassifyListErr(String info) {

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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
