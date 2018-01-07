package github.markfrank01.five_wanandroid.base.activity;

import github.markfrank01.five_wanandroid.base.presenter.AbsPresenter;
import github.markfrank01.five_wanandroid.base.view.AbstractView;
import github.markfrank01.five_wanandroid.until.network.NetworkBroadcastReceiver;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by MarkFrank01
 * description : BaseActivity
 */

public abstract class BaseActivity<T extends AbsPresenter> extends SupportActivity implements AbstractView, NetworkBroadcastReceiver.NetEvent {


    public static NetworkBroadcastReceiver.NetEvent eventActivity;

}
