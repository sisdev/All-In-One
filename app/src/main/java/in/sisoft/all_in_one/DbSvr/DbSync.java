package in.sisoft.all_in_one.DbSvr;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import in.sisoft.all_in_one.DbSvr.DbSyncBizCategory;
import in.sisoft.all_in_one.DbSvr.DbSyncBizEstablishment;
import in.sisoft.all_in_one.pojo.AppConstants;

/**
 * Created by vijay on 14-Jan-18.
 */
public class DbSync {

    public static void databaseSync(Context ctx) {
        ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            // WebServer Request URL
            String catURL = AppConstants.catURL;
            Log.d("Biz Category URL", catURL);
            // Use AsyncTask execute Method To Prevent ANR Problem
            new DbSyncBizCategory(ctx).execute(catURL);

            // WebServer Request URL
            String estabURL = AppConstants.estabURL;
            Log.d("Biz Estab URL", estabURL);

            // Use AsyncTask execute Method To Prevent ANR Problem
            new DbSyncBizEstablishment(ctx).execute(estabURL);

        }

    }
}
