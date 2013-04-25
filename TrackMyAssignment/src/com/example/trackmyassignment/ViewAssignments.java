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

	DBManager myDB = new DBManager(this) ;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
			super.onCreate(savedInstanceState);
	    	setContentView(R.layout.view_assignments);
		 	
		String[] assignments1 = {"Assign 1", "Assign 2"};		
		myDB.open();		
		String[] assignments = myDB.getTitle2() ;		

		
		

		setListAdapter(new ArrayAdapter<String>(this, R.layout.row, R.id.assignments, assignments));
		myDB.close();
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