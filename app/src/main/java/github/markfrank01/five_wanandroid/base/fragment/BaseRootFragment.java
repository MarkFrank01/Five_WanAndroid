package github.markfrank01.five_wanandroid.base.fragment;

import android.view.View;
import android.view.ViewGroup;

import github.markfrank01.five_wanandroid.R;
import github.markfrank01.five_wanandroid.base.presenter.BasePresenter;

/**
 * Created by MarkFrank01
 * description : loading state
 */

public abstract class BaseRootFragment<T extends BasePresenter> extends BaseFragment<T> {

    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;
    public static final int EMPTY_STATE = 3;
    public static final int NETEASE_LOADING_STATE = 4;

    private View mErrorView;
    private View mLoadingView;
    private View mLoadingNetease;
    private View mEmptyView;
    private ViewGroup mNormalView;
    private int currentState = NORMAL_STATE;

    public BaseRootFragment() {

    }

    @Override
    protected void initUI() {
        if (getView() == null) {
            return;
        }

        mNormalView = getView().findViewById(R.id.normal_view);
        if (mNormalView == null) {
            throw new IllegalStateException("The subclass of RootActivity must contain a View named 'mNormalView'.");
        }
        if (!(mNormalView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException("mNormalView's ParentView should be a ViewGroup.");
        }

        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(activity, R.layout.view_loading, parent);
        View.inflate(activity, R.layout.netease_loading, parent);
        View.inflate(activity, R.layout.view_error, parent);
        View.inflate(activity, R.layout.view_empty, parent);

        mLoadingView = parent.findViewById(R.id.loading_group);
        mLoadingNetease = parent.findViewById(R.id.loading_netease);
        mErrorView = parent.findViewById(R.id.error_group);
        mEmptyView = parent.findViewById(R.id.empty_group);

        mErrorView.setOnClickListener(v -> reload());
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        mLoadingNetease.setVisibility(View.GONE);
        mNormalView.setVisibility(View.VISIBLE);
    }

    private void hideCurrentView() {
        switch (currentState) {
            case NORMAL_STATE:
                if (mNormalView == null) {
                    return;
                }
                mNormalView.setVisibility(View.GONE);
                break;
            case LOADING_STATE:
                mLoadingView.setVisibility(View.GONE);
                break;
            case NETEASE_LOADING_STATE:
                mLoadingNetease.setVisibility(View.GONE);
                break;
            case ERROR_STATE:
                mErrorView.setVisibility(View.GONE);
                break;
            case EMPTY_STATE:
                mEmptyView.setVisibility(View.GONE);
            default:
                break;
        }
    }

    @Override
    public void showNormal() {
        if (currentState == NORMAL_STATE) {
            return;
        }
        hideCurrentView();
        currentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        if (currentState == LOADING_STATE) {
            return;
        }

        hideCurrentView();
        currentState = LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNeteaseLoading() {
        if (currentState == NETEASE_LOADING_STATE) {
            return;
        }

        hideCurrentView();
        currentState = NETEASE_LOADING_STATE;
        mLoadingNetease.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        if (currentState == ERROR_STATE) {
            return;
        }

        hideCurrentView();
        currentState = ERROR_STATE;
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        if (currentState == EMPTY_STATE) {
            return;
        }
        hideCurrentView();
        currentState = EMPTY_STATE;
        mEmptyView.setVisibility(View.VISIBLE);
    }


}
