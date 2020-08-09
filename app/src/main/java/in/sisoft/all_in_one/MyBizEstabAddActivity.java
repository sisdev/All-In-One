package in.sisoft.all_in_one;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import in.sisoft.all_in_one.DbSvr.DbHelper;
import in.sisoft.all_in_one.DbSvr.DbServMyBizAddAsycTask;
import in.sisoft.all_in_one.pojo.AppConstants;
import in.sisoft.all_in_one.pojo.BizCategory;

public class MyBizEstabAddActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button btn_save ;
    Spinner spin_bcat;
    DbHelper dbh ;
    int biz_cat_id = 0;
    String biz_cat_name = "" ;
    ArrayList<BizCategory> alistBizCat ;
    ArrayList<String> alistCatName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (!(wifi.isConnected() || mobile.isConnected())) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Warning");
            alertBuilder.setMessage("No Internet Connection Available. Please try with internet connection");
            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }

            });
            AlertDialog ad = alertBuilder.create();
            ad.show();
        }
        else {

/*
            WebView wv = new WebView(this);
            setContentView(wv);
            wv.loadUrl("http://www.innoforia.com/biz-classified");
            wv.setWebViewClient(new MyWebViewClient(this));

 */


            setContentView(R.layout.activity_listing_request);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            dbh = new DbHelper(this);
            alistBizCat = dbh.query_biz_category_array(0);  // Ascending order of the name
            alistCatName = new ArrayList<>();
            for (BizCategory x:alistBizCat)
                alistCatName.add(x.name);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.spinner_bizcat_layout,alistCatName);
            spin_bcat = findViewById(R.id.spinner);
            spin_bcat.setAdapter(arrayAdapter);
            spin_bcat.setOnItemSelectedListener(this);
            btn_save = findViewById(R.id.btn_save);
            btn_save.setOnClickListener(this);

        }



    }

    public void saveListingRequest()
    {
        boolean valid = true;
        String strPrimaryPhone, strBizName, strBizDetails, strAddress1, strAddress2, strAddress3;
        String strEmail, strWebSite, strAlternatePhone ;
        EditText et_primary_phone, et_biz_name, et_biz_details, et_address1, et_address2, et_address3 ;
        EditText et_email, et_website, et_alt_phone ;
        et_primary_phone = findViewById(R.id.biz_phone);
        et_biz_name = findViewById(R.id.biz_name);
        et_biz_details = findViewById(R.id.biz_details);
        et_address1 = findViewById(R.id.address1);
        et_address2 = findViewById(R.id.address2);
        et_address3 = findViewById(R.id.address3);
        et_email = findViewById(R.id.email);
        et_website = findViewById(R.id.web_site);
        et_alt_phone = findViewById(R.id.phone2);

        strPrimaryPhone = et_primary_phone.getText().toString().trim();
        strBizName = et_biz_name.getText().toString().trim();
        strBizDetails = et_biz_details.getText().toString().trim();
        strAddress1 = et_address1.getText().toString().trim();
        strAddress2 = et_address2.getText().toString().trim();
        strAddress3 = et_address3.getText().toString().trim();
        strEmail = et_email.getText().toString().trim();
        strWebSite = et_website.getText().toString().trim();
        strAlternatePhone = et_alt_phone.getText().toString().trim();

        if (strPrimaryPhone.length() == 0) {
            et_primary_phone.setError("Primary Phone Required");
            valid = false;
        }
        if (strBizName.length() == 0) {
            et_biz_name.setError("Business Name Required");
            valid = false;
        }
        if (strBizDetails.length() == 0) {
            et_biz_details.setError("Business Details Required");
            valid = false;
        }
        if (strAddress1.length() == 0) {
            et_address1.setError("Street Address Required");
            valid = false;
        }
        if (strAddress2.length() == 0) {
            et_address2.setError("Khand Name Required");
            valid = false;
        }
        if (strAddress3.length() == 0) {
            strAddress3= "No ShoppingComplex";
        }
        if (strEmail.length() == 0) {
            strEmail="NoEmail@sisoft.in";
        }

        if (strWebSite.length() == 0) {
            strWebSite="No Web Site";
        }
        if (strAlternatePhone.length() == 0) {
            strAlternatePhone="No Alternate Phone";
        }

        if (biz_cat_name.length()==0){
           // spin_bcat.set
        }
        SharedPreferences sp1 = this.getSharedPreferences(AppConstants.SHARED_PREF_NAME1,Context.MODE_PRIVATE);
        String strUserLogin= sp1.getString(AppConstants.user_login,"ANDROID LOGIN");
 //       editor.putString(AppConstants.user_login,personEmail);


        if (valid == true){
            String strBizCatID = String.valueOf(biz_cat_id) ;
            DbServMyBizAddAsycTask u3 = new DbServMyBizAddAsycTask();
            u3.execute(strPrimaryPhone,strBizName,strBizDetails,strAddress1,strAddress2, strAddress3, strEmail, strWebSite, strAlternatePhone, strBizCatID, strUserLogin);
            Toast.makeText(this,"Listing request submitted and will be processed after verification", Toast.LENGTH_LONG).show() ;
            finish();
        }
        else{
            Toast.makeText(this,"Please rectify the errors", Toast.LENGTH_LONG).show() ;

        }


    }

    @Override
    public void onClick(View view) {
        saveListingRequest();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        biz_cat_name = alistCatName.get(position);
        biz_cat_id = alistBizCat.get(position).getId() ;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class MyWebViewClient extends WebViewClient {
        Context ctx ;
        MyWebViewClient(Context c){
            ctx = c ;
        }
        @Override public boolean shouldOverrideUrlLoading(WebView view, final String url){
        if (Uri.parse(url).getHost().equals("www.innoforia.com")) {

        // This is my web site, so do not override; let my WebView load the page           
                return false;
            }

        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs       
            final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx);
            alertBuilder.setTitle("Warning");
            alertBuilder.setMessage("You are moving to browser for your further actions. On return, please sync and start");
            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);

                }

            });
            AlertDialog ad = alertBuilder.create();
            ad.show();
            return true;
        }
    }

}

