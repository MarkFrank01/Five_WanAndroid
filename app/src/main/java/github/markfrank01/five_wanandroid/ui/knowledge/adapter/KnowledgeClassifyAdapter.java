package github.markfrank01.five_wanandroid.ui.knowledge.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import github.markfrank01.five_wanandroid.R;
import github.markfrank01.five_wanandroid.data.knowledge.KnowledgeClassifyListBean;

/**
 * Created by WJC on 2018/10/7.
 */
public class KnowledgeClassifyAdapter extends BaseQuickAdapter<KnowledgeClassifyListBean.DatasBean,BaseViewHolder> {

    public KnowledgeClassifyAdapter(int layoutResId, @Nullable List<KnowledgeClassifyListBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeClassifyListBean.DatasBean item) {
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_content, item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_author, item.getAuthor());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_time, item.getNiceDate());
        }
        if (!TextUtils.isEmpty(item.getChapterName())) {
            String classifyName = item.getSuperChapterName() + " / " + item.getChapterName();
            helper.setText(R.id.tv_type, classifyName);
        }
        helper.addOnClickListener(R.id.image_collect);
        helper.setImageResource(R.id.image_collect, item.isCollect() ? R.drawable.icon_collect : R.drawable.icon_no_collect);
    }
}
