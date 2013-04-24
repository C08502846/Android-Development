package com.example.trackmyassignment;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewAssignments extends ListActivity
{
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		try
		{
			super.onCreate(savedInstanceState);
	    	setContentView(R.layout.view_assignments);
		String[] assignments={"","","",""} ;		
		String[] assignments1 = {"Assign 1", "Assign 2"};	
		DBManager myDB ;
		myDB = new DBManager(this) ;
		
		myDB.open();
		String test ;
		
		test = myDB.getTitle1() ;
		Log.i("test = ", test);		
		
		for(int i=0; i < assignments.length ; i++)
		{
			assignments[i] = myDB.getTitle1() ;
		}			
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.row, R.id.assignments, assignments));
		myDB.close();
		}
		
		catch(NullPointerException e)
		{
			Toast toast = Toast.makeText(this, "Null Pointer!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
//	for(int i=0 ; i < 3 ; i++)
//	{
//		//Assignments[i] = myDB.getTitle2()[i];
//		//Assignments[i] = myDB.getTitle2()[i];			
//		//Assignments1[i] += myDB.getTitle25()[i];
//		//Assignments1.equals(myDB.getTitle25());
//	}
	//Assignments1.equals(myDB.getTitle25());
	  protected void onListItemClick(ListView l, View v, int position, long id)
	    {   
		     // If Name is clicked, Go to database, get
		    // Start new intent to View All Data about that Assignment.
		    //TextView tv = (TextView) findViewById(R.id.info);
			DBManager info = new DBManager(this);
			info.open();
			String data = info.getAllData();
			info.close();
			//tv.setText(data);
	    	super.onListItemClick(l, v, position, id); 
	    	String selection = l.getItemAtPosition(position).toString(); 
	    	Toast.makeText(this, selection, Toast.LENGTH_SHORT).show(); 
	    }	  

}