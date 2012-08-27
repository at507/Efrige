package com.example.efrigerator9;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import android.util.Log;

public class NotificationService extends Service {
	private static final String TAG = NotificationService.class.getSimpleName();
	private String dater_s;
	NotificationManager mNotificationManager;
	/**
     * Simply return null, since our Service will not be communicating with
     * any other components. It just does its work silently.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    @Override
    public void onCreate() {
      super.onCreate();
      Log.d(TAG, "onCreate'd");
    }

    @Override
    public  void onStart(Intent intent, int startId) {
      super.onStart(intent, startId);
      Log.d(TAG, "onStart'd");
      int id;
      dater_s= intent.getStringExtra("dater_s");
      id= intent.getIntExtra("id", 0);
      displayNotification(dater_s,id);
    }

    @Override
    public  void onDestroy() {
      super.onDestroy();
      Log.d(TAG, "onDestroy'd");
    }
    
    void displayNotification(String dater_s1, int id1) {
    	Log.d(TAG, "Notification"+dater_s1);
    	mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	Notification notification = new Notification( R.drawable.switch_thumb, "hello", System.currentTimeMillis()); 
    	Intent notificationIntent = new Intent(this, DisplayActivityList.class);
        notificationIntent.putExtra("date",dater_s);
        PendingIntent contentIntent = PendingIntent.getActivity(this,id1 , notificationIntent, PendingIntent.FLAG_ONE_SHOT);
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.setLatestEventInfo(getApplicationContext(), "Efridge Warning", dater_s1, contentIntent);
        mNotificationManager.notify(id1, notification);
    } 
   
}