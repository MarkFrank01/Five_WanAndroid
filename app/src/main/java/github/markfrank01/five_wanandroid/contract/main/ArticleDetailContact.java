package github.markfrank01.five_wanandroid.contract.main;

import github.markfrank01.five_wanandroid.base.presenter.AbsPresenter;
import github.markfrank01.five_wanandroid.base.view.AbstractView;

/**
 * Created by WJC on 2018/9/30.
 */
public class ArticleDetailContact {

    public interface View extends AbstractView{
        void collectArticleOK(String info);

        void collectArticleErr(String info);

        void cancelCollectArticleOK(String info);

        void cancelCollectArticleErr(String info);
    }

    public interface Presenter extends AbsPresenter<ArticleDetailContact.View>{

        void collectArticle(int id);

        void cancelCollectArticle(int id);
    }
}
