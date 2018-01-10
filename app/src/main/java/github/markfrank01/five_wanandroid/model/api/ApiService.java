package github.markfrank01.five_wanandroid.model.api;

import java.util.List;

import github.markfrank01.five_wanandroid.data.login.UserInfo;
import github.markfrank01.five_wanandroid.data.main.BannerBean;
import github.markfrank01.five_wanandroid.data.main.HomePageArticleBean;
import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by WJC on 2018/9/19.
 */
public interface ApiService {
    /**
     * HomePager
     */
    @Headers({"baseUrl:normal"})
    @GET("article/list/{page}/json")
    Observable<BaseResp<HomePageArticleBean>> getArticleList(@Path("page") int num);

    /**
     * banner
     */
    @Headers({"baseUrl:normal"})
    @GET("banner/json")
    Observable<BaseResp<List<BannerBean>>> getBanner();

    /**
     * login
     */
    @Headers({"baseUrl:normal"})
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResp<UserInfo>> login(@Field("username") String username, @Field("password") String password);

    /**
     * collect Article
     */
    @Headers({"baseUrl:normal"})
    @POST("lg/collect/{id}/json")
    Observable<BaseResp> collectArticle(@Path("id") int id);

    /**
     * cancel collect Article
     */
    @Headers({"baseUrl:normal"})
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseResp> cancelCollectArticle(@Path("id") int id);
}
