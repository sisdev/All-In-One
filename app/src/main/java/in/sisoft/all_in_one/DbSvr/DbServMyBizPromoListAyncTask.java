package in.sisoft.all_in_one.DbSvr;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import in.sisoft.all_in_one.adapter.MyBizPromoRecyclerViewAdapter;
import in.sisoft.all_in_one.pojo.AppConstants;
import in.sisoft.all_in_one.pojo.Promotions;

/**
 * Created by vijay on 26-July-2020.
 */


// Class with extends AsyncTask class

public class DbServMyBizPromoListAyncTask extends AsyncTask<String, Void, Void> {

    // Required initialization

//    DbHelper dbHelper ;
    RecyclerView rv ;
    private String strResponse;
    private String Error = null;
    Context my_ctx  ;
    Activity a1 ;
    String urlParameters="";


    public DbServMyBizPromoListAyncTask(Context ctx, RecyclerView v1, Activity act)
    {
        my_ctx = ctx ;
        rv = v1 ;
        a1 = act ;
    }


    String data ="";
    int sizeData = 0;


    protected void onPreExecute() {
        try{
            // Set Request parameter
            data +="&" + URLEncoder.encode("data", "UTF-8") ;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // Call after onPreExecute method
    protected Void doInBackground(String... urls) {

        /************ Make Post Call To Web Server ***********/
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(AppConstants.myBizPromoList);


            // No criteria to search..

            if (urls.length==1) {
                urlParameters =
                        "biz_id=" + URLEncoder.encode(urls[0], "UTF-8");
            }


            Log.d("DbServPromoList",url.toString());
            Log.d("DbServPromoList",urlParameters);

            // Send POST data request

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);


            //Send request
            DataOutputStream wr = new DataOutputStream(
                    httpURLConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();


            // Get the server response

            reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + " ");
            }

            // Append Server Response To Content String
            strResponse = sb.toString();
            Log.i("ws_my_bizPromoList.php", strResponse);
        }
        catch(Exception ex)
        {
            Error = ex.getMessage();
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {}
        }

        /*****************************************************/
        return null;
    }

    protected void onPostExecute(Void unused) {
        // NOTE: You can call UI Element here.

        // Close progress dialog
    //    Dialog.dismiss();
        ArrayList<Promotions> myBizPromotionList = new ArrayList<Promotions>() ;

        if (Error != null) {

            Log.i("WS Calling Error", Error);

        } else {

            // Show Response Json On Screen (activity)

            Log.i("WS Calling Successful", strResponse);


            /****************** Start Parse Response JSON Data *************/

            String OutputData = "";
            JSONObject jsonResponse;

            try {

                /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                jsonResponse = new JSONObject(strResponse);

                /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                /*******  Returns null otherwise.  *******/
                JSONArray jsonMainNode = jsonResponse.optJSONArray("data");

                /*********** Process each JSON Node ************/

                int lengthJsonArr = jsonMainNode.length();

                if (lengthJsonArr==0)
                {
                    Log.d("DbServPromoListAsync", "No Records Available for download & sync" );
                }
                else
                {
                    Log.d("DbServPromoListAsync", lengthJsonArr+ ": Records Available for download & sync" );
                }
                for(int i=0; i < lengthJsonArr; i++)
                {
                    /****** Get Object for each JSON node.***********/
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    Promotions p1 = new Promotions();
                    /******* Fetch node values **********/
                    p1.promo_id = Integer.parseInt(jsonChildNode.optString("promo_id"));
                    p1.biz_id = Integer.parseInt(jsonChildNode.optString("biz_id"));
                    p1.promo_code       = jsonChildNode.optString("promo_code");
                    p1.promo_text1      = jsonChildNode.optString("promo_text1");
                    p1.promo_text2      = jsonChildNode.optString("promo_text2");
                    p1.promo_start_dt   = jsonChildNode.optString("promo_start_dt");
                    p1.promo_end_dt     = jsonChildNode.optString("promo_end_dt");
                    p1.biz_name = jsonChildNode.optString("biz_name");
                    p1.biz_phone = jsonChildNode.optString("biz_phone1");
                    p1.biz_address = jsonChildNode.optString("biz_street")+","+ jsonChildNode.optString("biz_khand");

                    myBizPromotionList.add(p1);
                    OutputData += "Biz Id       : "+ p1.biz_id +" "
                            + "Promo Id         : "+ p1.promo_id +" "
                            + "Promo Code       : "+ p1.promo_code +" "
                            + "Promo Text1      : "+ p1.promo_text1 +" "
                            + "Promo Text2      : "+ p1.promo_text2 +" "
                            + "Promo Start Date : "+ p1.promo_start_dt +" "
                            + "Promo End Date   : "+ p1.promo_end_dt +" "
                            +"--------------------------------------------------";

                //    dbHelper.insert_update_biz_establishment(b1);
                    //           dbhelper.insertqp(Integer.parseInt(id),  course_name, file_name, file_loc, date_added);

                }
                /****************** End Parse Response JSON Data *************/

                //Show Parsed Output on screen (activity)
                //  jsonParsed.setText( OutputData );
                Log.i("JSON Data", OutputData);
                //  ListExamData() ;
                setupRecyclerView(rv,myBizPromotionList );
            } catch (JSONException e) {

                e.printStackTrace();
            }


        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, ArrayList<Promotions> al_be1) {
        Log.d("Num Promos", al_be1.size() + "");
        if (al_be1.size() == 0) {
            Toast.makeText(my_ctx, "No promos listed for this business", Toast.LENGTH_LONG).show();
        }
        else {
            MyBizPromoRecyclerViewAdapter rva = new MyBizPromoRecyclerViewAdapter(al_be1, a1,1);
            recyclerView.setAdapter(rva);
//            recyclerView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getActivity(), "Set On Click", Toast.LENGTH_LONG).show();
//                }
//            });
        }
    }


}
