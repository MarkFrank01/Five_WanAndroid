package github.markfrank01.five_wanandroid.until.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import github.markfrank01.five_wanandroid.base.activity.BaseActivity;
import github.markfrank01.five_wanandroid.model.constant.Constant;

/**
 * Created by MarkFrank01
 * description : 网络状态
 */

public class NetworkBroadcastReceiver extends BroadcastReceiver {

    public NetEvent eventActivity = BaseActivity.eventActivity;

    @Override
    public void onReceive(Context context, Intent intent) {

        //检测网络变化
        if (intent.getAction().equals(Constant.NETBROADCAST)){
            NetUtils.init(context);
            int netWorkState = NetUtils.getNetWorkState();

            eventActivity.onNetChange(netWorkState);
        }

    }

    public interface NetEvent{
        void onNetChange(int newMobile);
    }
}
