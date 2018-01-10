package github.markfrank01.five_wanandroid.ui.main.viewholder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import butterknife.BindView;
import github.markfrank01.five_wanandroid.R;

public class HomePageViewHolder extends BaseViewHolder {

    @BindView(R.id.tv_author)
    TextView mTvAuthor;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.image_collect)
    ImageView mImageCollect;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_tag)
    TextView mTvTag;

    public HomePageViewHolder(View view) {
        super(view);
    }
}
