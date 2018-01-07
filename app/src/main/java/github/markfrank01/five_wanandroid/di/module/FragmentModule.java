package github.markfrank01.five_wanandroid.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MarkFrank01
 * description :
 */

@Module
public class FragmentModule {

    private Fragment mFagment;

    public FragmentModule(Fragment mFagment) {
        this.mFagment = mFagment;
    }

    @Provides
    Activity provideActivity(){
        return mFagment.getActivity();
    }

    @Provides
    Fragment provideFragment(){
        return mFagment;
    }

}
