package github.markfrank01.five_wanandroid.contract.login;

import github.markfrank01.five_wanandroid.base.presenter.AbsPresenter;
import github.markfrank01.five_wanandroid.base.view.AbstractView;
import github.markfrank01.five_wanandroid.data.login.UserInfo;

/**
 * Created by WJC on 2018/9/26.
 */
public class RegisterContract {

    public interface View extends AbstractView {

        void registerOk(UserInfo userInfo);

        void registerErr(String info);

    }

    public interface Presenter extends AbsPresenter<RegisterContract.View> {

        void register(String name, String password, String rePassword);

    }

}
