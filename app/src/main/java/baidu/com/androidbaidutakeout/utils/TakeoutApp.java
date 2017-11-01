package baidu.com.androidbaidutakeout.utils;

import android.app.Application;

/*
 *  创建者:   Administrator
 *  创建时间:  2017/10/28 17:44
 *  描述：    TODO
 */
public class TakeoutApp extends Application {
    public static TakeoutApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
