package baidu.com.androidbaidutakeout.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import baidu.com.androidbaidutakeout.modle.net.ResponseInfo;
import baidu.com.androidbaidutakeout.modle.net.Seller;
import baidu.com.androidbaidutakeout.ui.fragment.HomeFragment;
import retrofit2.Call;

/*
 *  创建者:   Administrator
 *  创建时间:  2017/10/25 16:16
 *  描述：    TODO
 */
/*首页业务类,获取首页信息*/
public class HomeFragmentPresenter extends NetPresenter {
    HomeFragment mHomeFragment;

    public HomeFragmentPresenter(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }

    public void loadHomeInfo() {
        Call<ResponseInfo> homeCall = mTakeoutService.loadHomeInfo();
        homeCall.enqueue(mCallback);
    }

    protected void parseJson(String json) {
        try {
            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject(json);
            String nearby = jsonObject.getString("nearbySellerList");
            //List<Seller> ,TypeToken
            List<Seller> nearbySellers = gson.fromJson(nearby, new TypeToken<List<Seller>>() {
            }.getType());
            String other = jsonObject.getString("otherSellerList");
            //List<Seller> ,TypeToken
            List<Seller> otherSellers = gson.fromJson(other, new TypeToken<List<Seller>>() {
            }.getType());
            //把解析的javabean赋值给recyclerview的adapter
            mHomeFragment.onSuccess(nearbySellers, otherSellers);
        } catch (Exception e) {
            e.printStackTrace();
            mHomeFragment.onFailed(e.getMessage());
        }

    }
}
