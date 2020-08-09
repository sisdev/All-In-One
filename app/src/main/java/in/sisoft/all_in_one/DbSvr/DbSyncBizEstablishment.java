package in.sisoft.all_in_one.DbSvr;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import in.sisoft.all_in_one.pojo.BizEstablishment;

/**
 * Created by vijay on 03-Dec-17.
 */
// http://androidexample.com/Restful_Webservice_Call_And_Get_And_Parse_JSON_Data-_Android_Example/index.php?view=article_discription&aid=101&aaid=123


// Class with extends AsyncTask class

public class DbSyncBizEstablishment extends AsyncTask<String, Void, Void> {

    // Required initialization

    DbHelper dbHelper ;
    public DbSyncBizEstablishment(Context ctx)
    {
        my_ctx = ctx ;
        dbHelper = new DbHelper(ctx);
    }
    private String Content;
    private String Error = null;
    Context my_ctx  ;

//    private ProgressDialog Dialog = new ProgressDialog(BusinessEstablishmentActivity.this);
    String data ="";
    //    TextView uiUpdate = (TextView) findViewById(R.id.output);
//    TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
    int sizeData = 0;
//    EditText serverText = (EditText) findViewById(R.id.serverText);


    protected void onPreExecute() {
        // NOTE: You can call UI Element here.

        //Start Progress Dialog (Message)

  //      Dialog.setMessage("Please wait..");
  //      Dialog.show();

        try{
            // Set Request parameter
            data +="&" + URLEncoder.encode("data", "UTF-8") ;

//            data +="&" + URLEncoder.encode("data", "UTF-8") + "="+serverText.getText();

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
            URL url = new URL(urls[0]);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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

                Log.d("DbSyncBizEstablishment",lengthJsonArr+" Records Available");
                if (lengthJsonArr==0)
                {
                    Toast.makeText(my_ctx, "No Records Available for download & sync" , Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(my_ctx, lengthJsonArr+ ": Records Available for download & sync" , Toast.LENGTH_LONG).show();
                }
                for(int i=0; i < lengthJsonArr; i++)
                {
                    /****** Get Object for each JSON node.***********/
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    BizEstablishment b1 = new BizEstablishment();
                    /******* Fetch node values **********/
                    b1.biz_id = Integer.parseInt(jsonChildNode.optString("biz_id"));
                    b1.biz_name       = jsonChildNode.optString("biz_name");
                    b1.biz_street     = jsonChildNode.optString("biz_street");
                    b1.biz_city     = jsonChildNode.optString("biz_city");
                    b1.biz_district = jsonChildNode.optString("biz_district");
                    b1.biz_state = jsonChildNode.optString("biz_state");
                    b1.biz_pin       = jsonChildNode.optString("biz_pin");
                    b1.biz_phone1     = jsonChildNode.optString("biz_phone1");
                    b1.biz_phone2     = jsonChildNode.optString("biz_phone2");
                    b1.biz_country = jsonChildNode.optString("biz_country");
                    b1.biz_details     = jsonChildNode.optString("biz_details");
                    b1.biz_logo_image_loc     = jsonChildNode.optString("biz_logo_image_loc");
                    b1.bcat_id = Integer.parseInt(jsonChildNode.optString("bcat_id"));
                    b1.disp_status = jsonChildNode.optString("disp_status");


                    OutputData += " Id           : "+ b1.biz_id +" "
                            + " Business Name           : "+ b1.biz_name +" "
                            + "Business Street      : "+ b1.biz_street +" "
                            + "Biz city      : "+ b1.biz_city +" "
                            + "Biz Category                : "+ b1.bcat_id +" "
                            +"--------------------------------------------------";

                    dbHelper.insert_update_biz_establishment(b1);
                    //           dbhelper.insertqp(Integer.parseInt(id),  course_name, file_name, file_loc, date_added);

                }
                /****************** End Parse Response JSON Data *************/

                //Show Parsed Output on screen (activity)
                //  jsonParsed.setText( OutputData );
                Log.i("JSON Data", OutputData);
                //  ListExamData() ;

            } catch (JSONException e) {

                e.printStackTrace();
            }


        }
    }

}
