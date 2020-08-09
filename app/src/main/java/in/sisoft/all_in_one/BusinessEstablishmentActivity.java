package in.sisoft.all_in_one;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import in.sisoft.all_in_one.DbSvr.DbSyncBizEstablishment;


/**
 * An activity representing a single Business-Category detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link BusinessCategoryListActivity}.
 */
public class BusinessEstablishmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businessestablishment_list);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        String cat_name = getIntent().getStringExtra(BusinessEstablishmentFragment.ARG_BIZ_CAT_NAME);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
              actionBar.setDisplayHomeAsUpEnabled(true);
            if (cat_name.length() != 0)
              actionBar.setTitle(cat_name);
            else
                actionBar.setTitle("Business-Category Details");
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            String str_biz_id=String.valueOf(getIntent().getIntExtra(BusinessEstablishmentFragment.ARG_BIZ_CAT_ID,0));
            Log.d("Str_Biz_Id", str_biz_id);
            arguments.putString(BusinessEstablishmentFragment.ARG_BIZ_CAT_ID, str_biz_id);
            BusinessEstablishmentFragment fragment = new BusinessEstablishmentFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.business_establishment_container, fragment)
                    .commit();
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.category_menu,menu);
        return true;
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
           // NavUtils.navigateUpTo(this, new Intent(this, BusinessCategoryListActivity.class));
            finish() ;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sync_with_server()
    {
        Toast.makeText(this, "Sync with Server", Toast.LENGTH_LONG).show();

        final ConnectivityManager connMgr = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (!(wifi.isConnected() || mobile.isConnected()))
        {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(BusinessEstablishmentActivity.this);
            alertBuilder.setTitle("Warning");
            alertBuilder.setMessage("No Internet Connection Available");
            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()  {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(BusinessEstablishmentActivity.this, "Positive Confirmation", Toast.LENGTH_LONG).show();
                    dialog.cancel();  }
            });

            AlertDialog ad = alertBuilder.create();
            ad.show() ;
        }
        else
        {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(BusinessEstablishmentActivity.this);
            alertBuilder.setTitle("Sync Data");
            alertBuilder.setMessage("Would you like to sync data from server");


            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(BusinessEstablishmentActivity.this, "Data Sync", Toast.LENGTH_LONG).show();
                    // Max Row Count after which record needs to be fetched ..
                  //  int max_id = dbhelper.getMaxQpRowCount();

                    // WebServer Request URL
                    String serverURL = "http://www.sisoft.in/all_in_one/ws_best.php?max_id=0"; //HARD CODED
                    Log.d("ListQuesPaper", serverURL);

                    // Use AsyncTask execute Method To Prevent ANR Problem
                    new DbSyncBizEstablishment(getApplicationContext()).execute(serverURL);

                    // Reload the exam data if some thing new might have come

                    //      ListExamData() ; - Long operation might not have completed here

                    dialog.cancel();

                } } );


            alertBuilder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(BusinessEstablishmentActivity.this, "Cancel Operation", Toast.LENGTH_LONG).show();
                    dialog.cancel();} });

            AlertDialog ad = alertBuilder.create();
            ad.show() ;

        }

    }




}



