package github.markfrank01.five_wanandroid.base.presenter;

import github.markfrank01.five_wanandroid.base.view.AbstractView;

/**
 * Created by MarkFrank01
 * description :
 */

public interface AbsPresenter<T extends AbstractView> {

    /**
     * 注入View
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * 回收View
     */
    void detachView();
}
