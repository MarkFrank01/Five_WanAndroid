package github.markfrank01.five_wanandroid.base.presenter;

import github.markfrank01.five_wanandroid.base.view.AbstractView;

/**
 * Created by MarkFrank01
 * description :
 */

public class BasePresenter<T extends AbstractView> implements AbsPresenter<T> {

    protected T mView;
    private int currentPage;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public int getCurrentPage(){
        return currentPage;
    }

    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }
}
