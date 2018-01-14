package github.markfrank01.five_wanandroid.presenter.knowledge;

import javax.inject.Inject;

import github.markfrank01.five_wanandroid.base.presenter.BasePresenter;
import github.markfrank01.five_wanandroid.contract.knowledge.KnowledgeClassifyContract;
import github.markfrank01.five_wanandroid.data.knowledge.KnowledgeClassifyListBean;
import github.markfrank01.five_wanandroid.model.api.ApiService;
import github.markfrank01.five_wanandroid.model.api.ApiStore;
import github.markfrank01.five_wanandroid.model.api.BaseResp;
import github.markfrank01.five_wanandroid.model.api.HttpObserver;
import github.markfrank01.five_wanandroid.model.constant.Constant;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WJC on 2018/10/7.
 */
public class KnowledgeClassifyPresenter extends BasePresenter<KnowledgeClassifyContract.View> implements KnowledgeClassifyContract.Presenter{

    private int currentPage;
    private int cid;
    private boolean isRefresh = true;

    @Inject
    public KnowledgeClassifyPresenter(){

    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        currentPage = 0;
        getKnowledgeClassifyList(currentPage,getCid());
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage ++;
        getKnowledgeClassifyList(currentPage,getCid());
    }

    @Override
    public void getKnowledgeClassifyList(int page, int id) {
        ApiStore.createApi(ApiService.class)
                .getKnowledgeClassifyList(page,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<KnowledgeClassifyListBean>>() {
                    @Override
                    public void onNext(BaseResp<KnowledgeClassifyListBean> baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS){
                            mView.getKnowledgeClassifyListOk(baseResp.getData(),isRefresh);
                        }else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR){
                            mView.getKnowledgeClassifyListErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getKnowledgeClassifyListErr(e.getMessage());
                    }
                });
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
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS){
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
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS){
                            mView.cancelCollectArticleOK((String) baseResp.getData());
                        }else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR){
                            mView.collectArticleErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.cancelCollectArticleErr(e.getMessage());
                    }
                });
    }
}
