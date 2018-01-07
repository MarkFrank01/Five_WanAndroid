package github.markfrank01.five_wanandroid.di.component;

import android.content.Context;

import dagger.Component;
import github.markfrank01.five_wanandroid.di.module.ApplicationModule;

/**
 * Created by MarkFrank01
 * description :
 */

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context getApplication();
}
