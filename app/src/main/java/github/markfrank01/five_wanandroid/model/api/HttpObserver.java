package github.markfrank01.five_wanandroid.model.api;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by WJC on 2018/9/19.
 */
public abstract class HttpObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
