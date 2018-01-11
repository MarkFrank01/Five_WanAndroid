package github.markfrank01.five_wanandroid.presenter.login;

import javax.inject.Inject;

import github.markfrank01.five_wanandroid.base.presenter.BasePresenter;
import github.markfrank01.five_wanandroid.contract.login.LoginContract;
import github.markfrank01.five_wanandroid.data.login.UserInfo;
import github.markfrank01.five_wanandroid.model.api.ApiService;
import github.markfrank01.five_wanandroid.model.api.ApiStore;
import github.markfrank01.five_wanandroid.model.api.BaseResp;
import github.markfrank01.five_wanandroid.model.api.HttpObserver;
import github.markfrank01.five_wanandroid.model.constant.Constant;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WJC on 2018/9/26.
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Persenter{

    @Inject
    public LoginPresenter(){

    }

    @Override
    public void login(String name, String password) {
        ApiStore.createApi(ApiService.class)
                .login(name,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<UserInfo>>() {
                    @Override
                    public void onNext(BaseResp<UserInfo> userInfoBaseResp) {
                        if (userInfoBaseResp.getErrorCode() == Constant.REQUEST_SUCCESS){
                            mView.loginOK(userInfoBaseResp.getData());
                        }else if (userInfoBaseResp.getErrorCode() == Constant.REQUEST_ERROR){
                            mView.loginErr(userInfoBaseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage()!=null){
                            mView.loginErr(e.getMessage());
                        }
                    }
                });
    }
}
