package com.example.efrigerator9;



import android.app.Application;

public class EfridgeApplication extends Application{

	
	FridgeData fridgeData;
	private boolean isServiceRunning = false;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		fridgeData= new FridgeData(this);
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		fridgeData.close();
	}
	
	 public boolean isServiceRunning() {
		    return isServiceRunning;
		  }

    public void setServiceRunning(boolean isServiceRunning) {
		    this.isServiceRunning = isServiceRunning;
		  }

}
