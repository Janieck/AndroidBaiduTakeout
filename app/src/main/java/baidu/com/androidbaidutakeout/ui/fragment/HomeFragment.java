package baidu.com.androidbaidutakeout.ui.fragment;

import android.animation.ArgbEvaluator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import baidu.com.androidbaidutakeout.R;
import baidu.com.androidbaidutakeout.modle.net.Seller;
import baidu.com.androidbaidutakeout.presenter.HomeFragmentPresenter;
import baidu.com.androidbaidutakeout.ui.adapter.HomeRvAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 *  创建者:   Administrator
 *  创建时间:  2017/10/17 12:01
 *  描述：    TODO
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.rv_home)
    RecyclerView mRvHome;
    @BindView(R.id.home_tv_address)
    TextView mHomeTvAddress;
    @BindView(R.id.ll_title_search)
    LinearLayout mLlTitleSearch;
    @BindView(R.id.ll_title_container)
    LinearLayout mLlTitleContainer;
    Unbinder unbinder;
    private HomeRvAdapter mHomeRvAdapter;
    private HomeFragmentPresenter mHomeFragmentPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View homeView = View.inflate(getActivity(), R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, homeView);
        mEvaluator = new ArgbEvaluator();
        //recyclerview的初始化(告诉用哪一种布局)
        mHomeFragmentPresenter = new HomeFragmentPresenter(this);
        mRvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeRvAdapter = new HomeRvAdapter(getActivity());
        mRvHome.setAdapter(mHomeRvAdapter);
        return homeView;
    }

    ArgbEvaluator mEvaluator;
    int sumY;
    float distance = 150.0f;
    int start = 0x553190E8;
    int end = 0xFF3190E8;
    int bgColor;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        testData();
        //必须有数据才能滚动
        mRvHome.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                sumY += dy;
                if (sumY <= 0) {
                    bgColor = start;
                } else if (sumY >= distance) {
                    bgColor = end;
                } else {
                    bgColor = (int) mEvaluator.evaluate(sumY / distance, start, end);
                }
                mLlTitleContainer.setBackgroundColor(bgColor);
            }
        });
    }

    private List<String> mDatas = new ArrayList<>();

    private void testData() {
       /* for (int i = 0; i < 100; i++) {
            mDatas.add("我是条目:" + i);
        }
        mHomeRvAdapter.setDatas(mDatas);*/
        mHomeFragmentPresenter.loadHomeInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void onSuccess(List<Seller> nearbySellers, List<Seller> otherSellers) {
        mHomeRvAdapter.setNearbySellers(nearbySellers, otherSellers);
    }

    public void onFailed(String message) {
        Toast.makeText(getActivity(), "对不起,网络繁忙", Toast.LENGTH_SHORT).show();
    }
}
