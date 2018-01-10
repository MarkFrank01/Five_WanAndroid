package github.markfrank01.five_wanandroid.model.api;

import java.util.List;

import github.markfrank01.five_wanandroid.data.main.BannerBean;
import github.markfrank01.five_wanandroid.data.main.HomePageArticleBean;
import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by WJC on 2018/9/19.
 */
public interface ApiService {
    /**
     * 主页
     */
    @Headers({"baseUrl:normal"})
    @GET("article/list/{page}/json")
    Observable<BaseResp<HomePageArticleBean>> getArticleList(@Path("page") int num);

    /**
     * banner
     */
    @Headers("baseUrl:normal")
    @GET("banner/json")
    Observable<BaseResp<List<BannerBean>>> getBanner();
}
