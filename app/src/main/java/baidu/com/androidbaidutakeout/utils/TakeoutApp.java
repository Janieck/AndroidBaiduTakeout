package baidu.com.androidbaidutakeout.utils;

import android.app.Application;

import baidu.com.androidbaidutakeout.modle.net.User;

/*
 *  创建者:   Administrator
 *  创建时间:  2017/10/28 17:44
 *  描述：    TODO
 */
public class TakeoutApp extends Application {
    public static TakeoutApp sInstance;
    public static User sUser;//内存常驻的

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sUser = new User();
        sUser.setId(-1);//未登录的
    }
}
