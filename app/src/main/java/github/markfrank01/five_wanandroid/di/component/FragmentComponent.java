package github.markfrank01.five_wanandroid.di.component;

import android.app.Activity;

import dagger.Component;
import github.markfrank01.five_wanandroid.di.module.FragmentModule;
import github.markfrank01.five_wanandroid.ui.main.fragment.HomePageFragment;

/**
 * Created by MarkFrank01
 * description :
 */

@Component(dependencies = ApplicationComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(HomePageFragment fragment);
}
