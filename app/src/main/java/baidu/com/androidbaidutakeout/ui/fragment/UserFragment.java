package baidu.com.androidbaidutakeout.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import baidu.com.androidbaidutakeout.R;
import baidu.com.androidbaidutakeout.ui.activity.LoginActivty;
import baidu.com.androidbaidutakeout.utils.TakeoutApp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 *  创建者:   Administrator
 *  创建时间:  2017/10/17 12:02
 *  描述：    TODO
 */
public class UserFragment extends Fragment {

    @BindView(R.id.tv_user_setting)
    ImageView mTvUserSetting;
    @BindView(R.id.iv_user_notice)
    ImageView mIvUserNotice;
    @BindView(R.id.login)
    ImageView mLogin;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.ll_userinfo)
    LinearLayout mLlUserinfo;
    @BindView(R.id.iv_address_manager)
    ImageView mIvAddressManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View userView = View.inflate(getActivity(), R.layout.fragment_user, null);
        ButterKnife.bind(this, userView);
        return userView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.login)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), LoginActivty.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        //如果是已经登录成功(id!=-1),显示欢迎界面
        if (TakeoutApp.sUser.getId() != -1) {
            //登录成功
            mLogin.setVisibility(View.GONE);
            mLlUserinfo.setVisibility(View.VISIBLE);
            mUsername.setText("欢迎您," + TakeoutApp.sUser.getName());
            mPhone.setText(TakeoutApp.sUser.getPhone());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //登录成功,显示欢迎界面

    }
}
