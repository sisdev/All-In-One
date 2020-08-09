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

public class DbServMyBizAddAsycTask extends AsyncTask<String,String, String> {

    @Override
    protected String doInBackground(String[] params) {
        BufferedReader bufferedReader = null;

        try {


//            String str_url = "https://docs.google.com/forms/u/1/d/e/1FAIpQLSf7vLfLGPh1gRO2SP13jHFc4cJF0JeerMi28_V-Bmcjx4BASw/formResponse";
              String str_url = AppConstants.bizAddURL;

//            u3.execute(strPrimaryPhone,strBizName,strBizDetails,strAddress1,strAddress2, strAddress3, strEmail, strWebSite, strAlternatePhone);


            URL u1 = new URL(str_url);

            String urlParameters =
                    "cphone1=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
                            "cname=" + URLEncoder.encode(params[1], "UTF-8")+ "&" +
                            "aboutus=" + URLEncoder.encode(params[2], "UTF-8")+ "&" +
                            "caddress1=" + URLEncoder.encode(params[3], "UTF-8")+ "&" +
                            "ckhand=" + URLEncoder.encode(params[4], "UTF-8")+ "&" +
                            "cmkt=" + URLEncoder.encode(params[5], "UTF-8")+ "&" +
                            "cemail=" + URLEncoder.encode(params[6], "UTF-8")+ "&" +
                            "weburl=" + URLEncoder.encode(params[7], "UTF-8")+ "&" +
                            "cphone2=" + URLEncoder.encode(params[8], "UTF-8")+"&" +
                            "cat_id=" + URLEncoder.encode(params[9], "UTF-8")+"&" +
                            "glogin=" + URLEncoder.encode(params[10], "UTF-8");


            Log.d("DoInBackGround", urlParameters);

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


