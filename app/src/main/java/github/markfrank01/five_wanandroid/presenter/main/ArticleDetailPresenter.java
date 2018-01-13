package github.markfrank01.five_wanandroid.presenter.main;

import javax.inject.Inject;

import github.markfrank01.five_wanandroid.base.presenter.BasePresenter;
import github.markfrank01.five_wanandroid.contract.main.ArticleDetailContact;
import github.markfrank01.five_wanandroid.model.api.ApiService;
import github.markfrank01.five_wanandroid.model.api.ApiStore;
import github.markfrank01.five_wanandroid.model.api.BaseResp;
import github.markfrank01.five_wanandroid.model.api.HttpObserver;
import github.markfrank01.five_wanandroid.model.constant.Constant;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WJC on 2018/9/30.
 */
public class ArticleDetailPresenter extends BasePresenter<ArticleDetailContact.View> implements ArticleDetailContact.Presenter {

    @Inject
    public ArticleDetailPresenter() {

    }

    @Override
    public void collectArticle(int id) {
        ApiStore.createApi(ApiService.class)
                .collectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp>() {
                    @Override
                    public void onNext(BaseResp baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.collectArticleOK((String) baseResp.getData());
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
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
                        if (baseResp.getErrorCode()==Constant.REQUEST_SUCCESS){
                            mView.cancelCollectArticleOK((String) baseResp.getData());
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
