/*
 * MOBILE SOFTWARE DEVELOPMENT ASSIGNMENT
 * STUDENT NUMBER: C08502846
 * This class is my Database Manager Class.
 * It creates and sets up my database. It creates my table/s and all of the tables columns
 * Its purpose is to handle all of database queries used in my application.
 * 
 */
package com.example.trackmyassignment;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManager 
{
	
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
	
    // This method adds(using insert) an assignment by passing in title, module and duedate to a new ContentValues.
	public long addAssignment(String title, String module, String duedate) 
	{
		ContentValues cv = new ContentValues();
		cv.put(KEY_TITLE, title);
		cv.put(KEY_MODULE, module);
		cv.put(KEY_DUEDATE, duedate);
		return myDB.insert(DATABASE_TABLE, null, cv);			
	}
	
	// This method passes in a  string and queries assignment title according to that string using LIKE SQL statement
	public String getTitle(String title) 
	{
		String[] columns = new String[]{ KEY_ROWID, KEY_TITLE, KEY_MODULE, KEY_DUEDATE};
		Cursor c = myDB.query(DATABASE_TABLE, columns, "assign_title"+" LIKE '"+title+"%'", null, null, null, null);
		String rTitle = "" ;
		while(c.moveToNext())
		{
			rTitle = c.getString(1);
		}
		return rTitle;
	}
	
	// This method queries the Modules column and places them in an ArrayList which is then converted to a String array
	// Source: http://stackoverflow.com/questions/8938847/android-transform-each-column-of-a-cursor-into-a-string-array
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
	
    // Returns a string when a Title is passed in and is found in the Title column
	public String getModule(String title) 
	{
		String[] columns = new String[]{ KEY_ROWID, KEY_TITLE, KEY_MODULE, KEY_DUEDATE};
		Cursor c = myDB.query(DATABASE_TABLE, columns, "assign_title"+" LIKE '"+title+"%'", null, null, null, null);
		String rTitle = "" ;
		while(c.moveToNext())
		{
			rTitle = c.getString(2);
		}
		return rTitle;
	}
	
	// Returns a string when a Title is passed in and is found in the Title column
	public String getDueDate(String title) 
	{
		String[] columns = new String[]{ KEY_ROWID, KEY_TITLE, KEY_MODULE, KEY_DUEDATE};
		Cursor c = myDB.query(DATABASE_TABLE, columns, "assign_title"+" LIKE '"+title+"%'", null, null, null, null);
		String rTitle = "" ;
		while(c.moveToNext())
		{
			rTitle = c.getString(3);
		}
		return rTitle;
	}
	
	// Updates Assignments Table by passing in 3 values, using SQL Update
	public void updateAssignments(String eTitle, String eModule, String eDueDate) 
	{
		ContentValues cv = new ContentValues();
		cv.put(KEY_TITLE, eTitle);
		cv.put(KEY_MODULE, eModule);
		cv.put(KEY_DUEDATE, eDueDate);
		myDB.update(DATABASE_TABLE, cv, "assign_title"+" LIKE '"+eTitle+"%'", null);		
	}
	
    // Simply deletes a row when title passed in, matches a title in the Title column
	public void delete(String title) 
	{
		myDB.delete(DATABASE_TABLE, "assign_title"+" LIKE '"+title+"%'", null); 
	}
	
	// When a user clicks an item in the list, pass in the module selected and return data from Title and Duedate Columns
	public String getDataByModule(String myString)
	{
		String[] columns = new String[]{ KEY_TITLE, KEY_DUEDATE};
		Cursor c = myDB.query(DATABASE_TABLE, columns, "assign_module"+" LIKE '"+myString+"%'", null, null, null, null);
		String myData = "" ;
		while(c.moveToNext())
		{
			myData +="------------------------------------------------" +"\n" + "Title: " + c.getString(0) + "\n" + "Due Date: " + c.getString(1) + "\n"
					+"------------------------------------------------"
					+ "Assignment Completed?\n"; //+ c.getString(2) + "\n"; 
		}
		return myData;
	}
	// Function to delete row by module when user clicks Complete in Dialog in ViewAssignments
	public void complete(String deleteData) 
	{
		myDB.delete(DATABASE_TABLE, "assign_module"+" LIKE '"+deleteData+"%'", null);
	}
}
