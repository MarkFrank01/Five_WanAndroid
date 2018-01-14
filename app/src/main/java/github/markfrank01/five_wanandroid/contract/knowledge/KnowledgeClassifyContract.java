package github.markfrank01.five_wanandroid.contract.knowledge;

import github.markfrank01.five_wanandroid.base.presenter.AbsPresenter;
import github.markfrank01.five_wanandroid.base.view.AbstractView;
import github.markfrank01.five_wanandroid.data.knowledge.KnowledgeClassifyListBean;
import github.markfrank01.five_wanandroid.data.knowledge.KnowledgeListBean;

/**
 * Created by WJC on 2018/10/7.
 */
public class KnowledgeClassifyContract {

    public interface View extends AbstractView {

        void getKnowledgeClassifyListOk(KnowledgeClassifyListBean knowledgeClassifyListBean, boolean isRefresh);

        void getKnowledgeClassifyListErr(String info);

        void collectArticleOK(String info);

        void collectArticleErr(String info);

        void cancelCollectArticleOK(String info);

        void cancelCollectArticleErr(String info);
    }

    public interface Presenter extends AbsPresenter<KnowledgeClassifyContract.View> {

        void autoRefresh();

        void loadMore();

        void getKnowledgeClassifyList(int page,int id);

        void collectArticle(int id);

        void cancelCollectArticle(int id);
    }
}
