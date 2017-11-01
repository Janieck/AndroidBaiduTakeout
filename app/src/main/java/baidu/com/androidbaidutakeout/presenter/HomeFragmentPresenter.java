package baidu.com.androidbaidutakeout.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import baidu.com.androidbaidutakeout.modle.net.ResponseInfo;
import baidu.com.androidbaidutakeout.modle.net.Seller;
import baidu.com.androidbaidutakeout.modle.net.TakeoutService;
import baidu.com.androidbaidutakeout.ui.fragment.HomeFragment;
import baidu.com.androidbaidutakeout.utils.Constant;
import baidu.com.androidbaidutakeout.utils.TakeoutApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 *  创建者:   Administrator
 *  创建时间:  2017/10/25 16:16
 *  描述：    TODO
 */
/*首页业务类,获取首页信息*/
public class HomeFragmentPresenter {
    HomeFragment mHomeFragment;

    public HomeFragmentPresenter(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }

    public void loadHomeInfo() {
        //请求就可以了
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TakeoutService takeoutService = retrofit.create(TakeoutService.class);
        Call<ResponseInfo> homeCall = takeoutService.loadHomeInfo();
        homeCall.enqueue(new Callback<ResponseInfo>() {
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
        });
    }

    private void parseJson(String json) {
        try {
            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject(json);
            String nearby = jsonObject.getString("nearbySellerList");
            //List<Seller> ,TypeToken
            List<Seller> nearbySellers = gson.fromJson(nearby, new TypeToken<List<Seller>>(){}.getType());
            String other = jsonObject.getString("otherSellerList");
            //List<Seller> ,TypeToken
            List<Seller> otherSellers = gson.fromJson(other, new TypeToken<List<Seller>>(){}.getType());
            //把解析的javabean赋值给recyclerview的adapter
            mHomeFragment.onSuccess(nearbySellers,otherSellers);
        } catch (Exception e) {
            e.printStackTrace();
            mHomeFragment.onFailed(e.getMessage());
        }

    }
}
