/*
 * Mobile Software Development - Assignment
 * Student Number: C08502846
 * Github Repository(Public): https://github.com/C08502846/Android-Development
 */


package com.example.trackmyassignment;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;


public class DBManager {
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_TITLE = "assign_title";
	public static final String KEY_MODULE= "assign_module";
	public static final String KEY_DUEDATE= "assign_duedate";
	
	private static final String DATABASE_NAME = "TrackMyAssignment";
	static final String DATABASE_TABLE = "Assignments";
	private static final int DATABASE_VERSION= 1;
	private static final String CREATE_DATABASE= "CREATE TABLE " + DATABASE_TABLE + " (" +
            KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		    KEY_TITLE + " TEXT NOT NULL, " + 
		    KEY_MODULE + " TEXT NOT NULL, " + 
		    KEY_DUEDATE + " TEXT NOT NULL);" ;
	
	
	private DbHelper myHelper;
	private final Context myContext ;
	private SQLiteDatabase myDB;
	
	private static class DbHelper extends SQLiteOpenHelper
	{

		public DbHelper(Context context) 
		{			
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			db.execSQL(CREATE_DATABASE);			
			Log.w("myApp", "no network");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			// To Upgrade Database
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);			
		}		
	}
	public DBManager(Context c)
	{
		myContext = c;
	}
	

	void publicDBManager(
			android.content.DialogInterface.OnClickListener onClickListener) {
		// TODO Auto-generated constructor stub
	}


	public DBManager open()
	{
		myHelper = new DbHelper(myContext);
		myDB = myHelper.getWritableDatabase();
		return this;
	}
	public void close()
	{
		myHelper.close(); // To close DbHelper
	}

	public long addAssignment(String title, String module, String duedate) 
	{
		ContentValues cv = new ContentValues();
		cv.put(KEY_TITLE, title);
		cv.put(KEY_MODULE, module);
		cv.put(KEY_DUEDATE, duedate);
		return myDB.insert(DATABASE_TABLE, null, cv);			
	}

	public String getAllData() 
	{
		String[] columns = new String[]{ KEY_ROWID, KEY_TITLE, KEY_MODULE, KEY_DUEDATE};
		Cursor c = myDB.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String result = "" ;
		
//		int iRow = c.getColumnIndex(KEY_ROWID);
//		int iTitle = c.getColumnIndex(KEY_TITLE);
//		int iModule = c.getColumnIndex(KEY_MODULE);
//		int iDescription = c.getColumnIndex(KEY_DESCRIPTION);
//		int iDueDate = c.getColumnIndex(KEY_DUEDATE);
		
		while(c.moveToNext())
		{
			result = result +c.getString(0)+","  +c.getString(1)+ "," +c.getString(2)+"," +c.getString(3)+ "\n";
		}
		return result;
	}

	public String getTitle(long l) // Get Title by rowID
	{
		String[] columns = new String[]{ KEY_ROWID, KEY_TITLE, KEY_MODULE, KEY_DUEDATE};
		Cursor c = myDB.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		String title = "" ;
		while(c.moveToNext())
		{
			title = c.getString(1);
		}
		return title;
	}
	public String getTitle1() 
	{
		String[] columns = new String[]{ KEY_ROWID, KEY_TITLE};
		Cursor c = myDB.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String title = "" ;
		while(c.moveToNext())
		{
			title += c.getString(1) + "\n";
		}
		return title;
	}
	public String[] getTitleForList() 
	{
		String[] columns = new String[]{ KEY_ROWID, KEY_TITLE};
		Cursor c = myDB.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String[] title = null  ;
		int i = 0 ;
		ArrayList<String> columnArray1 = new ArrayList<String>();
		while(c.moveToNext())
		        {			
			        columnArray1.add(c.getString(1));
		        }
		String[] colStrArr1 = (String[]) columnArray1.toArray(new String[columnArray1.size()]);

		return colStrArr1;
	}
	public String[] getModuleForList() 
	{
		String[] columns = new String[]{ KEY_ROWID, KEY_MODULE};
		Cursor c = myDB.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String[] module = null  ;
		int i = 0 ;
		ArrayList<String> modules = new ArrayList<String>();
		while(c.moveToNext())
		        {			
			        modules.add(c.getString(1));
		        }
		String[] modulesReturn = (String[]) modules.toArray(new String[modules.size()]);

		return modulesReturn;
	}
	

	public String getTitle4() 
	{
		String[] columns = new String[]{ null, KEY_TITLE, null, null, null,};
		Cursor c = myDB.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String result = "" ;
			
		while(c.moveToNext())
		{
			result = result  + c.getString(1) + "\n";
		}
		return result;
	}
	

	public String getModule(long l) 
	{
		String[] columns = new String[]{ KEY_ROWID, KEY_TITLE, KEY_MODULE, KEY_DUEDATE};
		Cursor c = myDB.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		String module = "" ;
		while(c.moveToNext())
		{
			module = c.getString(2);
		}
		return module;
	}

	public String getDescription(long l) 
	{
	String[] columns = new String[]{ KEY_ROWID, KEY_TITLE, KEY_MODULE, KEY_DUEDATE};
	Cursor c = myDB.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
	String description = "" ;
	while(c.moveToNext())
	{
		description = c.getString(3);
	}
	return description;
	}

	public String getDueDate(long l) 
	{
		String[] columns = new String[]{ KEY_ROWID, KEY_TITLE, KEY_MODULE, KEY_DUEDATE};
		Cursor c = myDB.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		String duedate = "" ;
		while(c.moveToNext())
		{
			duedate = c.getString(3);
		}
		return duedate;
	}

	public String getDataPos(long l) 
	{
		String[] columns = new String[]{ KEY_ROWID, KEY_TITLE, KEY_MODULE, KEY_DUEDATE};
		Cursor c = myDB.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		String allInfo = "" ;
		while(c.moveToNext())
		{
			allInfo = c.getString(4);
		}
		return allInfo;
	}

	public void editAssignments(long l, String eTitle, String eModule, String eDueDate) 
	{
		ContentValues cv = new ContentValues();
		cv.put(KEY_TITLE, eTitle);
		cv.put(KEY_MODULE, eModule);
		cv.put(KEY_DUEDATE, eDueDate);
		myDB.update(DATABASE_TABLE, cv, KEY_ROWID + "=" + l, null);
		
	}

	public void delete(long l) 
	{
		myDB.delete(DATABASE_TABLE, KEY_ROWID + "=" + l, null); 
	}	
	public String getDataByModule(String myString)
	{
		String[] columns = new String[]{ KEY_TITLE, KEY_DUEDATE};
		Cursor c = myDB.query(DATABASE_TABLE, columns, "assign_module"+" LIKE '"+myString+"%'", null, null, null, null);
		String myData = "" ;
		while(c.moveToNext())
		{
			myData += "Title: " + c.getString(0) + "\n" + "Due Date: " + c.getString(1) + "\n"; //+ c.getString(2) + "\n"; 
		}
		return myData;
	}
	public void complete(String deleteData)
	{
		myDB.delete(DATABASE_TABLE, "assign_module"+" LIKE '"+deleteData+"%'", null);
	}
}
