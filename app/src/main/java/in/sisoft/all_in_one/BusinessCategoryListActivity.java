package in.sisoft.all_in_one;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.sisoft.all_in_one.DbSvr.DbHelper;
import in.sisoft.all_in_one.DbSvr.DbSyncBizCategory;
import in.sisoft.all_in_one.pojo.BizCategory;

/**
 * An activity representing a list of Categories. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BusinessEstablishmentActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BusinessCategoryListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    DbHelper dbh ;
    View recyclerView ;
    int sort_order = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businesscategory_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

            if (findViewById(R.id.business_establishment_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbh = new DbHelper(this);

        recyclerView = findViewById(R.id.businesscategory_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView, sort_order);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.category_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.sort_alpha:
                recyclerView = findViewById(R.id.businesscategory_list);
                assert recyclerView != null;
                sort_order = sort_order ^ 1 ;  // Flip the values between 0 and 1
                setupRecyclerView((RecyclerView) recyclerView, sort_order);
                break ;
            case android.R.id.home:
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. Use NavUtils to allow users
                    // to navigate up one level in the application structure. For
                    // more details, see the Navigation pattern on Android Design:
                    //
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                    //
                    //NavUtils.navigateUpTo(this, new Intent(this, AppHome.class));
                    finish() ;
                    return true;

        }
        return true;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, int sort_order) {
        ArrayList<BizCategory> al_bc2 = dbh.query_biz_category_array(sort_order);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(al_bc2));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<BizCategory> mValues;

        public SimpleItemRecyclerViewAdapter(List<BizCategory> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.businesscategory_list_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
 //           holder.mIdView.setText(mValues.get(position).id+"");

            // Just show the position
            holder.mIdView.setText((position+1)+"");
            holder.mContentView.setText(mValues.get(position).name.trim());
            if (position%2==0) {
                holder.mView.setBackgroundColor(getResources().getColor(R.color.colorRow));
            }
            else {
                holder.mView.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
            }

            holder.mBtnViewBiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                      //  Toast.makeText(getApplicationContext(),"Two Pane Mode->BusinessEstablishmentList",Toast.LENGTH_LONG).show();
                        Bundle arguments = new Bundle();
                        arguments.putString(BusinessEstablishmentFragment.ARG_BIZ_CAT_ID, String.valueOf(holder.mItem.id));
                        arguments.putString(BusinessEstablishmentFragment.ARG_BIZ_CAT_NAME, holder.mItem.name);

                        BusinessEstablishmentFragment fragment = new BusinessEstablishmentFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.business_establishment_container, fragment)
                                .commit();
                    } else {
                      //  Toast.makeText(getApplicationContext(),"Single Pane Mode->BusinessEstablishmentList",Toast.LENGTH_LONG).show();
                        Context context = v.getContext();
                        Intent intent = new Intent(getApplicationContext(), BusinessEstablishmentActivity.class);
                        Log.d("Biz Category ID",holder.mItem.id+":::");
                        intent.putExtra(BusinessEstablishmentFragment.ARG_BIZ_CAT_ID, holder.mItem.id);
                        intent.putExtra(BusinessEstablishmentFragment.ARG_BIZ_CAT_NAME, holder.mItem.name);
                        context.startActivity(intent);
                    }
                }
            });
            holder.mBtnViewDeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Toast.makeText(context,"Will come up soon",Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public final Button mBtnViewBiz ;
            public final Button mBtnViewDeal ;
            public BizCategory mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = view.findViewById(R.id.id);
                mContentView = view.findViewById(R.id.content);
                mBtnViewDeal = view.findViewById(R.id.viewDeal);
                mBtnViewBiz = view.findViewById(R.id.viewBiz);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
