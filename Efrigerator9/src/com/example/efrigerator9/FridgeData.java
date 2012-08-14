//<revision>
// 20120813 : aat
// Issue #2 : Fully functional database.
// Finally deletion is successfully implemented
// Issue #2: 20120807 at
// DS databasehelper activity creation
// Issue #2: 20120810 at
// DS databasehelper activity creation
// Except deletion, added every functionality mentioned in DS
// </revision>
package com.example.efrigerator9;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;



public class FridgeData {
	
	public static final String C_ID = BaseColumns._ID; // Special for id
	  public static final String C_CREATED_DATE = "Fridge_createdDate"; 
	  public static final String C_ITEM = "Fridge_item";
	  public static final String C_CAT = "Fridge_category";

	  Context context;
	  DbHelper dbHelper;

	  public FridgeData(Context context) {
	    this.context = context;
	    dbHelper = new DbHelper();
	  }
	  
	  public void close() {
		    dbHelper.close();
		  }

	  /**
	   * Inserts into database
	   * 
	   * @param values
	   *          Name/value pairs data
	   */
	  public long insert(ContentValues values) {
	    // Open Database
	    SQLiteDatabase db = dbHelper.getWritableDatabase();

	    // Insert into database
	    long ret;
	    try {
	      ret = db.insertOrThrow(DbHelper.TABLE, null, values);
	     
	    } catch (SQLException e) {
	      ret = -1;
	    } finally {
	      db.close();
	    }
	    return ret;
	  }
	  
	  /**
	   * Inserts into database
	   * 
	   * @param status
	   *          Status data as provided by online service
	   */
	  public long insert(int id,String exp_date,String item_name, String Cat ) {
	    ContentValues values = new ContentValues();
	  //  values.put(FridgeData.C_ID, null);
	    values.put(FridgeData.C_CREATED_DATE, exp_date);
	    values.put(FridgeData.C_ITEM, item_name);
	    values.put(FridgeData.C_CAT, Cat);
	    return this.insert(values);
	  }
	  
	  /**
	   * Deletes ALL the data
	   */
	  
	  public void delete(String item_name) {
	    // Open Database
	    SQLiteDatabase db = dbHelper.getWritableDatabase();

	    
	    
	    try {
	      db.delete(DbHelper.TABLE, C_ITEM+"=?", new String[]{item_name});
	   
	    } catch (SQLException e) {
	     
	    } finally {
	      db.close();
	    }
	
	  }

	  /**
	   * Deletes ALL the data
	   */
	  public void delete_all() {
	    // Open Database
	    SQLiteDatabase db = dbHelper.getWritableDatabase();

	    // Delete the data
	    db.delete(DbHelper.TABLE, null, null);

	    // Close Database
	    db.close();
	  }

	  public Cursor query() {
	    // Open Database
	    SQLiteDatabase db = dbHelper.getReadableDatabase();
	    return db.query(DbHelper.TABLE, null, null, null, null, null, C_CREATED_DATE
	        + " DESC");
	  }

	  private class DbHelper extends SQLiteOpenHelper {
		    public static final String DB_NAME = "timeline.db";
		    public static final int DB_VERSION = 4;
		    public static final String TABLE = "statuses";

		    public DbHelper() {
		      super(context, DB_NAME, null, DB_VERSION);
		    }

		    @Override
		    public void onCreate(SQLiteDatabase db) {
		      String sql = String.format(
		          "create table %s (%s int primary key, %s TEXT, %s TEXT, %s TEXT)",
		          TABLE, C_ID, C_CREATED_DATE, C_ITEM, C_CAT);
		      // sql = context.getString(R.string.sql);

		     // Log.d(TAG, "onCreate sql: " + sql);

		      db.execSQL(sql);
		    }

		    @Override
		    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		      // Typically you do ALTER TABLE... here
		      db.execSQL("drop table if exists " + TABLE);
		      //Log.d(TAG, "onUpdate dropped table " + TABLE);
		      this.onCreate(db);
		    }

		  }
}
