package github.markfrank01.five_wanandroid.until.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import github.markfrank01.five_wanandroid.base.app.MyApplication;

/**
 * Created by jxy on 2018/6/12.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(MyApplication.getInstance()).load(path).into(imageView);
    }
}
