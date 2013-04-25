package com.example.trackmyassignment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ViewAssignments extends ListActivity implements OnClickListener
{

	DBManager myDB = new DBManager(this) ;
	Button addNew ;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.view_assignments);
	    addNew = (Button) findViewById(R.id.addNew);
	    addNew.setOnClickListener(this);
	    
		myDB.open();		
		String[] assignments = myDB.getTitle2() ;
		setListAdapter(new ArrayAdapter<String>(this, R.layout.row, R.id.assignments, assignments));
		myDB.close();
	}
	  protected void onListItemClick(ListView l, View v, int position, long id)
	    {
		  DBManager myDB= new DBManager(this);
		  myDB.open();
		  super.onListItemClick(l, v, position, id);
		  String selection = l.getItemAtPosition(position).toString();
		  AlertDialog.Builder builder = new AlertDialog.Builder(this);
		  builder.setTitle(selection).setMessage(myDB.getAllData()).setCancelable(false)
		  .setPositiveButton("Complete", new DialogInterface.OnClickListener(){
		   public void onClick(DialogInterface dialog, int id)
		     {
		    	 // myDB.deleteAssign(position);
		     }
		  });
		  AlertDialog alert = builder.create();
		  alert.show();		  
		  myDB.close();
//		  myDB.getAllData();
//		  Dialog d = new Dialog(this);
//		  
//		  d.setTitle(selection);		
//		  TextView text = new  TextView(this);
//		  text.setTextColor(Color.parseColor("#FFFFFF"));
//		  text.setText(myDB.getAllData());
//		  
//		  d.setContentView(text);
//		  d.show();
//		  
		
		    // If Name is clicked, Go to database, get
		    // Start new intent to View All Data about that Assignment.
		    //TextView tv = (TextView) findViewById(R.id.info);
//			DBManager myDB= new DBManager(this);
//			myDB.open();
//			String data = myDB.getAllData();
//			myDB.close();
//			//tv.setText(data);
//	    	super.onListItemClick(l, v, position, id); 
//	    	String selection = l.getItemAtPosition(position).toString(); 
//	    	Toast.makeText(this, selection, Toast.LENGTH_SHORT).show(); 
	    }
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.addNew:
			System.out.println("Add New Pressed");
			startActivity(new Intent(ViewAssignments.this, MainActivity.class));
		    break;
		}
			
		
		
	}	  

}