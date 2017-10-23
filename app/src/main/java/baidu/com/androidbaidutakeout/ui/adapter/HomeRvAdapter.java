package baidu.com.androidbaidutakeout.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import baidu.com.androidbaidutakeout.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/*
 *  创建者:   Administrator
 *  创建时间:  2017/10/21 11:31
 *  描述：    TODO
 */
public class HomeRvAdapter extends RecyclerView.Adapter {
    public static final int TYPE_NORMAL = 0;//普通的条目
    public static final int TYPE_TITLE = 1;//头布局
    /*
    * 复写此方法,支持多类型item*/

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TITLE;
        } else {
            return TYPE_NORMAL;
        }
    }

    private static final String TAG = "HomeRvAdapter";
    private Context mContext;
    private List<String> mDatas = new ArrayList<>();

    public void setDatas(List<String> datas) {
        mDatas = datas;
    }

    public HomeRvAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建一个holder,控制一个个itemView
        switch (viewType) {
            case TYPE_TITLE:
                View titleView = View.inflate(mContext, R.layout.item_title, null);
                TitleHolder titleHolder = new TitleHolder(titleView);
                return titleHolder;
            case TYPE_NORMAL:
                View normalView = View.inflate(mContext, R.layout.item_home_common, null);
                NormalHolder normalHolder = new NormalHolder(normalView);
                return normalHolder;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //绑定holder和view,给view赋值
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_TITLE:
                TitleHolder titleHolder = (TitleHolder) holder;
                titleHolder.setData("我是头布局----");
                break;
            case TYPE_NORMAL:
                NormalHolder normalHolder = (NormalHolder) holder;
                normalHolder.setData(mDatas.get(position));
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    static class TitleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView mTv;

        TitleHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(String data) {

            mTv.setText(data);
        }
    }

    static class NormalHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView mTv;

        NormalHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(String data) {
            mTv.setText(data);
        }
    }
}
