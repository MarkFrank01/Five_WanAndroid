package github.markfrank01.five_wanandroid.ui.login;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import github.markfrank01.five_wanandroid.R;
import github.markfrank01.five_wanandroid.base.activity.BaseActivity;
import github.markfrank01.five_wanandroid.contract.login.RegisterContract;
import github.markfrank01.five_wanandroid.data.login.UserInfo;
import github.markfrank01.five_wanandroid.presenter.login.RegisterPresenter;
import github.markfrank01.five_wanandroid.until.app.JumpUtil;
import github.markfrank01.five_wanandroid.until.app.ToastUtil;

/**
 * Created by WJC on 2018/9/28.
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.toolbar_register)
    Toolbar mToolbarRegister;
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_ensure_password)
    EditText mEtEnsurePassword;

    private String username, password, ensurePassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbarRegister);
        getSupportActionBar().setTitle(getString(R.string.register));
        mToolbarRegister.setNavigationOnClickListener(v -> finish());

    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void registerOk(UserInfo userInfo) {
        ToastUtil.show(context,getString(R.string.register_ok));
        JumpUtil.overlay(activity,LoginActivity.class);
        finish();
    }

    @Override
    public void registerErr(String info) {
        ToastUtil.show(context,info);
    }

    @OnClick(R.id.btn_register)
    void click(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if (check()) {
                    mPresenter.register(username, password, ensurePassword);
                }
                break;
        }
    }

    private boolean check() {
        username = mEtUsername.getText().toString().trim();
        password = mEtPassword.getText().toString().trim();
        ensurePassword = mEtEnsurePassword.getText().toString().trim();
        if (username.length() < 6 || password.length() < 6) {
            ToastUtil.show(context,getString(R.string.username_incorrect));
            return false;
        }else if (!password.equals(ensurePassword)){
            ToastUtil.show(context,getString(R.string.password_incorrect));
            return false;
        }
        return true;
    }
}
