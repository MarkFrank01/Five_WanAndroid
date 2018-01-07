package github.markfrank01.five_wanandroid.base.view;

import android.view.View;

/**
 * Created by MarkFrank01
 * description :
 */

public interface AbstractView {

    /**
     * showNormal
     */
    void showNormal();

    /**
     * Show error
     */
    void showError();

    /**
     * Show loading
     */
    void showLoading();

    void showNeteaseLoading();

    /**
     * Show empty
     */
    void showEmpty();

    /**
     * Reload
     */
    void reload();

    void setVisible(View... views);

    void setInVisible(View... views);

    void setGone(View... views);
}
