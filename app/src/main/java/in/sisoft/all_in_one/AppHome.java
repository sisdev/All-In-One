package in.sisoft.all_in_one;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

import in.sisoft.all_in_one.DbSvr.DbSync;

public class AppHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btn_contact_admin ;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_contact_admin = findViewById(R.id.button2);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void search_biz(View v)
    {

        startActivity(new Intent(this, BizSearchActivity.class));
    }


    public void ViewBizCategory(View v){
    startActivity(new Intent(this, BusinessCategoryListActivity.class));
    }

    public void ViewPromos(View v){
        startActivity(new Intent(this, PromoListActivity.class));
    }


    public void btn_my_biz(View v){
        startActivity(new Intent(this, GoogleLoginActivity.class));

    }

    public void sync_data() {
        Toast.makeText(this, "Sync with Server", Toast.LENGTH_LONG).show();

        final ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (!(wifi.isConnected() || mobile.isConnected())) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AppHome.this);
            alertBuilder.setTitle("Warning");
            alertBuilder.setMessage("No Internet Connection Available");
            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
 //                   Toast.makeText(AppHome.this, "Positive Confirmation", Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }

            });
            AlertDialog ad = alertBuilder.create();
            ad.show();
        } else {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AppHome.this);
            alertBuilder.setTitle("Sync Data");
            alertBuilder.setMessage("Would you like to sync data from server");
            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(AppHome.this, "Data Sync", Toast.LENGTH_LONG).show();
                    DbSync.databaseSync(getApplicationContext());
                }
            });
            alertBuilder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(AppHome.this, "Cancel Operation", Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }
            });

            AlertDialog ad = alertBuilder.create();
            ad.show();


        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_deal) {
            // Business category
            startActivity(new Intent(this, PromoListActivity.class)) ;
        } else
        if (id == R.id.nav_category) {
            // Business category
            startActivity(new Intent(this, BusinessCategoryListActivity.class));
        } else if (id == R.id.nav_search) {
            // Business search;
            startActivity(new Intent(this, BizSearchActivity.class));
        }else if (id == R.id.nav_biz_owner) {
            // Business Owner - Manage Business and promotions;
            startActivity(new Intent(this, GoogleLoginActivity.class));

        } else if (id == R.id.nav_sync) {
            // Business search;
            sync_data();
        } else if (id == R.id.nav_share) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT,"Use Deals Local App to Explore deals in Indirapuram\n The Url of App is: https://play.google.com/store/apps/details?id=in.sisoft.all_in_one");
            startActivity(share);
        } else if (id == R.id.nav_send) {
            Intent email = new Intent(Intent.ACTION_SEND);
            String[] to ={"appdev@sisoft.in"} ;
            email.putExtra(Intent.EXTRA_EMAIL, to);
            email.putExtra(android.content.Intent.EXTRA_SUBJECT,"Deals Local : Indirapuram - Feedback");
            email.putExtra(Intent.EXTRA_TEXT, "Please send your feedback/question to us");
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Deals Local Indirapuram App:Choose an Email Client"));


        } else if (id == R.id.nav_about_app) {
//            Toast.makeText(this,"Please call 9540-283-283 for any help",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,AboutAppActivity.class));

//            Snackbar sb = Snackbar.make(btn_contact_admin,"Call for business listing",Snackbar.LENGTH_LONG);
//            sb.setAction("Call", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String calluri = "tel:9540283283";
//                    Intent call= new Intent(Intent.ACTION_CALL, Uri.parse(calluri));
//                    int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
//                    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
//                        startActivity(call);
//                    }
//                    else {
//                        Toast.makeText(getApplicationContext()," You are trying calling a phone from App, you need allow App to do this",Toast.LENGTH_LONG).show();
//                        ActivityCompat.requestPermissions(AppHome.this, new String[]{Manifest.permission.CALL_PHONE}, 10);
//                    }
//
//                }
//
//            });
//            sb.show();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}

