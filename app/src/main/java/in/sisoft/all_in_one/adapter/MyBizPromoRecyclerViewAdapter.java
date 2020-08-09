package in.sisoft.all_in_one.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.sisoft.all_in_one.DbSvr.DbServPromoManageAsyncTask;
import in.sisoft.all_in_one.R;
import in.sisoft.all_in_one.pojo.AppConstants;
import in.sisoft.all_in_one.pojo.BizCategory;
import in.sisoft.all_in_one.pojo.Promotions;

/**
 * Created by vijay on 14-Jan-18.
 */
public class MyBizPromoRecyclerViewAdapter
        extends RecyclerView.Adapter<MyBizPromoRecyclerViewAdapter.PromotionsViewHolder> {

    private final List<Promotions> mValues;
    Activity a1 ;
    int viewOption ; // 0 - user View 1- Owner View

    public MyBizPromoRecyclerViewAdapter(List<Promotions> items, Activity act, int option) {

        mValues = items;
        a1 = act ;
        viewOption = option ;

    }

    @Override
    public PromotionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_biz_promo_layout, parent, false);
        return new PromotionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PromotionsViewHolder holder, int position) {
        final Promotions mItem= mValues.get(position);
        holder.mPromoCode.setText(mItem.getPromo_code());
        holder.mPromoText1.setText(mItem.getPromo_text1());
        holder.mPromoText2.setText(mItem.getPromo_text2());
        holder.mValidity.setText("Validity From: "+mItem.getPromo_start_dt()+" Upto:"+mItem.getPromo_end_dt());
/*
        if (viewOption==1){
            holder.callNow.setVisibility(View.GONE);
        }
        else
        {
            holder.numPromo.setVisibility(View.GONE);
            holder.managePromo.setVisibility(View.GONE);
        }
*/
        holder.deleteNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(a1);
                alertBuilder.setTitle("Deals Local");
                alertBuilder.setMessage("Delete Promo:"+mItem.promo_text1);
                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strUserId = AppConstants.getUserLogin(a1);
                        String strPromoId = String.valueOf(mItem.promo_id);
                        DbServPromoManageAsyncTask p1 = new DbServPromoManageAsyncTask();
                        p1.execute("delete",strPromoId,strUserId);
                        Toast.makeText(a1, "Delete Promo Code :"+strPromoId+":"+mItem.getPromo_code(), Toast.LENGTH_LONG).show();
                        a1.finish();
                        // Delete this promo code for particular Id and User Id. No other user Id can delete the promo.
                        // Also need to encode this value - TODO
                        dialog.cancel();
                    } } );
                alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    } });

                AlertDialog ad = alertBuilder.create();
                ad.show() ;



            }
        });



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
        public final Button deleteNow;
        //  public final Button mWebsite;

        public BizCategory mItem;

        public PromotionsViewHolder(View view) {
            super(view);
            mView = view;
            mPromoCode = view.findViewById(R.id.promo_code);
            mPromoText1 = view.findViewById(R.id.promo_text1);
            mPromoText2 = view.findViewById(R.id.promo_text2);
            mValidity = view.findViewById(R.id.validity);
            deleteNow = view.findViewById(R.id.deletePromo);

            // mPhone2 = (TextView) view.findViewById(R.id.web_site);
        }
    }
}