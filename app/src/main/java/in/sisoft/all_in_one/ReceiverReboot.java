package in.sisoft.all_in_one;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Random;

import in.sisoft.all_in_one.pojo.AppConstants;

public class ReceiverReboot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, ReceiverMessage.class), 0);
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
