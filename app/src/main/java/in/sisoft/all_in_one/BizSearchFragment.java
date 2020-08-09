package in.sisoft.all_in_one;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
//import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class BizSearchFragment extends Fragment {


    private OnBSCFragmentInteractionListener mListener;

    public BizSearchFragment() {
    }

    ImageButton btn1 ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.biz_search_fragment, container, false);
        btn1 = rootView.findViewById(R.id.btn_search);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edt1 = rootView.findViewById(R.id.searchText);
                String str1 = edt1.getText().toString().trim();
                if (str1.length()<3){
                    edt1.setError("Enter Search String");

                }
                else{
                    Toast.makeText(getActivity(),"Searching for word:"+ str1, Toast.LENGTH_LONG).show() ;
                    mListener.openBSRFragment(str1);
                }
            }
        });
        return rootView ;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        Toast.makeText(context,"In Attach Biz Search Fragment-Context", Toast.LENGTH_LONG).show();
        if (context instanceof OnBSCFragmentInteractionListener) {
            mListener = (OnBSCFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBSRFragmentInteractionListener");
        }
    }

    /* Since API Level 23, OAttach(Activity activity) is deprecated and new alternative is
    onAttach(Context). How ever
     */

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
//        Toast.makeText(context,"In Attach Biz Search Fragment-Activity", Toast.LENGTH_LONG).show();
        if (context instanceof OnBSCFragmentInteractionListener) {
            mListener = (OnBSCFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBSRFragmentInteractionListener");
        }
    }



    public interface OnBSCFragmentInteractionListener {
        // TODO: Update argument type and name
        void openBSRFragment(String s1);
    }
}
