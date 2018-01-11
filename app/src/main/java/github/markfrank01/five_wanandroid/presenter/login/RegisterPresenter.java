package github.markfrank01.five_wanandroid.presenter.login;

import javax.inject.Inject;

import github.markfrank01.five_wanandroid.base.presenter.BasePresenter;
import github.markfrank01.five_wanandroid.contract.login.RegisterContract;
import github.markfrank01.five_wanandroid.data.login.UserInfo;
import github.markfrank01.five_wanandroid.model.api.ApiService;
import github.markfrank01.five_wanandroid.model.api.ApiStore;
import github.markfrank01.five_wanandroid.model.api.BaseResp;
import github.markfrank01.five_wanandroid.model.api.HttpObserver;
import github.markfrank01.five_wanandroid.model.constant.Constant;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WJC on 2018/9/28.
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    @Inject
    public RegisterPresenter() {

    }

    @Override
    public void register(String name, String password, String rePassword) {
        ApiStore.createApi(ApiService.class)
                .register(name, password, rePassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<UserInfo>>() {
                    @Override
                    public void onNext(BaseResp<UserInfo> userInfoBaseResp) {
                        if (userInfoBaseResp.getErrorCode() == Constant.REQUEST_SUCCESS){
                            mView.registerOk(userInfoBaseResp.getData());
                        }else if (userInfoBaseResp.getErrorCode() == Constant.REQUEST_ERROR){
                            mView.registerErr(userInfoBaseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.registerErr(e.getMessage());
                    }
                });
    }
}
