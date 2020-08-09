package in.sisoft.all_in_one.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.sisoft.all_in_one.MyBizPromoManageActivity;
import in.sisoft.all_in_one.R;
import in.sisoft.all_in_one.pojo.BizCategory;
import in.sisoft.all_in_one.pojo.BizEstablishment;

/**
 * Created by vijay on 14-Jan-18.
 */
public class EstablishmentRecyclerViewAdapter
        extends RecyclerView.Adapter<EstablishmentRecyclerViewAdapter.EstablishmentViewHolder> {

    private final List<BizEstablishment> mValues;
    Activity a1 ;
    int viewOption ; // 0 - user View 1- Owner View

    public EstablishmentRecyclerViewAdapter(List<BizEstablishment> items, Activity act, int option) {

        mValues = items;
        a1 = act ;
        viewOption = option ;

    }

    @Override
    public EstablishmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_biz_establishment_layout, parent, false);
        return new EstablishmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EstablishmentViewHolder holder, int position) {
        final BizEstablishment mItem= mValues.get(position);
        holder.mTitle.setText(mItem.biz_name);
        holder.mDetails.setText(mItem.biz_details);
        holder.mAddress.setText(mItem.biz_street+","+mItem.biz_city);
        holder.mPhone1.setText(mItem.biz_phone1);
        if (viewOption==1){
            holder.callNow.setVisibility(View.GONE);
        }
        else
        {
            holder.managePromo.setVisibility(View.GONE);
        }

        holder.numPromo.setVisibility(View.GONE);  // Number of active promotion not being displayed now - TODO


//        holder.mPhone1.setText("Call Phone");

        //  Toast.makeText(a1,mItem.biz_phone1,Toast.LENGTH_LONG).show();
        // holder.mWebsite.setText(mItem.);

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("OnClick", "Implement onclick here ");
//                Toast.makeText(a1,"View Clicked",Toast.LENGTH_LONG).show();
//            }
//        });

        holder.callNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(a1,"Call Phone Opted:"+mItem.biz_phone1,Toast.LENGTH_LONG).show();
                String calluri = "tel:"+mItem.biz_phone1;
                Intent call= new Intent(Intent.ACTION_DIAL, Uri.parse(calluri));
       //         int permissionCheck = ContextCompat.checkSelfPermission(a1,Manifest.permission.CALL_PHONE);
       //         if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    a1.startActivity(call);
       //         }
       //         else {
       //             Toast.makeText(a1," You are trying calling a phone from App, you need allow App to do this",Toast.LENGTH_LONG).show();
       //             ActivityCompat.requestPermissions(a1, new String[]{Manifest.permission.CALL_PHONE}, 10);
       //         }


            }
        });

        holder.managePromo.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                    Intent i2 = new Intent(a1, MyBizPromoManageActivity.class);
                    i2.putExtra("BizId", String.valueOf(mItem.biz_id));
                    i2.putExtra("BizName",mItem.biz_name);
                    a1.startActivity(i2);                             }
                                              }
        );


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class EstablishmentViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final TextView mDetails ;
        public final TextView mAddress;
        public final TextView mPhone1;
        public final Button callNow;
        public final TextView numPromo ;
        public final Button managePromo ;
        //  public final Button mWebsite;

        public BizCategory mItem;

        public EstablishmentViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = view.findViewById(R.id.title);
            mDetails = view.findViewById(R.id.details);
            mAddress = view.findViewById(R.id.address);
            mPhone1 = view.findViewById(R.id.phone1);
            callNow = view.findViewById(R.id.call_phone);
            numPromo = view.findViewById(R.id.numPromotions);
            managePromo = view.findViewById(R.id.managePromo);

            // mPhone2 = (TextView) view.findViewById(R.id.web_site);
        }
    }
}