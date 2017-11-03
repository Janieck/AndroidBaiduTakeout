package baidu.com.androidbaidutakeout.presenter;

import android.util.Log;

import com.google.gson.Gson;

import baidu.com.androidbaidutakeout.modle.net.ResponseInfo;
import baidu.com.androidbaidutakeout.modle.net.User;
import baidu.com.androidbaidutakeout.ui.activity.LoginActivty;
import baidu.com.androidbaidutakeout.utils.TakeoutApp;
import retrofit2.Call;

/*
 *  创建者:   Administrator
 *  创建时间:  2017/11/3 18:32
 *  描述：    TODO
 */
public class LoginActivityPresenter extends NetPresenter {
    LoginActivty mLoginActivty;

    public LoginActivityPresenter(LoginActivty loginActivty) {
        mLoginActivty = loginActivty;
    }

    public void loginByPhone(String phone, String type) {
        Call<ResponseInfo> loginCall = mTakeoutService.login(phone, type);
        loginCall.enqueue(mCallback);
    }

    @Override
    protected void parseJson(String json) {
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);
        if (user != null) {
            Log.e("login", "登录成功");
            //1,做一个内存缓存,保留整个user
            TakeoutApp.sUser = user;
            //cacheData.user = user;
            //cacheData = null; //回收的方法
            //业务完成后刷新界面
            //2,做本地缓存,用sqlite比较多,不停的执行sql语句,sqliteopenHelper
            //直接操作javabean实现数据的增删改查,Orm是javabean和数据库表的映射


            mLoginActivty.onLoginSuccess();
        } else {
            mLoginActivty.onLoginFailed();
            Log.e("login", "登录失败");
        }
    }
}
