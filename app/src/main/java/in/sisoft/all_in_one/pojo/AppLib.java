package in.sisoft.all_in_one.pojo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import in.sisoft.all_in_one.BusinessEstablishmentActivity;
import in.sisoft.all_in_one.DbSvr.DbSyncBizEstablishment;

public class AppLib {

    // Not used -- 02-Aug-20
    public static boolean confirmDialog(Activity a1, String msg)
    {   boolean ret_val = false ;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(a1);
        alertBuilder.setTitle("Deals Local");
        alertBuilder.setMessage(msg);
       alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            } } );
        alertBuilder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            } });

        AlertDialog ad = alertBuilder.create();
        ad.show() ;
       return true ;
    }
}
