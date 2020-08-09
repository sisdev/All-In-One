package in.sisoft.all_in_one;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BizSearchActivity extends AppCompatActivity implements BizSearchResultFragment.OnBSRFragmentInteractionListener, BizSearchFragment.OnBSCFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biz_search_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
