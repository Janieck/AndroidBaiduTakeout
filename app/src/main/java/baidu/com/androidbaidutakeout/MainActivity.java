package baidu.com.androidbaidutakeout;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import baidu.com.androidbaidutakeout.ui.fragment.HomeFragment;
import baidu.com.androidbaidutakeout.ui.fragment.MoreFragment;
import baidu.com.androidbaidutakeout.ui.fragment.OrderFragment;
import baidu.com.androidbaidutakeout.ui.fragment.UserFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_fragment_container)
    FrameLayout mMainFragmentContainer;
    @BindView(R.id.main_bottome_switcher_container)
    LinearLayout mMainBottomeSwitcherContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        initBottom();
        changIndex(0);
        getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, mFragmentList.get(0)).commit();
    }

    private void initBottom() {
        int count = mMainBottomeSwitcherContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = mMainBottomeSwitcherContainer.getChildAt(i);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = mMainBottomeSwitcherContainer.indexOfChild(child);
                    changIndex(index);
                }
            });
        }
    }

    private void changIndex(int index) {
        int count = mMainBottomeSwitcherContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = mMainBottomeSwitcherContainer.getChildAt(i);
            if (i == index) {
                //禁用它
                setEnable(child, false);
            } else {
                setEnable(child, true);
            }
        }
    }

    private void setEnable(View child, boolean isEnable) {
        child.setEnabled(isEnable);
        if (child instanceof ViewGroup) {
            ViewGroup item = (ViewGroup) child;
            int childCount = item.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childChild = item.getChildAt(i);
                setEnable(childChild, isEnable);
            }
        }
    }

    private List<Fragment> mFragmentList = new ArrayList<>();

    private void initFragment() {
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new OrderFragment());
        mFragmentList.add(new UserFragment());
        mFragmentList.add(new MoreFragment());
    }
}
