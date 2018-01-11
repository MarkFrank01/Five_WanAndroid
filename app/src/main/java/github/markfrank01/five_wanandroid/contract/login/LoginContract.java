package github.markfrank01.five_wanandroid.contract.login;

import github.markfrank01.five_wanandroid.base.presenter.AbsPresenter;
import github.markfrank01.five_wanandroid.base.view.AbstractView;
import github.markfrank01.five_wanandroid.data.login.UserInfo;

/**
 * Created by WJC on 2018/9/26.
 */
public class LoginContract {

    public interface View extends AbstractView {
        void loginOK(UserInfo userInfo);

        void loginErr(String info);
    }

    public interface Persenter extends AbsPresenter<LoginContract.View> {

        void login(String name, String password);
    }
}
