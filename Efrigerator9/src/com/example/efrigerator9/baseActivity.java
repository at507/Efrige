// <title> this is base class </title>
// <author> aat </author>

// <revision>
// 08-05-2012 : aat
// added formatting to file
// Issue #2: 20120810 at
// DS databasehelper activity creation
// Except deletion, added every functionality mentioned in DS
//</revision>
package com.example.efrigerator9;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;



import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

public class baseActivity extends SherlockFragmentActivity implements ActionBar.TabListener{
	
//	protected ActionBar actionBar;
	EfridgeApplication efridge; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
        for (int i = 1; i <= 3; i++) {
            ActionBar.Tab tab = getSupportActionBar().newTab();
            tab.setText("Tab " + i);
            tab.setTabListener(this);
            getSupportActionBar().addTab(tab);
        }
	
		efridge = (EfridgeApplication)getApplication();
		
		//startService(new Intent(this,NotificationService.class));
	}
	@Override
	public void onTabSelected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		Toast.makeText(this, tab.getText(), Toast.LENGTH_LONG).show();
		 final String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };		
		 DateFormat formatter ; 
		 Date date = null ; 
		 formatter = new SimpleDateFormat("dd-MMM-yy");
		 Calendar cal=Calendar.getInstance();
		 for (int i1= 1; i1< 3;i1++)
		 {
			 cal.set(2012, i1, 21);
		     String dater_s = cal.get(Calendar.DATE)+"-"+months[cal.get(Calendar.MONTH)]+"-"+cal.get(Calendar.YEAR);
			 Intent intent1= new Intent(this,NotificationService.class );
			 intent1.putExtra("dater_s", dater_s);
			 intent1.putExtra("id", i1);
			 if(tab.getText().equals("Tab 2"))
			{
			  startService(intent1);
			} 
		 }
		 
		if(tab.getText().equals("Tab 3"))
		{
		  stopService(new Intent(this,NotificationService.class));
		}
		
	}
	@Override
	public void onTabUnselected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabReselected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	

	
}
