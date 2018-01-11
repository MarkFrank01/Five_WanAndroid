package github.markfrank01.five_wanandroid.contract.mine;

import github.markfrank01.five_wanandroid.base.presenter.AbsPresenter;
import github.markfrank01.five_wanandroid.base.view.AbstractView;
import github.markfrank01.five_wanandroid.data.main.HomePageArticleBean;

/**
 * Created by WJC on 2018/9/29.
 */
public class CollectContract {
    public interface View extends AbstractView {
        void getCollectListOK(HomePageArticleBean articleBean,boolean isRefresh);

        void getCollectListErr(String info);

        void cancelCollectOk();

        void cancelCollectErr(String info);
    }

    public interface Presenterr extends AbsPresenter<CollectContract.View> {

        void autoRefresh();

        void loadMore();

        void getCollectList(int page);

        void cancelCollect(int id);
    }
}
