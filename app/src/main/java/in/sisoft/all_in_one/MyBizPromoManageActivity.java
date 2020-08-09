package in.sisoft.all_in_one;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import in.sisoft.all_in_one.DbSvr.DbServMyBizPromoListAyncTask;

public class MyBizPromoManageActivity extends AppCompatActivity {


    RecyclerView recyclerView ;
    String strBizId, strBizName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_biz_promo_manage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar() ;
        ab.setDisplayHomeAsUpEnabled(true);

        Intent i1 = getIntent() ; //call ing intent
        strBizId = i1.getStringExtra("BizId");
        strBizName = i1.getStringExtra("BizName");

        ab.setTitle("Manage Promotions:"+strBizName);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(getApplicationContext(), MyBizPromoAddActivity.class);
                i2.putExtra("BizId", String.valueOf(strBizId));
                i2.putExtra("BizName",strBizName);
                Snackbar.make(view, "Add another Promotion in this business:"+ strBizName, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(i2);
            }
        });


        recyclerView = findViewById(R.id.promo_recycler);
        assert recyclerView != null;
//        String serverURL = AppConstants.myBizPromoList ;
//        serverURL.concat("'"+strBizId+"'");
//        Log.d("myBizPromoManage", serverURL);

        DbServMyBizPromoListAyncTask myAyncTask = new DbServMyBizPromoListAyncTask(getApplicationContext(),recyclerView, this);

        // Use AsyncTask execute Method To Prevent ANR Problem

        myAyncTask.execute(strBizId);


    }
}