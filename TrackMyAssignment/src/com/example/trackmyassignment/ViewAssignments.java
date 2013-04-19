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
	String Assignments[]={"Cloud Comp","Modile Dev", "Team Project", "Universal Design", "Robotics" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_assignments);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.row, R.id.country, Assignments));
		
		TextView tv = (TextView) findViewById(R.id.info);
		DBManager info = new DBManager(this);
		info.open();
		String data = info.getData();
		info.close();
		tv.setText(data);
	}
	  protected void onListItemClick(ListView l, View v, int position, long id)
	    {   
	    	super.onListItemClick(l, v, position, id); 
	    	String selection = l.getItemAtPosition(position).toString(); 
	    	Toast.makeText(this, selection, Toast.LENGTH_SHORT).show(); 
	    }

}
