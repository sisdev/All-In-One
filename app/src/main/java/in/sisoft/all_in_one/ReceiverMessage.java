package in.sisoft.all_in_one;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.sisoft.all_in_one.DbSvr.DbServCheckMsgAyncTask;
import in.sisoft.all_in_one.DbSvr.DbServLoginNotifyAyncTask;
import in.sisoft.all_in_one.DbSvr.DbSync;

public class ReceiverMessage extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet

            // Check Messages
            Calendar cal = Calendar.getInstance();
            Date dt1 = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String strDt = sdf.format(dt1);
            DbServCheckMsgAyncTask chkMsg = new DbServCheckMsgAyncTask(context);
            chkMsg.execute(strDt);
            Log.d("ReceiverMessage","Check Messages Called for :"+strDt );

        // Sync Database
            DbSync.databaseSync(context);
        }


    }
}
