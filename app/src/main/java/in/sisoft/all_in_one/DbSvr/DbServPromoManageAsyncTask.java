package in.sisoft.all_in_one.DbSvr;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import in.sisoft.all_in_one.pojo.AppConstants;

public class DbServPromoManageAsyncTask extends AsyncTask<String,String, String> {

    @Override
    protected String doInBackground(String[] params) {
        BufferedReader bufferedReader = null;

        try {
            String urlParameters= "";

              String str_url = AppConstants.bizManagePromo;
              URL u1 = new URL(str_url);

              if (params[0].equals("add")) {
                  urlParameters =
                          "action=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
                                  "biz_id=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                                  "promo_code=" + URLEncoder.encode(params[2], "UTF-8") + "&" +
                                  "promo_text1=" + URLEncoder.encode(params[3], "UTF-8") + "&" +
                                  "promo_text2=" + URLEncoder.encode(params[4], "UTF-8") + "&" +
                                  "promo_start_date=" + URLEncoder.encode(params[5], "UTF-8") + "&" +
                                  "promo_end_date=" + URLEncoder.encode(params[6], "UTF-8") + "&" +
                                  "created_by=" + URLEncoder.encode(params[7], "UTF-8");
              }
            if (params[0].equals("delete")) {
                urlParameters =
                        "action=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
                                "promo_id=" + URLEncoder.encode(params[1], "UTF-8")+ "&" +
                                "user_id=" + URLEncoder.encode(params[2], "UTF-8")       ;
            }

            if (params[0].equals("update`")) {
                urlParameters =
                        "action=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
                                "promo_id=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                                "promo_code=" + URLEncoder.encode(params[2], "UTF-8") + "&" +
                                "promo_text1=" + URLEncoder.encode(params[3], "UTF-8") + "&" +
                                "promo_text2=" + URLEncoder.encode(params[4], "UTF-8") + "&" +
                                "promo_start_date=" + URLEncoder.encode(params[5], "UTF-8") + "&" +
                                "promo_end_date=" + URLEncoder.encode(params[6], "UTF-8") + "&" +
                                "created_by=" + URLEncoder.encode(params[7], "UTF-8");
            }

                Log.d("DbServManagePromot:","DoInBackGround:URL Params"+ urlParameters);

            HttpURLConnection httpURLConnection = (HttpURLConnection) u1.openConnection();

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

            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
            String response = "";
            String line = "";

            // Read Server Response

            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            Log.d("doInBackground", "Response:" + response);


            return response;

        } catch (Exception e1) {

            Log.d("doInBackground", "Response:" + e1.getMessage());

        }
        return "11111";
    }
}


