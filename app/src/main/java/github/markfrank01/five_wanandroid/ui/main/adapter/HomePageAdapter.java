package github.markfrank01.five_wanandroid.ui.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import github.markfrank01.five_wanandroid.data.main.HomePageArticleBean;
import github.markfrank01.five_wanandroid.ui.main.viewholder.HomePageViewHolder;

/**
 * Created by WJC on 2018/9/19.
 */
public class HomePageAdapter extends BaseQuickAdapter<HomePageArticleBean.DatasBean,HomePageViewHolder> {

    public HomePageAdapter(int layoutResId, @Nullable List<HomePageArticleBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(HomePageViewHolder helper, HomePageArticleBean.DatasBean item) {

    }
}
