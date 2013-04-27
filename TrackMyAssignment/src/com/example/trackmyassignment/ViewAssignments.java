package com.example.trackmyassignment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewAssignments extends ListActivity implements OnClickListener
{
	
	EditText assignTitle, assignDueDate, rowID ;

	
	DBManager myDB = new DBManager(this) ;
	Button addNew ;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		assignTitle =  (EditText) findViewById(R.id.assignTitle);		  		
		assignDueDate = (EditText) findViewById(R.id.assignDueDate);
		rowID = (EditText) findViewById(R.id.rowID);
		  
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.view_assignments);
	    addNew = (Button) findViewById(R.id.addNew);
	    addNew.setOnClickListener(this);
	    
		myDB.open();		
		String[] assignments = myDB.getModuleForList() ;
		setListAdapter(new ArrayAdapter<String>(this, R.layout.row, R.id.assignments, assignments));
		
		if(assignments == null)
		{
			String[] empty = {"No assignments added yet."};
			setListAdapter(new ArrayAdapter<String>(this, R.layout.row, R.id.assignments, empty));
		}
		myDB.close();
	}
	
	  protected void onListItemClick(ListView l, View v, int position, long id)
	    {
		   //super.onListItemClick(null, v, position, id);
		  System.out.println("List Clicked");				  
		  String selection = l.getItemAtPosition(position).toString();
		  
		  
		  myDB.open();	
			
		  AlertDialog.Builder builder = new AlertDialog.Builder(this);
		  builder.setTitle(selection).setMessage(myDB.getAllData()).setCancelable(false).
		  setNegativeButton("Close", new DialogInterface.OnClickListener(){
			   public void onClick(DialogInterface dialog, int id)
			     {
				   Log.i("TEST", "Close Clicked!");
			    	 // Closes Dialog
			     }
			  })
		  .setPositiveButton("Complete", new DialogInterface.OnClickListener(){
		   public void onClick(DialogInterface dialog, int id)
		     {
			     Log.i("TEST", "Complete Clicked!");
			     //complete(selection);
		    	 // Delete THIS position from DB. :)			    
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
	    }
		  
		
		  
//			DBManager myDB= new DBManager(this);
//			myDB.open();
//			String data = myDB.getAllData();
//			myDB.close();
//			//tv.setText(data);
//	    	super.onListItemClick(l, v, position, id); 
//	    	String selection = l.getItemAtPosition(position).toString(); 
//	    	Toast.makeText(this, selection, Toast.LENGTH_SHORT).show(); 
//	    }
	public void complete(String selection)
	  {
		    String cTitle = assignTitle.getText().toString();							
			String cDueDate = assignDueDate.getText().toString();			
			String s = rowID.getText().toString(); // Convert whats in Editext into long type
			long l = Long.parseLong(s);
			myDB.delete(l);
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