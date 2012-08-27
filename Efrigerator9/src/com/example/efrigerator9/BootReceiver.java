package com.example.efrigerator9;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
   /* SharedPreferences prefs = PreferenceManager
        .getDefaultSharedPreferences(context);
    
    boolean startAtBoot = prefs.getBoolean("startAtBoot", false);
    if (startAtBoot) {
      context.startService(new Intent(context, UpdaterService.class));
    }
    Log.d("BootReceiver", "onReceived prefs.startAtBoot: " + startAtBoot);*/
	  
	  
	 /* int minutes = 1;
	    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	    Intent i = new Intent(context, NotificationService.class);
	    PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
	    am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + minutes*6000, pi);// triggerAtMillis, operation)(pi);
	   */
	   
	   // by my own convention, minutes <= 0 m notifications are disabled
	   // if (minutes > 0) {
	   //     am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
	   //         SystemClock.elapsedRealtime() + minutes*60*1000,
	   //         minutes*60*1000, pi);
	   // }
  }

}
