package in.sisoft.all_in_one;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import in.sisoft.all_in_one.DbSvr.DbServAllPromoListAyncTask;

public class PromoListActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_biz_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        recyclerView = findViewById(R.id.business_list);
        assert recyclerView != null;

        DbServAllPromoListAyncTask myAyncTask = new DbServAllPromoListAyncTask(getApplicationContext(),recyclerView, this);

        // Use AsyncTask execute Method To Prevent ANR Problem

        myAyncTask.execute();



    }

}
