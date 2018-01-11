package github.markfrank01.five_wanandroid.ui.mine.fragment;

import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.net.CookieManager;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import github.markfrank01.five_wanandroid.R;
import github.markfrank01.five_wanandroid.base.fragment.BaseFragment;
import github.markfrank01.five_wanandroid.model.constant.Constant;
import github.markfrank01.five_wanandroid.model.constant.EventConstant;
import github.markfrank01.five_wanandroid.model.constant.MessageEvent;
import github.markfrank01.five_wanandroid.model.cookie.CookiesManager;
import github.markfrank01.five_wanandroid.ui.login.LoginActivity;
import github.markfrank01.five_wanandroid.ui.mine.activity.AboutUsActivity;
import github.markfrank01.five_wanandroid.ui.mine.activity.MyCollectActivity;
import github.markfrank01.five_wanandroid.until.app.JumpUtil;
import github.markfrank01.five_wanandroid.until.app.SharedPreferenceUtil;
import github.markfrank01.five_wanandroid.until.app.ToastUtil;
import github.markfrank01.five_wanandroid.until.glide.GlideUtil;
import github.markfrank01.five_wanandroid.widget.CommonAlertDialog;
import github.markfrank01.five_wanandroid.widget.CommonDialog;

/**
 * Created by WJC on 2018/9/26.
 */
public class PersonalFragment extends BaseFragment {

    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.tv_username)
    TextView mTvName;
    @BindView(R.id.tv_logout)
    TextView mTvLogout;
    private boolean isLogin;
    private CommonDialog dialog;

    public static PersonalFragment getInstance() {
        return new PersonalFragment();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_presonal;
    }

    @Override
    protected void initUI() {
        GlideUtil.loadImage(context, R.drawable.wjc, imageHead);
    }

    @Override
    protected void initData() {
        isLogin = (boolean) SharedPreferenceUtil.get(activity, Constant.ISLOGIN, Constant.FALSE);
        String username = (String) SharedPreferenceUtil.get(activity, Constant.USERNAME, Constant.DEFAULT);
        mTvLogout.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        mTvName.setText(isLogin ? username : getString(R.string.click_head_login));
        imageHead.setEnabled(!isLogin);
    }

    @OnClick({R.id.image_head, R.id.view_collect, R.id.view_about, R.id.tv_logout})
    void click(View view) {
        switch (view.getId()) {
            case R.id.image_head:
                //jump to login
                JumpUtil.overlay(context,LoginActivity.class);
                break;
            case R.id.view_collect:
                //jump to collect after login
                if (isLogin){
                    JumpUtil.overlay(context,MyCollectActivity.class);
                }else {
                    ToastUtil.show(activity,getString(R.string.please_login));
                }
                break;
            case R.id.view_about:
                //jump to about
                JumpUtil.overlay(context,AboutUsActivity.class);
                break;
            case R.id.tv_logout:
                dialog = new CommonDialog.Builder(activity)
                        .setTitle(getString(R.string.logout))
                        .setMessage(getString(R.string.logout_sure))
                        .setPositiveButton(getString(R.string.logout_ok), v -> logout())
                        .setNegativeButton(getString(R.string.cancel), v -> dialog.dismiss())
                        .setCancelable(false)
                        .create();
                dialog.show();

                break;
        }
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event.getCode() == EventConstant.LOGINSUCCESS) {
            initData();
        }
    }

    private void logout() {
        dialog.dismiss();
        ToastUtil.show(activity, getString(R.string.logout_ok));
        CommonAlertDialog.newInstance().cancelDialog(true);
        SharedPreferenceUtil.clear(activity);
        initData();
        CookiesManager.clearAllCookies();
        SharedPreferenceUtil.put(activity, Constant.ISLOGIN, false);
        EventBus.getDefault().post(new MessageEvent(EventConstant.LOGOUTSUCCESS, ""));
    }
}
