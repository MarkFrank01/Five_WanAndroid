package github.markfrank01.five_wanandroid.di.component;

import android.app.Activity;

import dagger.Component;
import github.markfrank01.five_wanandroid.di.module.ActivityModule;
import github.markfrank01.five_wanandroid.ui.login.LoginActivity;
import github.markfrank01.five_wanandroid.ui.login.RegisterActivity;
import github.markfrank01.five_wanandroid.ui.mine.activity.MyCollectActivity;

/**
 * Created by MarkFrank01
 * description :
 */

@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(LoginActivity activity);

    void inject(RegisterActivity activity);

    void inject(MyCollectActivity activity);

}
