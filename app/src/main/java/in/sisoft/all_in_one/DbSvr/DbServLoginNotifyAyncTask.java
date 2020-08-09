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

public class DbServLoginNotifyAyncTask extends AsyncTask<String, Void, Void> {

    // Required initialization

//    DbHelper dbHelper ;
    RecyclerView rv ;
    private String strResponse;
    private String Error = null;
    Context my_ctx  ;
    String urlParameters="";


    public DbServLoginNotifyAyncTask(Context ctx)
    {
        my_ctx = ctx ;
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
            URL url = new URL(AppConstants.loginNotify);


            // No criteria to search..


                urlParameters =
                        "gid=" + URLEncoder.encode(urls[0], "UTF-8")+ "&" +
                        "gname=" + URLEncoder.encode(urls[1], "UTF-8") + "&" +
                        "gemail=" + URLEncoder.encode(urls[2], "UTF-8") + "&" +
                        "gplink=" + URLEncoder.encode(urls[3], "UTF-8") + "&" +
                        "gimgurl=" + URLEncoder.encode(urls[4], "UTF-8") ;




            Log.d("DbServLoginNotify",url.toString());
            Log.d("DbServLoginNotify",urlParameters);

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
            Log.i("ws_LoginNotify.php", strResponse);
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
    }

}
