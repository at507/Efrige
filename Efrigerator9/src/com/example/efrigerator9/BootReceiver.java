package com.example.efrigerator9;


import java.util.Calendar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
  private static final String TAG = BootReceiver.class.getSimpleName();
  NotificationManager mNotificationManager;
  
  @Override
  public void onReceive(Context context, Intent intent) { 
	  final String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };		
		
		 Calendar cal=Calendar.getInstance();		
		 cal.set(2012, 7, 21);
		 String dater_s = cal.get(Calendar.DATE)+"-"+months[cal.get(Calendar.MONTH)]+"-"+cal.get(Calendar.YEAR);
		 displayNotification(dater_s, 10,context);	 
  }

  void displayNotification(String dater_s1, int id1, Context context) {
  	Log.d(TAG, "Notification"+dater_s1);
  	mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
  	Notification notification = new Notification(R.drawable.switch_thumb, "hello", System.currentTimeMillis()); 
  	Intent notificationIntent = new Intent(context, DisplayActivityList.class);
    notificationIntent.putExtra("date",dater_s1);
    PendingIntent contentIntent = PendingIntent.getActivity(context,id1 , notificationIntent, PendingIntent.FLAG_ONE_SHOT);
    notification.flags = Notification.FLAG_AUTO_CANCEL;
    notification.setLatestEventInfo(context, "Efridge Warning", dater_s1, contentIntent);
    mNotificationManager.notify(id1, notification);
  } 
}
