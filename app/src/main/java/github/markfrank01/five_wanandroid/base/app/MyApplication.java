package github.markfrank01.five_wanandroid.base.app;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

import github.markfrank01.five_wanandroid.di.component.ApplicationComponent;
import github.markfrank01.five_wanandroid.di.component.DaggerApplicationComponent;
import github.markfrank01.five_wanandroid.di.module.ApplicationModule;
import github.markfrank01.five_wanandroid.model.constant.Constant;

/**
 * Created by MarkFrank01
 * description :
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initApplicationComponent();
        myApplication = this;

        //init Bugly
        CrashReport.initCrashReport(getApplicationContext(), Constant.BUGLY_ID,false);
    }

    public static synchronized MyApplication getInstance(){
        return myApplication;

    }

    /**
     * init ApplicationComponent
     */
    private void initApplicationComponent(){
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
