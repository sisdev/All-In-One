package in.sisoft.all_in_one;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Calendar;
import java.util.Random;

import in.sisoft.all_in_one.DbSvr.DbSync;
import in.sisoft.all_in_one.pojo.AppConstants;

public class AppSplash extends Activity {

	public static String Sync_Status_File="SyncStatusFile";
	public static String Sync_Status = "SyncStatus";
	SharedPreferences sp ;
	int syncStatus = 0 ;
	int splash_duration = 3000 ;
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		sp = getSharedPreferences(Sync_Status_File,Context.MODE_PRIVATE);
		syncStatus = sp.getInt(Sync_Status,0);
		if (syncStatus==0) {

			ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
			if (activeNetwork != null) { // connected to the internet
				DbSync.databaseSync(getApplicationContext());
				splash_duration = 9000;
				SharedPreferences.Editor spe = sp.edit();
				spe.putInt(Sync_Status,1);
				spe.commit();
			}
			else
			{
				Toast.makeText(this,"Please connect with Internet and restart App",Toast.LENGTH_LONG).show();
				finish();

			}

			setAlarm();

		}

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

					startActivity(new Intent(AppSplash.this, AppHome.class));
					finish();

			}
		}, splash_duration);

// Initialize Mobile Ads SDK

		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
			}
		});


	}

	public void setAlarm(){
		AlarmManager alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, ReceiverMessage.class), 0);
		Calendar cal_today = Calendar.getInstance();
		Calendar cal_alarm = Calendar.getInstance();
		cal_alarm.set(Calendar.HOUR_OF_DAY, AppConstants.hourOfDay);
		Random rand = new Random() ;
		int time_min = rand.nextInt(60); // Return any number below 60 i.e 0-59
		cal_alarm.set(Calendar.MINUTE, time_min);
		cal_alarm.set(Calendar.SECOND, 0);
		alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), 86400000L, pendingIntent);
		Log.d("AppHome", "Alarm Set for Check Messages:"+cal_alarm.get(Calendar.HOUR_OF_DAY)+":"+cal_alarm.get(Calendar.MINUTE));
	}

}
