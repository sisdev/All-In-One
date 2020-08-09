package in.sisoft.all_in_one;

import android.app.Activity;

import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.sisoft.all_in_one.DbSvr.DbHelper;
import in.sisoft.all_in_one.adapter.EstablishmentRecyclerViewAdapter;
import in.sisoft.all_in_one.pojo.BizEstablishment;


/**
 * A fragment representing a single Business-Category detail screen.
 * This fragment is either contained in a {@link BusinessCategoryListActivity}
 * in two-pane mode (on tablets) or a {@link BusinessEstablishmentActivity}
 * on handsets.
 */
public class BusinessEstablishmentFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_BIZ_CAT_ID = "biz_cat_id";
    public static final String ARG_BIZ_CAT_NAME = "biz_cat_name";

    DbHelper dbh ;
    int biz_cat_id ;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BusinessEstablishmentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbh = new DbHelper(getActivity());

        biz_cat_id=Integer.parseInt(getArguments().getString(ARG_BIZ_CAT_ID));

        if (getArguments().containsKey(ARG_BIZ_CAT_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            Activity activity = this.getActivity();
//            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
//            if (appBarLayout != null) {
//                appBarLayout.setTitle(getArguments().getString(ARG_BIZ_CAT_NAME));
//            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.businesscategory_list, container, false);

        View recyclerView = rootView.findViewById(R.id.businesscategory_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);


        return rootView;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        ArrayList<BizEstablishment> al_be1 = dbh.query_biz_establishments(biz_cat_id);
        Log.d("Num Establishments",al_be1.size()+"");
        recyclerView.setAdapter(new EstablishmentRecyclerViewAdapter(al_be1,getActivity(),0));
//        recyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),"Set On Click", Toast.LENGTH_LONG).show();
//            }
//        });
    }



    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }
}


