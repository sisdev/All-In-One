package in.sisoft.all_in_one.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.sisoft.all_in_one.R;
import in.sisoft.all_in_one.pojo.BizCategory;
import in.sisoft.all_in_one.pojo.Promotions;

/**
 * Created by vijay on 14-Jan-18.
 */
public class PromoListRecyclerViewAdapter
        extends RecyclerView.Adapter<PromoListRecyclerViewAdapter.PromotionsViewHolder> {

    private final List<Promotions> mValues;
    Activity a1 ;
    int viewOption ; // 0 - user View 1- Owner View

    public PromoListRecyclerViewAdapter(List<Promotions> items, Activity act, int option) {

        mValues = items;
        a1 = act ;
        viewOption = option ;

    }

    @Override
    public PromotionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_all_promo_layout, parent, false);
        return new PromotionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PromotionsViewHolder holder, int position) {
        final Promotions mItem= mValues.get(position);
        holder.mPromoCode.setText(mItem.getPromo_code());
        holder.mPromoText1.setText(mItem.getPromo_text1());
        holder.mPromoText2.setText(mItem.getPromo_text2());
        holder.mValidity.setText("Validity Upto:"+mItem.getPromo_end_dt());
        holder.mBizName.setText(mItem.biz_name);
        holder.mBizAddress.setText(mItem.biz_address);
        holder.mBizPhone.setText(mItem.biz_phone);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class PromotionsViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPromoCode;
        public final TextView mPromoText1 ;
        public final TextView mPromoText2;
        public final TextView mValidity;
        public final TextView mBizName ;
        public final TextView mBizAddress ;
        public final TextView mBizPhone ;


        //  public final Button mWebsite;

        public BizCategory mItem;

        public PromotionsViewHolder(View view) {
            super(view);
            mView = view;
            mPromoCode = view.findViewById(R.id.promo_code);
            mPromoText1 = view.findViewById(R.id.promo_text1);
            mPromoText2 = view.findViewById(R.id.promo_text2);
            mValidity = view.findViewById(R.id.validity);
            mBizName = view.findViewById(R.id.biz_name);
            mBizAddress = view.findViewById(R.id.biz_address);
            mBizPhone = view.findViewById(R.id.biz_phone);



            // mPhone2 = (TextView) view.findViewById(R.id.web_site);
        }
    }
}