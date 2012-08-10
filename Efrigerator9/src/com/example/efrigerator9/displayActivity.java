// <title> This is display activity. This activity is followed by calendar activity</title>

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.efrigerator9.MySwitch.OnChangeAttemptListener;


import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class displayActivity extends baseActivity implements OnClickListener, OnChangeAttemptListener, OnCheckedChangeListener
{
	private TextView title,previous_day, next_day;
	private String dater;
	private GridView ItemView;
	private ArrayList<String> Cold_itemsadded = new ArrayList<String>();
	private ArrayList<String> Normal_itemsadded = new ArrayList<String>();
	private ViewSwitcher switcher;
	private boolean Is_frozen=true;
	MySwitch slideToUnLock;
	 Cursor cursor,cursor_next,cursor_prev;
	DateFormat formatter_db=new SimpleDateFormat("dd-MMM-yy");
	 Date date_db;
	 addItemAdapter frozenAdapter=  new addItemAdapter(displayActivity.this, Cold_itemsadded); ;
	 addItemAdapter normalAdapter =  new addItemAdapter(displayActivity.this, Normal_itemsadded); ;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		Intent intent= getIntent();
		
		
		switcher = (ViewSwitcher) findViewById(R.id.profileSwitcher);
		slideToUnLock = (MySwitch)findViewById(R.id.switch1);
		slideToUnLock.setOnCheckedChangeListener(this);
	    dater= intent.getStringExtra("date");
		 
	  //  formatter_db = new SimpleDateFormat("dd-MMM-yy");
		 
	    cursor = fridgeData.query();
	    startManagingCursor(cursor);

	    // Setup Adapter
	   // String[] fromdb_ToDisplay = {  fridgeData.C_ITEM };
	   // int[] to = { R.id.textUser, R.id.textText, R.id.textCreatedAt };
	    if (cursor.getCount()>0)
	    {
	    	while (cursor.moveToNext())
	    	
	    	{
	    		if(cursor.getString(1).equals(dater))
	    		{
	    			String test = cursor.getString(2);
	    			String cat = cursor.getString(3);
		    		if(cat.equals("Frozen"))
		    		{
		    			Cold_itemsadded.add(test);
		    			ItemView = (GridView) findViewById(R.id.items_cold);	
		    			ItemView.setAdapter(new addItemAdapter(displayActivity.this, Cold_itemsadded));
		    		}
		    		else
		    		{
		    			Normal_itemsadded.add(test);
						ItemView = (GridView) findViewById(R.id.items_normal);	
						ItemView.setAdapter(new addItemAdapter(displayActivity.this, Normal_itemsadded));
					
									 
		    		}
	    		}
	    	}
	    }
		title = (TextView) findViewById(R.id.title);
		title.setText(dater);
		Button addItem_cold_Button = (Button)findViewById(R.id.addItem_cold);
		Button addItem_normal_Button = (Button)findViewById(R.id.addItem_normal);
		addItem_cold_Button.setOnClickListener(this);
		addItem_normal_Button.setOnClickListener(this);
		
		previous_day = (TextView) findViewById(R.id.previous_day);
		next_day = (TextView) findViewById(R.id.next_day);
		previous_day.setOnClickListener(this);
		next_day.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		 final String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };		
		 DateFormat formatter ; 
		 Date date = null ; 
		 formatter = new SimpleDateFormat("dd-MMM-yy");
		 Calendar cal=Calendar.getInstance();
		  
		 super.onClick(v);
		 switch (v.getId()) {
		
		 case R.id.addItem_cold :
			final EditText input = new EditText(this); // This could also come from an xml resource, in which case you would use findViewById() to access the input
			AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
			float vol = (float) 0.5; //This will be half of the default system sound
			am.playSoundEffect(AudioManager.FX_KEY_CLICK, vol);
			Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); 
			vb.vibrate(40);
			input.setBackgroundResource(R.drawable.buttonaction);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setView(input);
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int whichButton) 
				{
					String value = input.getText().toString();
		    
				
					if(!value.isEmpty())
					{
						
					//	if (!Is_frozen)
						{
							Cold_itemsadded.add(value);
							ItemView = (GridView) findViewById(R.id.items_cold);	
							ItemView.setAdapter(new addItemAdapter(displayActivity.this, Cold_itemsadded));
							
						}
					/*	else
						{
							Normal_itemsadded.add(value);
							ItemView = (GridView) findViewById(R.id.items_normal);	
							ItemView.setAdapter(new addItemAdapter(displayActivity.this, Normal_itemsadded));
						}*/
									 
					
					}
		        
					
		            fridgeData.insert(100, dater, value, "Frozen");
					dialog.dismiss();
				}
			});
			
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
			{
				  public void onClick(DialogInterface dialog, int whichButton) 
				  {
				    // Canceled.
					  dialog.dismiss();
				  }
			});
			builder.show();
			break;
		
		 case R.id.addItem_normal :
			final EditText input_n = new EditText(this); // This could also come from an xml resource, in which case you would use findViewById() to access the input
			AudioManager am_n = (AudioManager)getSystemService(AUDIO_SERVICE);
			float vol_n = (float) 0.5; //This will be half of the default system sound
			am_n.playSoundEffect(AudioManager.FX_KEY_CLICK, vol_n);
			Vibrator vb_n = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); 
			vb_n.vibrate(40);
			input_n.setBackgroundResource(R.drawable.buttonaction);
			AlertDialog.Builder builder_n = new AlertDialog.Builder(this);
			builder_n.setView(input_n);
			builder_n.setPositiveButton("OK", new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int whichButton) 
				{
					String value = input_n.getText().toString();
		    
				
					if(!value.isEmpty())
					{
						
						
							Normal_itemsadded.add(value);
							ItemView = (GridView) findViewById(R.id.items_normal);	
							ItemView.setAdapter(new addItemAdapter(displayActivity.this, Normal_itemsadded));
						
										 
						
					}
			        
					
					
		            fridgeData.insert(100, dater, value, "Normal");
		            
					dialog.dismiss();
				}
			});
			
			builder_n.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
			{
				  public void onClick(DialogInterface dialog, int whichButton) 
				  {
				    // Canceled.
					  dialog.dismiss();
				  }
			});
			builder_n.show();
			break;
		
		case R.id.previous_day:
			 formatter = new SimpleDateFormat("dd-MMM-yy");
			 try 
			 {
				 date = (Date)formatter.parse(dater);
			 } catch (ParseException e) 
			 {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } 
			
			 cal.setTime(date);
			 cal.add(Calendar.DATE, -1);
			 dater = cal.get(Calendar.DATE)+"-"+months[cal.get(Calendar.MONTH)]+"-"+cal.get(Calendar.YEAR);
			 title.setText(dater);
			 Cold_itemsadded.clear();
			 Normal_itemsadded.clear();
			 cursor_prev = fridgeData.query();
			 startManagingCursor(cursor_prev);
			 			
			 cursor_prev.moveToFirst();
			 if (cursor_prev.getCount()>0)
			    {
			    	do 
			    	
			    	{
			    		if(cursor_prev.getString(1).equals(dater))
			    		{
			    			String test_prev = cursor_prev.getString(2);
			    			String cat = cursor_prev.getString(3);
				    		if(cat.equals("Frozen"))
				    		{
				    			Cold_itemsadded.add(test_prev);
				    			//ItemView = (GridView) findViewById(R.id.items_cold);	
				    			//ItemView.setAdapter(new addItemAdapter(displayActivity.this, Cold_itemsadded));
				    		}
				    		else
				    		{
				    			Normal_itemsadded.add(test_prev);
				    			//	ItemView = (GridView) findViewById(R.id.items_normal);	
				    			//	ItemView.setAdapter(new addItemAdapter(displayActivity.this, Normal_itemsadded));
							
											 
				    		}
			    		}
			    	}while(cursor_prev.moveToNext());
			    }
			 if(Is_frozen)
			 {
			 	ItemView = (GridView) findViewById(R.id.items_cold);
			    ItemView.setAdapter(this.frozenAdapter);
			    this.frozenAdapter.notifyDataSetChanged();
			    
			 }
			 else
			 {
			 	ItemView = (GridView) findViewById(R.id.items_normal);	
			    ItemView.setAdapter(this.normalAdapter);
			    this.normalAdapter.notifyDataSetChanged();
			 }
			 
			 break;
			
		case R.id.next_day:
			 formatter = new SimpleDateFormat("dd-MMM-yy");
			 try 
			 {
				  	date = (Date)formatter.parse(dater);
			 } catch (ParseException e) 
			 {
				  // TODO Auto-generated catch block
			  		e.printStackTrace();
			 } 
			 cal.setTime(date);
			 cal.add(Calendar.DATE, 1);
			 dater = cal.get(Calendar.DATE)+"-"+months[cal.get(Calendar.MONTH)]+"-"+cal.get(Calendar.YEAR);
			 title.setText(dater);
			 
			 //frozenAdapter = new addItemAdapter(displayActivity.this, Cold_itemsadded);
			 //normalAdapter =  new addItemAdapter(displayActivity.this, Normal_itemsadded);
			 Cold_itemsadded.clear();
			 Normal_itemsadded.clear();
			 
			
			 cursor_next = fridgeData.query();
			    startManagingCursor(cursor_next);
			 cursor_next.moveToFirst();
			 if (cursor_next.getCount()>0)
			    {
			    	do 
			    	
			    	{
			    		if(cursor_next.getString(1).equals(dater))
			    		{
			    			String test_next = cursor_next.getString(2);
			    			String cat = cursor_next.getString(3);
				    		if(cat.equals("Frozen"))
				    		{
				    			Cold_itemsadded.add(test_next);
				    			
				    			
				    		}
				    		else
				    		{
				    			Normal_itemsadded.add(test_next);
								
											 
				    		}
			    		}
			    	}while (cursor_next.moveToNext());
			    }
			 if(Is_frozen)
			 {
			 	ItemView = (GridView) findViewById(R.id.items_cold);
			    ItemView.setAdapter(this.frozenAdapter);
			    this.frozenAdapter.notifyDataSetChanged();
			    
			 }
			 else
			 {
			 	ItemView = (GridView) findViewById(R.id.items_normal);	
			    ItemView.setAdapter(this.normalAdapter);
			    this.normalAdapter.notifyDataSetChanged();
			 }
			 
			 break;
			
		}
	
	}
	
	public class addItemAdapter extends BaseAdapter 
	{
		private Context context;
		private final ArrayList<String> Item; 
	 
		public addItemAdapter(Context context, ArrayList<String> itemsadded) 
		{
			this.context = context;
			this.Item = itemsadded;
		}
	 
		public View getView(int position, View convertView, ViewGroup parent) 
		{
	 
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View gridView;
	 
			if (convertView == null) 
			{
				gridView = new View(context);	
				gridView = inflater.inflate(R.layout.addeditem, null);
				Button eachItem = (Button) gridView.findViewById(R.id.added);
				eachItem.setText(Item.get(position));
			} 
			else 
			{
				gridView = (View) convertView;
			}
			
			return gridView;
		}
	 
		@Override
		public int getCount()
		{
			return Item.size();
		}
	 
		@Override
		public Object getItem(int position)
		{
			return null;
		}
	 
		@Override
		public long getItemId(int position)
		{
			return 0;
		}
	 
	}
  
	@Override
	public void onChangeAttempted(boolean isChecked)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) 
	{
		// TODO Auto-generated method stub
			
		if(arg1)
		{		
			Is_frozen=false;
			switcher.showPrevious();
			 Cold_itemsadded.clear();
			 Normal_itemsadded.clear();
			 
			
			 cursor_next = fridgeData.query();
			    startManagingCursor(cursor_next);
			 cursor_next.moveToFirst();
			 if (cursor_next.getCount()>0)
			    {
			    	do 
			    	
			    	{
			    		if(cursor_next.getString(1).equals(dater))
			    		{
			    			String test_next = cursor_next.getString(2);
			    			String cat = cursor_next.getString(3);
				    		if(!(cat.equals("Frozen")))
				    		//{
				    			//Cold_itemsadded.add(test_next);
				    			
				    			
				    	//	}
				    		//else
				    		{
				    			Normal_itemsadded.add(test_next);
								
											 
				    		}
			    		}
			    	}while (cursor_next.moveToNext());
			    }
			
			 {
			 	ItemView = (GridView) findViewById(R.id.items_normal);	
			    ItemView.setAdapter(this.normalAdapter);
			    this.normalAdapter.notifyDataSetChanged();
			 }
			
			
		}
		else
		{	
			Is_frozen=true;
			switcher.showNext();
			
			Cold_itemsadded.clear();
			 Normal_itemsadded.clear();
			 cursor_prev = fridgeData.query();
			 startManagingCursor(cursor_prev);
			 			
			 cursor_prev.moveToFirst();
			 if (cursor_prev.getCount()>0)
			    {
			    	do 
			    	
			    	{
			    		if(cursor_prev.getString(1).equals(dater))
			    		{
			    			String test_prev = cursor_prev.getString(2);
			    			String cat = cursor_prev.getString(3);
				    		if(cat.equals("Frozen"))
				    		{
				    			Cold_itemsadded.add(test_prev);
				    			//ItemView = (GridView) findViewById(R.id.items_cold);	
				    			//ItemView.setAdapter(new addItemAdapter(displayActivity.this, Cold_itemsadded));
				    		}
				    		
			    		}
			    	}while(cursor_prev.moveToNext());
			    }
			// if(Is_frozen)
			 {
			 	ItemView = (GridView) findViewById(R.id.items_cold);
			    ItemView.setAdapter(this.frozenAdapter);
			    this.frozenAdapter.notifyDataSetChanged();
			    
			 }
			 
			
			
		}
	
	
	}

}
