package github.markfrank01.five_wanandroid.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MarkFrank01
 * description :
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    Activity provideActivity(){
        return mActivity;
    }
}
