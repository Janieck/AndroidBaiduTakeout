package baidu.com.androidbaidutakeout.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*
 *  创建者:   Administrator
 *  创建时间:  2017/10/17 12:02
 *  描述：    TODO
 */
public class UserFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("用户");
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
