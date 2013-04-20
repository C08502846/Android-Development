package com.example.trackmyassignment;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewAssignments extends ListActivity
{
	String Assignments[]={"Cloud Comp","Modile Dev" };
	String Assignments1[] = {"Assignment 1"}  ;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		DBManager myDB ;
		myDB = new DBManager(this) ;
		myDB.open();
		for(int i=0; i < 4 ; i++)
		{
			Assignments1.equals(myDB.getTitle2());
		}
		
		// return string array from myDB and put it in New String Array
		myDB.close();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_assignments);		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.row, R.id.assignments, Assignments1));		
	}
	  protected void onListItemClick(ListView l, View v, int position, long id)
	    {   
		     // If Name is clicked, Go to database, get
		    // Start new intent to View All Data about that Assignment.
		    //TextView tv = (TextView) findViewById(R.id.info);
			DBManager info = new DBManager(this);
			info.open();
			String data = info.getData();
			info.close();
			//tv.setText(data);
	    	super.onListItemClick(l, v, position, id); 
	    	String selection = l.getItemAtPosition(position).toString(); 
	    	Toast.makeText(this, selection, Toast.LENGTH_SHORT).show(); 
	    }	  

}
