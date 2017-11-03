package baidu.com.androidbaidutakeout.modle.net;

import baidu.com.androidbaidutakeout.utils.Constant;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
 *  创建者:   Administrator
 *  创建时间:  2017/10/27 17:22
 *  描述：    TODO
 */
/*只需要定义所有接口的规范,首页接口,订单接口*/
public interface TakeoutService {
    //    @GET("users/{user}/repos")
//    Call<List<Repo>> listRepos(@Path("user") String user);
    //登录的接口
    @GET(Constant.LOGIN)
    Call<ResponseInfo> login(@Query("phone") String user, @Query("type") String type);

    //首页的接口
    @GET(Constant.HOME)
    Call<ResponseInfo> loadHomeInfo();
}
