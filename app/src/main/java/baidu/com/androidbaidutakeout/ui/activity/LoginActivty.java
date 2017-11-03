package baidu.com.androidbaidutakeout.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import baidu.com.androidbaidutakeout.R;
import baidu.com.androidbaidutakeout.presenter.LoginActivityPresenter;
import baidu.com.androidbaidutakeout.utils.SMSUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/*
 *  创建者:   Administrator
 *  创建时间:  2017/11/1 23:07
 *  描述：    TODO
 */
public class LoginActivty extends AppCompatActivity {
    private static final int TIME_MINUS = -1;
    private static final int TIME_IS_OUT = 0;
    @BindView(R.id.iv_user_back)
    ImageView mIvUserBack;
    @BindView(R.id.iv_user_password_login)
    TextView mIvUserPasswordLogin;
    @BindView(R.id.et_user_phone)
    EditText mEtUserPhone;
    @BindView(R.id.tv_user_code)
    TextView mTvUserCode;
    @BindView(R.id.et_user_code)
    EditText mEtUserCode;
    @BindView(R.id.login)
    TextView mLogin;
    private LoginActivityPresenter mLoginActivityPresenter;
    private String mPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginActivityPresenter = new LoginActivityPresenter(this);

        //项目启动执行
        SMSSDK.initSDK(this, "221d7853e8060", "f94743b54c8f5aeda0f916dbed18c79d");
        //多少个App对应一个key mob 短信验证

        //一个App对应一个key 极光推送

        //一个App对应多个key 高德地图 版本更新key
        SMSSDK.registerEventHandler(eh);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eh != null) {
            SMSSDK.registerEventHandler(eh);
        }
    }

    EventHandler eh = new EventHandler() {

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    Log.e("sms", "提交验证码成功");
                    //表明了身份,手机在我手里,我是本人,可以去服务器数据了
                    //在使用账号密码登录服务器
                    mLoginActivityPresenter.loginByPhone(mPhone, "1");
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    Log.e("sms", "获取短信验证码成功");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

    @OnClick({R.id.iv_user_back, R.id.tv_user_code, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_back:
                finish();
                break;
            case R.id.tv_user_code:
                mPhone = mEtUserPhone.getText().toString().trim();
                //获取验证码
                if (SMSUtil.judgePhoneNums(this, mPhone)) {
                    SMSSDK.getVerificationCode("86", mPhone);
                    //同时开启倒计时
                    mTvUserCode.setEnabled(false);
                    new Thread(new CutDownTask()).start();
                }

                break;
            case R.id.login:
                mPhone = mEtUserPhone.getText().toString().trim();
              /*  if (SMSUtil.judgePhoneNums(this, mPhone)) {
                    String code = mEtUserCode.getText().toString().trim();
                    SMSSDK.submitVerificationCode("86", mPhone, code);
                }*/
                mLoginActivityPresenter.loginByPhone(mPhone, "1");
                break;
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIME_MINUS:
                    mTvUserCode.setText("剩余时间(" + time + ")秒");
                    break;
                case TIME_IS_OUT:
                    mTvUserCode.setText("重新发送");
                    mTvUserCode.setEnabled(true);
                    break;
            }
        }
    };
    int time = 60;


    private class CutDownTask implements Runnable {
        @Override
        public void run() {
            for (; time > 0; time--) {
                SystemClock.sleep(999);
                mHandler.sendEmptyMessage(TIME_MINUS);
            }
            mHandler.sendEmptyMessage(TIME_IS_OUT);
            time = 60;
        }
    }

    public void onLoginSuccess() {
        finish();
    }

    public void onLoginFailed() {
    }
}
