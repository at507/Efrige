package com.example.efrigerator9;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;


import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.View;

public class baseActivity extends SherlockFragmentActivity implements TabListener{
	
	protected ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		actionBar = getSupportActionBar();
		//		getActionBar();
	actionBar.setDisplayShowHomeEnabled(false);
	actionBar.setDisplayShowTitleEnabled(false);
	//actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
		
	    
		
	}

	
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
		
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 getSupportMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
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
