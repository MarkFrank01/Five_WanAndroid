package github.markfrank01.five_wanandroid.presenter.main;

import java.util.List;

import javax.inject.Inject;

import github.markfrank01.five_wanandroid.base.presenter.BasePresenter;
import github.markfrank01.five_wanandroid.contract.main.HomePageContract;
import github.markfrank01.five_wanandroid.data.main.BannerBean;
import github.markfrank01.five_wanandroid.data.main.HomePageArticleBean;
import github.markfrank01.five_wanandroid.model.api.ApiService;
import github.markfrank01.five_wanandroid.model.api.ApiStore;
import github.markfrank01.five_wanandroid.model.api.BaseResp;
import github.markfrank01.five_wanandroid.model.api.HttpObserver;
import github.markfrank01.five_wanandroid.model.constant.Constant;
import github.markfrank01.five_wanandroid.until.network.RxUtils;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WJC on 2018/9/19.
 */
public class HomePagePresenter extends BasePresenter<HomePageContract.View> implements HomePageContract.Presenter {

    private boolean isRefresh = true;
    private int currentPage;

    @Inject
    public HomePagePresenter() {

    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        currentPage = 0;
        getBanner();
        getHomepageList(currentPage);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getHomepageList(currentPage);
    }

    @Override
    public void getHomepageList(int page) {
        ApiStore.createApi(ApiService.class)
                .getArticleList(page)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new HttpObserver<BaseResp<HomePageArticleBean>>() {
                    @Override
                    public void onNext(BaseResp<HomePageArticleBean> baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.getHomepageListOk(baseResp.getData(), isRefresh);
                        } else if(baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.getBannerErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getBannerErr(e.getMessage());
                    }
                });
    }

    @Override
    public void getBanner() {
        ApiStore.createApi(ApiService.class)
                .getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<List<BannerBean>>>() {
                    @Override
                    public void onNext(BaseResp<List<BannerBean>> baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS){
                            mView.getBannerOk(baseResp.getData());
                        }else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR){
                            mView.getBannerErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getBannerErr(e.getMessage());
                    }
                });

    }

    @Override
    public void loginAndLoad() {

    }

    @Override
    public void collectArticle(int id) {

    }

    @Override
    public void cancelCollectArticle(int id) {

    }
}
