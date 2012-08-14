// <title> This is calendar activity. The first activity of the application </title>

// <author> aat </author>

// <revision>
// 08-05-2012 : aat
// added formatting to file

package com.example.efrigerator9;

import java.util.Calendar;
import java.util.Locale;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.example.efrigerator9.SimpleGestureFilter.SimpleGestureListener;




import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class CalendarActivity extends baseActivity implements OnClickListener,SimpleGestureListener 
{

		private static final String tag = "Main";
		private int currentMonth;
		private TextView prevMonth;
		private TextView nextMonth;
		private GridView daysofweekView;
		private GridCellAdapter adapter;
		private Calendar _calendar;
		private int month, year;
		private GridView calendarView;
		private int width,height;
		private SimpleGestureFilter detector; 

		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_calendar);
				final String[] daysofweekIn = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
				daysofweekView = (GridView) findViewById(R.id.daysofweek);
				daysofweekView.setAdapter(new weekdayAdapter(this, daysofweekIn));
				_calendar = Calendar.getInstance(Locale.getDefault());
				
				month = _calendar.get(Calendar.MONTH);
				year = _calendar.get(Calendar.YEAR);
				currentMonth= month;
				
				//setting tile as current month
				TextView title  = (TextView) findViewById(R.id.title);
				title.setText(android.text.format.DateFormat.format("MMMM yyyy", _calendar));
				
				// next month and previous month
				prevMonth = (TextView) findViewById(R.id.previous);
				prevMonth.setOnClickListener(this); 
				nextMonth = (TextView) findViewById(R.id.next);
				nextMonth.setOnClickListener(this);
				
				calendarView = (GridView) this.findViewById(R.id.calendar);	
				adapter = new GridCellAdapter(getApplicationContext(), R.id.day_gridcell, month, year);
				adapter.notifyDataSetChanged();
				calendarView.setAdapter(adapter);
				detector = new SimpleGestureFilter(this,this);
		}

		@Override
		public void onClick(View v)
		{
			if (v == prevMonth)
			{
					if (month <= 1)
					{
						month = 11;
						year--;
					}
					else
					{
						month--;
					}

					adapter = new GridCellAdapter(getApplicationContext(), R.id.day_gridcell, month, year);
					_calendar.set(year, month, _calendar.get(Calendar.DAY_OF_MONTH));
					TextView title  = (TextView) findViewById(R.id.title);
					title.setText(android.text.format.DateFormat.format("MMMM yyyy", _calendar));

					adapter.notifyDataSetChanged();
					calendarView.setAdapter(adapter);
				}
				
				if (v == nextMonth)
				{
					if (month >= 11)
					{
						month = 0;
						year++;
					} 
					else
					{
						month++;
					}

					adapter = new GridCellAdapter(getApplicationContext(), R.id.day_gridcell, month, year);
					_calendar.set(year, month, _calendar.get(Calendar.DAY_OF_MONTH));
					TextView title  = (TextView) findViewById(R.id.title);
					title.setText(android.text.format.DateFormat.format("MMMM yyyy", _calendar));
					adapter.notifyDataSetChanged();
					calendarView.setAdapter(adapter);
				}
			}

			// Inner Class
			public class GridCellAdapter extends BaseAdapter implements OnClickListener
			{
				private static final String tag = "GridCellAdapter";
				private final Context _context;
				private final List<String> list;
				private final String[] weekdays = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
				private final String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
				private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
				private final int month, year;
				private int daysInMonth, prevMonthDays;
				private final int currentDayOfMonth;
				private Button gridcell;
			
				// Days in Current Month
				public GridCellAdapter(Context context, int textViewResourceId, int month, int year)
				{
					super();
					this._context = context;
					this.list = new ArrayList<String>();
					this.month = month;
					this.year = year;

					Log.d(tag, "Month: " + month + " " + "Year: " + year);
					Calendar calendar = Calendar.getInstance();
					currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

					printMonth(month, year);
				}

				public String getItem(int position)
				{
					return list.get(position);
				}

				@Override
				public int getCount()
				{
					return list.size();
				}

				private void printMonth(int mm, int yy)
				{
					// The number of days to leave blank at
					// the start of this month.
					int trailingSpaces = 0;
					int leadSpaces = 0;
					int daysInPrevMonth = 0;
					int prevMonth = 0;
					int prevYear = 0;
					int nextMonth = 0;
					int nextYear = 0;
					
					GregorianCalendar cal = new GregorianCalendar(yy, mm, 1);

					// Days in Current Month
					daysInMonth = daysOfMonth[mm];
					int currentMonth = mm;
					if (currentMonth == 11)
					{
						prevMonth = 10;
						daysInPrevMonth = daysOfMonth[prevMonth];
						nextMonth = 0;
						prevYear = yy;
						nextYear = yy + 1;
					} else if (currentMonth == 0)
					{
						prevMonth = 11;
						prevYear = yy - 1;
						nextYear = yy;
						daysInPrevMonth = daysOfMonth[prevMonth];
						nextMonth = 1;
					} else
					{
						prevMonth = currentMonth - 1;
						nextMonth = currentMonth + 1;
						nextYear = yy;
						prevYear = yy;
						daysInPrevMonth = daysOfMonth[prevMonth];
					}

					// Compute how much to leave before before the first day of the
					// month.
					// getDay() returns 0 for Sunday.
					trailingSpaces = cal.get(Calendar.DAY_OF_WEEK) - 1;

					if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1)
					{
						++daysInMonth;
					}

					// Trailing Month days
					for (int i = 0; i < trailingSpaces; i++)
					{
						list.add(String.valueOf((daysInPrevMonth - trailingSpaces + 1) + i) + "-GREY" + "-" + months[prevMonth] + "-" + prevYear);
					}

					// Current Month Days
					for (int i = 1; i <= daysInMonth; i++)
					{
						list.add(String.valueOf(i) + "-WHITE" + "-" + months[mm] + "-" + yy);
					}

				
					// Leading Month days
					for (int i = 0; i < list.size() % 7; i++)
					{
						Log.d(tag, "NEXT MONTH:= " + months[nextMonth]);
						list.add(String.valueOf(i + 1) + "-GREY" + "-" + months[nextMonth] + "-" + nextYear);
						leadSpaces = i+1;
					}
				
					if((list.size() / 7) < 6)
					{
						
						for (int i = leadSpaces  ; i < leadSpaces+7; i++)
						{
							Log.d(tag, "NEXT MONTH:= " + months[nextMonth]);
							list.add(String.valueOf(i + 1) + "-GREY" + "-" + months[nextMonth] + "-" + nextYear);
						}
						
					
					}
						
				}

				@Override
				public long getItemId(int position)
				{
					return position;
				}

				@Override
				public View getView(int position, View convertView, ViewGroup parent)
				{
					Log.d(tag, "getView ...");
					View row = convertView;
					if (row == null)
					{
						// ROW INFLATION
						
						Log.d(tag, "Starting XML Row Inflation ... ");
						LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						row = inflater.inflate(R.layout.day_gridcell, parent, false);
						//row.setMinimumHeight(height/8);
						Log.d(tag, "Successfully completed XML Row Inflation!");
					}

					DisplayMetrics metrics = new DisplayMetrics();
					getWindowManager().getDefaultDisplay().getMetrics(metrics);
				 
					width = metrics.widthPixels;
					height = metrics.heightPixels;
					gridcell = (Button) row.findViewById(R.id.day_gridcell);
					gridcell.setOnClickListener(this);
					Log.d(tag, "Current Day: " + currentDayOfMonth);
					String[] day_color = list.get(position).split("-");
					gridcell.setText(day_color[0]);
					gridcell.setTag(day_color[0] + "-" + day_color[2] + "-" + day_color[3]);
					gridcell.setMinHeight(height/8);
					if (day_color[1].equals("GREY"))
					{
						gridcell.setTextColor(Color.GRAY);
					}
					if (day_color[1].equals("WHITE"))
					{
						gridcell.setTextColor(Color.BLACK);
					}
					if (position == currentDayOfMonth)
					{

						if(this.month == currentMonth)
						{
							gridcell.setTextColor(Color.BLUE);
					
						}
					}

					return row;
				}

				@Override
				public void onClick(View view)
				{
					String date_month_year = (String) view.getTag();
					AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
					float vol = (float) 0.5; //This will be half of the default system sound
					am.playSoundEffect(AudioManager.FX_KEY_CLICK, vol);
					Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); 
					vb.vibrate(40);
					Intent intent = new Intent(CalendarActivity.this, DisplayActivityList.class);
					intent.putExtra("date", date_month_year);
					startActivity(intent); 	        
				
				}
			}
		
		
			public class weekdayAdapter extends BaseAdapter 
			{
				private Context context;
				private final String[] daysofweek; 
		 
				public weekdayAdapter(Context context, String[] daysofweekIn) 
				{
					this.context = context;
					this.daysofweek = daysofweekIn;
				}
		 
				public View getView(int position, View convertView, ViewGroup parent) 
				{
		 
					LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 
					View gridView;
			 
					if (convertView == null) 
					{
		 
					gridView = new View(context);
					gridView = inflater.inflate(R.layout.daysofweek, null);
					TextView textView = (TextView) gridView.findViewById(R.id.day_cell);
					textView.setText(daysofweek[position]);			
		 
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
					return daysofweek.length;
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
		    public boolean dispatchTouchEvent(MotionEvent me)
			{ 
		      this.detector.onTouchEvent(me);
		     return super.dispatchTouchEvent(me); 
		    }
		
			@Override
			public void onSwipe(int direction) 
			{
			  String str = "";
			  switch (direction) 
			  {
			  
			  	case SimpleGestureFilter.SWIPE_RIGHT : str = "Swipe Right";
			  	if (month >= 11)
				{
					month = 0;
					year++;
				} 
			  	else
			  	{
			  		month++;
			  	}

			  	adapter = new GridCellAdapter(getApplicationContext(), R.id.day_gridcell, month, year);
			  	_calendar.set(year, month, _calendar.get(Calendar.DAY_OF_MONTH));
			  	TextView title2  = (TextView) findViewById(R.id.title);
			  	title2.setText(android.text.format.DateFormat.format("MMMM yyyy", _calendar));
			  	adapter.notifyDataSetChanged();
			  	calendarView.setAdapter(adapter);
			  	break;
	  
			  	case SimpleGestureFilter.SWIPE_LEFT :  str = "Swipe Left";
			  	if (month <= 1)
			  	{
					month = 11;
					year--;
				} else
				{
					month--;
				}

				adapter = new GridCellAdapter(getApplicationContext(), R.id.day_gridcell, month, year);
				_calendar.set(year, month, _calendar.get(Calendar.DAY_OF_MONTH));
				TextView title1  = (TextView) findViewById(R.id.title);
				title1.setText(android.text.format.DateFormat.format("MMMM yyyy", _calendar));
				adapter.notifyDataSetChanged();
				calendarView.setAdapter(adapter);
				                                                 break;
				case SimpleGestureFilter.SWIPE_DOWN :  str = "Swipe Down";
				                                                 break;
				case SimpleGestureFilter.SWIPE_UP :    str = "Swipe Up";
				                                                 break;
	                                           
			  } 
			  Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
			}

			 @Override
			 public void onDoubleTap() {
			    Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show(); 
			 }
    
}
