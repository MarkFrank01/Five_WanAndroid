package github.markfrank01.five_wanandroid.presenter.mine;

import javax.inject.Inject;

import github.markfrank01.five_wanandroid.base.presenter.BasePresenter;
import github.markfrank01.five_wanandroid.contract.mine.CollectContract;
import github.markfrank01.five_wanandroid.data.main.HomePageArticleBean;
import github.markfrank01.five_wanandroid.model.api.ApiService;
import github.markfrank01.five_wanandroid.model.api.ApiStore;
import github.markfrank01.five_wanandroid.model.api.BaseResp;
import github.markfrank01.five_wanandroid.model.api.HttpObserver;
import github.markfrank01.five_wanandroid.model.constant.Constant;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WJC on 2018/9/29.
 */
public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenterr {

    private int currentPage;
    private boolean isRefresh = true;

    @Inject
    public CollectPresenter() {

    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        currentPage = 0;
        getCollectList(currentPage);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getCollectList(currentPage);
    }

    @Override
    public void getCollectList(int page) {
        ApiStore.createApi(ApiService.class)
                .getCollectList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<HomePageArticleBean>>() {
                    @Override
                    public void onNext(BaseResp<HomePageArticleBean> baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.getCollectListOK(baseResp.getData(), isRefresh);
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.getCollectListErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getCollectListErr(e.getMessage());
                    }
                });
    }

    @Override
    public void cancelCollect(int id) {
        ApiStore.createApi(ApiService.class)
                .cancelCollectArticleList(id,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<HomePageArticleBean>>() {
                    @Override
                    public void onNext(BaseResp<HomePageArticleBean> collectBaseResp) {
                        if (collectBaseResp.getErrorCode() == Constant.REQUEST_SUCCESS){
                            mView.cancelCollectOk();
                        }else if (collectBaseResp.getErrorCode() == Constant.REQUEST_ERROR){
                            mView.cancelCollectErr(collectBaseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getCollectListErr(e.getMessage());
                    }
                });
    }
}
