package github.markfrank01.five_wanandroid.presenter.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import github.markfrank01.five_wanandroid.base.app.MyApplication;
import github.markfrank01.five_wanandroid.base.presenter.BasePresenter;
import github.markfrank01.five_wanandroid.contract.main.HomePageContract;
import github.markfrank01.five_wanandroid.data.login.UserInfo;
import github.markfrank01.five_wanandroid.data.main.BannerBean;
import github.markfrank01.five_wanandroid.data.main.HomePageArticleBean;
import github.markfrank01.five_wanandroid.model.api.ApiService;
import github.markfrank01.five_wanandroid.model.api.ApiStore;
import github.markfrank01.five_wanandroid.model.api.BaseResp;
import github.markfrank01.five_wanandroid.model.api.HttpObserver;
import github.markfrank01.five_wanandroid.model.constant.Constant;
import github.markfrank01.five_wanandroid.until.app.SharedPreferenceUtil;
import github.markfrank01.five_wanandroid.until.network.RxUtils;
import io.reactivex.Observable;
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
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
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
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.getBannerOk(baseResp.getData());
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
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
        String username = (String) SharedPreferenceUtil.get(MyApplication.getInstance(), Constant.USERNAME, Constant.DEFAULT);
        String password = (String) SharedPreferenceUtil.get(MyApplication.getInstance(), Constant.PASSWORD, Constant.DEFAULT);

        Observable<BaseResp<UserInfo>> observableUser = ApiStore.createApi(ApiService.class).login(username, password);
        Observable<BaseResp<List<BannerBean>>> observableBanner = ApiStore.createApi(ApiService.class).getBanner();
        Observable<BaseResp<HomePageArticleBean>> observableArticle = ApiStore.createApi(ApiService.class).getArticleList(currentPage);

        Observable.zip(observableUser, observableBanner, observableArticle, (userInfoBaseResp, bannerData, homepageData) -> {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put(Constant.LOGINDATA, userInfoBaseResp);
            hashMap.put(Constant.BANNERDATA, bannerData);
            hashMap.put(Constant.HOMEPAGEDATA, homepageData);
            return hashMap;
        }).compose(RxUtils.rxSchedulerHelper())
                .subscribe(new HttpObserver<Map<String, Object>>() {
                    @Override
                    public void onNext(Map<String, Object> map) {
                        /**
                         * login info
                         */
                        BaseResp<UserInfo> userInfo = RxUtils.cast(map.get(Constant.LOGINDATA));
                        if (userInfo.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.loginSuccess(userInfo.getData());
                        } else if (userInfo.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.loginErr(userInfo.getErrorMsg());
                        }

                        /**
                         * banner info
                         */
                        BaseResp<List<BannerBean>> bannerInfo = RxUtils.cast(map.get(Constant.BANNERDATA));
                        if (bannerInfo.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.getBannerOk(bannerInfo.getData());
                        } else if (bannerInfo.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.getBannerErr(bannerInfo.getErrorMsg());
                        }

                        /**
                         * homepage info
                         */
                        BaseResp<HomePageArticleBean> homepageInfo = RxUtils.cast(map.get(Constant.HOMEPAGEDATA));
                        if (homepageInfo.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.getHomepageListOk(homepageInfo.getData(), isRefresh);
                        } else if (homepageInfo.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.getHomepageListErr(homepageInfo.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getHomepageListErr(e.getMessage());
                    }
                });
    }

    @Override
    public void collectArticle(int id) {
        ApiStore.createApi(ApiService.class)
                .collectArticle(id)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new HttpObserver<BaseResp>() {
                    @Override
                    public void onNext(BaseResp baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.collectArticleOK((String) baseResp.getData());
                        }else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR){
                            mView.collectArticleErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.collectArticleErr(e.getMessage());
                    }
                });
        }

    @Override
    public void cancelCollectArticle(int id) {
        ApiStore.createApi(ApiService.class)
                .cancelCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp>() {
                    @Override
                    public void onNext(BaseResp baseResp) {
                        if(baseResp.getErrorCode() == Constant.REQUEST_SUCCESS){
                            mView.cancelCollectArticleOK((String)baseResp.getData());
                        }else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR){
                            mView.cancelCollectArticleErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.cancelCollectArticleErr(e.getMessage());
                    }
                });
    }
}
