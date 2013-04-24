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
	String[] Assignments ;
	String[] Assignments1 = {"Assign 1", "Assign 2"};
	Cursor rAssignments ;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.view_assignments);
		
		DBManager myDB ;
		myDB = new DBManager(this) ;
		myDB.open();
		System.out.println("DB Opened");
		for (int i = 0 ; i < 2 ; i++)
		{	
			System.out.println("Before Loop");
			//Assignments.equals(myDB.getTitle1());
			Assignments[i] += myDB.getTitle1();
			Log.i("Assignments Value:", Assignments[i]);		
		}
		System.out.println("After Loop");
		
		myDB.close();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_assignments);	

		setListAdapter(new ArrayAdapter<String>(this, R.layout.row, R.id.assignments, Assignments));		
	}
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