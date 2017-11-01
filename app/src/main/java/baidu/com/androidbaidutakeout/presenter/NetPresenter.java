package baidu.com.androidbaidutakeout.presenter;

import android.util.Log;
import android.widget.Toast;

import baidu.com.androidbaidutakeout.modle.net.ResponseInfo;
import baidu.com.androidbaidutakeout.modle.net.TakeoutService;
import baidu.com.androidbaidutakeout.utils.Constant;
import baidu.com.androidbaidutakeout.utils.TakeoutApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 *  创建者:   Administrator
 *  创建时间:  2017/11/1 11:22
 *  描述：    TODO
 */
public abstract class NetPresenter {
    private final Retrofit mRetrofit;
    protected TakeoutService mTakeoutService;

    public NetPresenter() {
        //请求就可以了
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mTakeoutService = mRetrofit.create(TakeoutService.class);
    }

    protected Callback mCallback = new Callback<ResponseInfo>() {
        @Override
        public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
            //服务器联通
            if (response.isSuccessful()) {
                ResponseInfo responseInfo = response.body();
                if ("0".equals(responseInfo.getCode())) {
                    //成功了
                    String json = responseInfo.getData();
                    Toast.makeText(TakeoutApp.sInstance, json, Toast.LENGTH_SHORT).show();
                    parseJson(json);
                } else if ("-1".equals(responseInfo.getCode())) {
                    //空数据
                    Log.e("home", "空数据");
                } else {
                    //其他错误,服务器代码有bug(404,500)
                    Toast.makeText(TakeoutApp.sInstance, "服务器繁忙", Toast.LENGTH_SHORT).show();
                    Log.e("home", "服务器哥们,你又写错了");
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseInfo> call, Throwable t) {
            //网络有问题
            Log.e("home", "网路有问题");
        }
    };


    protected abstract void parseJson(String json);


}
