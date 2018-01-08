package github.markfrank01.five_wanandroid.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by MarkFrank01
 * description :
 */

public class SimpleFragmentStateAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public SimpleFragmentStateAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
