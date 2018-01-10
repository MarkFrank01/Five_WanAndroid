package github.markfrank01.five_wanandroid.contract.main;

import java.util.List;

import github.markfrank01.five_wanandroid.base.presenter.AbsPresenter;
import github.markfrank01.five_wanandroid.base.view.AbstractView;
import github.markfrank01.five_wanandroid.data.login.UserInfo;
import github.markfrank01.five_wanandroid.data.main.BannerBean;
import github.markfrank01.five_wanandroid.data.main.HomePageArticleBean;

/**
 * Created by MarkFrank01
 * description :
 */

public class HomePageContract {

    public interface View extends AbstractView{

        void getHomepageListOk(HomePageArticleBean dataBean, boolean isRefresh);

        void getHomepageListErr(String info);

        void getBannerOk(List<BannerBean> bannerBean);

        void getBannerErr(String info);

        void loginSuccess(UserInfo userInfo);

        void loginErr(String info);

        void collectArticleOK(String info);

        void collectArticleErr(String info);

        void cancelCollectArticleOK(String info);

        void cancelCollectArticleErr(String info);

    }

    public interface Presenter extends AbsPresenter<View>{

        void autoRefresh();

        void loadMore();

        void getHomepageList(int page);

        void getBanner();

        void loginAndLoad();

        void collectArticle(int id);

        void cancelCollectArticle(int id);
    }
}
