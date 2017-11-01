package baidu.com.androidbaidutakeout.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import baidu.com.androidbaidutakeout.R;
import baidu.com.androidbaidutakeout.modle.net.Seller;
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
    //    private List<String> mDatas = new ArrayList<>();//模拟数据
    private List<Seller> mAllSellers = new ArrayList<>();
    private List<Seller> mNearbySellers = new ArrayList<>();
    private List<Seller> mOtherSellers = new ArrayList<>();

    public void setNearbySellers(List<Seller> nearbySellers, List<Seller> otherSellers) {
        mNearbySellers = nearbySellers;
        mOtherSellers = otherSellers;
        mAllSellers.clear();
        mAllSellers.addAll(mNearbySellers);
        mAllSellers.addAll(mOtherSellers);
        notifyDataSetChanged();
    }

    /*  public void setDatas(List<String> datas) {
          mDatas = datas;
          notifyDataSetChanged();
      }
  */
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
                View normalView = View.inflate(mContext, R.layout.item_seller, null);
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
                normalHolder.setData(mAllSellers.get(position));
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (mAllSellers != null) {
            return mAllSellers.size();
        }
        return 0;
    }


    static class TitleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.slider)
        SliderLayout mSlider;

        TitleHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(String data) {
            HashMap<String, String> url_maps = new HashMap<String, String>();
            url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
            url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
            url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
            url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

            for (String desc : url_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(itemView.getContext());
                textSliderView
                        .description(desc)
                        .image(url_maps.get(desc));
                mSlider.addSlider(textSliderView);
            }
        }
    }


    static class NormalHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.seller_logo)
        ImageView mSellerLogo;
        @BindView(R.id.tvCount)
        TextView mTvCount;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.ratingBar)
        RatingBar mRatingBar;
        @BindView(R.id.tv_home_sale)
        TextView mTvHomeSale;
        @BindView(R.id.tv_home_send_price)
        TextView mTvHomeSendPrice;
        @BindView(R.id.tv_home_distance)
        TextView mTvHomeDistance;

        NormalHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


        public void setData(Seller seller) {
            mTvTitle.setText(seller.getName());
        }
    }


}

