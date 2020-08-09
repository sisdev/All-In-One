package in.sisoft.all_in_one.DbSvr;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
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

import in.sisoft.all_in_one.AppHome;
import in.sisoft.all_in_one.R;
import in.sisoft.all_in_one.adapter.EstablishmentRecyclerViewAdapter;
import in.sisoft.all_in_one.pojo.AppConstants;
import in.sisoft.all_in_one.pojo.BizEstablishment;

/**
 * Created by vijay on 03-Dec-17.
 */
// http://androidexample.com/Restful_Webservice_Call_And_Get_And_Parse_JSON_Data-_Android_Example/index.php?view=article_discription&aid=101&aaid=123


// Class with extends AsyncTask class

public class DbServCheckMsgAyncTask extends AsyncTask<String, Void, Void> {

    // Required initialization

    DbHelper dbHelper ;
    RecyclerView rv ;
    private String Content;
    private String Error = null;
    Context my_ctx  ;
    Activity a1 ;
    String urlParameters="" ;

    private NotificationManager mNotificationManager;
    private Notification notification;
    private int notificationCount = 0;

    String CHANNEL_ID = "10" ;


    public DbServCheckMsgAyncTask(Context ctx)
    {
        my_ctx = ctx ;
        dbHelper = new DbHelper(ctx);
    }


    int sizeData = 0;



    // Call after onPreExecute method
    protected Void doInBackground(String... urls) {

        /************ Make Post Call To Web Server ***********/
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(AppConstants.CheckMsg);

            if (urls[0].length()!=0) {
                urlParameters =
                        "msg_dt=" + URLEncoder.encode(urls[0], "UTF-8");
            }

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
            Content = sb.toString();
            Log.i("ws_best.php", Content);
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
        ArrayList<BizEstablishment> myBizEstablishmentList = new ArrayList<BizEstablishment>() ;

        this.mNotificationManager = (NotificationManager)my_ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel(mNotificationManager);

        if (Error != null) {

            Log.i("WS Calling Error", Error);

        } else {

            // Show Response Json On Screen (activity)

            Log.i("WS Calling Successful", Content);


            /****************** Start Parse Response JSON Data *************/

            String OutputData = "";
            JSONObject jsonResponse;

            try {

                /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                jsonResponse = new JSONObject(Content);

                /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                /*******  Returns null otherwise.  *******/
                JSONArray jsonMainNode = jsonResponse.optJSONArray("data");

                /*********** Process each JSON Node ************/

                int lengthJsonArr = jsonMainNode.length();

                if (lengthJsonArr==0)
                {
                    Log.d("DbServCheckMsg", "No Messages Available" );
                }
                else
                {
                    Log.d("DbServCheckMsg", lengthJsonArr+ ": Messages Available" );
                }
                for(int i=0; i < lengthJsonArr; i++)
                {
                    /****** Get Object for each JSON node.***********/
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    /******* Fetch node values **********/
                    String msg_text = jsonChildNode.optString("msg_text");
                    NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(my_ctx,CHANNEL_ID);
                    Intent intent1 = new Intent(my_ctx, AppHome.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(my_ctx, this.notificationCount, intent1, 0);
                    notiBuilder.setContentTitle("DealsLocal");
                    notiBuilder.setSmallIcon(R.drawable.deal_local_noti_icon );
                    notiBuilder.setContentText(msg_text);
                    notiBuilder.setContentIntent(pendingIntent);
                    this.notification = notiBuilder.build();
                    this.mNotificationManager.notify(this.notificationCount, this.notification);
                    this.notificationCount++ ;
                }
                /****************** End Parse Response JSON Data *************/

                //Show Parsed Output on screen (activity)
                //  jsonParsed.setText( OutputData );
                Log.i("JSON Data", OutputData);
                //  ListExamData() ;
                setupRecyclerView(rv,myBizEstablishmentList );
            } catch (JSONException e) {

                e.printStackTrace();
            }


        }
    }

    private void createNotificationChannel(NotificationManager notificationManager) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name =  "Channel1" ;      //getString(R.string.channel_name);
            String description = "Description" ;   //getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            // NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView, ArrayList<BizEstablishment> al_be1) {
//        ArrayList<BizEstablishment> al_be1 = dbh.search_biz_establishments(qry_str);
        Log.d("Num Establishments", al_be1.size() + "");
        if (al_be1.size() == 0) {
            Toast.makeText(my_ctx, "No business listed by you", Toast.LENGTH_LONG).show();
        }
        else {
            recyclerView.setAdapter(new EstablishmentRecyclerViewAdapter(al_be1, a1,1));
//            recyclerView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getActivity(), "Set On Click", Toast.LENGTH_LONG).show();
//                }
//            });
        }
    }


}
