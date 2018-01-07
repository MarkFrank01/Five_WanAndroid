package github.markfrank01.five_wanandroid.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import github.markfrank01.five_wanandroid.base.app.MyApplication;

/**
 * Created by MarkFrank01
 * description :
 */

@Module
public class ApplicationModule {

    private MyApplication myApplication;

    public ApplicationModule(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Provides
    Context provideApplicationContext(){
        return myApplication.getApplicationContext();
    }
}
