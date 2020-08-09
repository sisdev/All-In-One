package in.sisoft.all_in_one;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import in.sisoft.all_in_one.DbSvr.DbServMyBizListAyncTask;
import in.sisoft.all_in_one.pojo.AppConstants;

public class MyBizEstabActivity extends AppCompatActivity implements BizSearchResultFragment.OnBSRFragmentInteractionListener, BizSearchFragment.OnBSCFragmentInteractionListener{

    RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.biz_search_activity);
        setContentView(R.layout.my_biz_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.business_list);
        assert recyclerView != null;
//        String serverURL = "http://www.sisoft.in/all_in_one/ws_my_bizList.php?user_id='info@sisoft.in'"; //HARD CODED
//        Log.d("ListQuesPaper", serverURL);
        String strUserLogin = AppConstants.getUserLogin(this);
        DbServMyBizListAyncTask myBizAyncTask = new DbServMyBizListAyncTask(getApplicationContext(),recyclerView, this);
        // Use AsyncTask execute Method To Prevent ANR Problem

        myBizAyncTask.execute(strUserLogin);


//        setupRecyclerView((RecyclerView) recyclerView, mParam1);


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_biz_option, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_biz:
               // newGame();
                startActivity(new Intent(this, MyBizEstabAddActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void openBSRFragment(String str1) {
        BizSearchResultFragment bsf = BizSearchResultFragment.newInstance(str1,"");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        BizSearchResultFragment b2 = (BizSearchResultFragment) fm.findFragmentByTag("search_results");
        if (b2 == null) {
            ft.add(R.id.search_result_frame, bsf, "search_results");
        }
        else
        {
            ft.replace(R.id.search_result_frame,bsf,"search_results");
        }
        ft.commit();

    }
}
