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
	public String globalSelection ;
	
	DBManager myDB = new DBManager(this) ;
	Button manageAssignments ;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{		
		assignTitle =  (EditText) findViewById(R.id.assignTitle);		  		
		assignDueDate = (EditText) findViewById(R.id.assignDueDate);
		//rowID = (EditText) findViewById(R.id.rowID);
		  
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.view_assignments);
	    manageAssignments = (Button) findViewById(R.id.manageAssignments);
	    manageAssignments.setOnClickListener(this);
	    
		myDB.open();		
		String[] assignments = myDB.getModuleForList() ;
		setListAdapter(new ArrayAdapter<String>(this, R.layout.row, R.id.assignments, assignments));		
		myDB.close();
	}
	
	  protected void onListItemClick(ListView l, View v, int position, long id)
	    {
		   //super.onListItemClick(null, v, position, id);
		  System.out.println("List Clicked");				  
		  final String selection = l.getItemAtPosition(position).toString();		  
		  globalSelection = selection ; // made for showToast method below
		  myDB.open();	
		  String data = null ;		  
		  data = myDB.getDataByModule(selection);
		  AlertDialog.Builder builder = new AlertDialog.Builder(this);
		  builder.setTitle(selection).setMessage(myDB.getDataByModule(selection)).setCancelable(false).
		  setNegativeButton("Close", new DialogInterface.OnClickListener(){
			   public void onClick(DialogInterface dialog, int id)
			     {
				   Log.i("TEST", "Close Clicked!");
			    	 // Closes Dialog by default
			     }
			  })
		  .setPositiveButton("Complete", new DialogInterface.OnClickListener(){
		   public void onClick(DialogInterface dialog, int id)
		     {
			     myDB.open();
			     myDB.complete(selection);
			     showToast();
			     finish();
			     startActivity(getIntent());		     
			     myDB.close();		    
		     }	
		   
		  });
		  
		  AlertDialog alert = builder.create();		  
		  alert.show();			  
		  myDB.close();		  
	    }
//	public void complete(String selection) // Function to delete row by rowID when user clicks Complete in Dialog
//	  {
//		    String cTitle = assignTitle.getText().toString();							
//			String cDueDate = assignDueDate.getText().toString();			
//			String s = rowID.getText().toString(); // Convert whats in Editext into long type
//			long l = Long.parseLong(s);
//			myDB.delete(l);
//	  }
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.manageAssignments:
			System.out.println("Add New Pressed");
			startActivity(new Intent(ViewAssignments.this, MainActivity.class));
		    break;
		}		
	}	
	public void showToast()
	{
		Toast toast14 = Toast.makeText(this, "Congratulations on completing your " +globalSelection+ " assignment!", Toast.LENGTH_SHORT);
		toast14.show();
	}
	//TODO 
	public void randomToastMaker()
	{
		Toast a, b, c, d, e, f, g ;
		a = Toast.makeText(this, "Congratulations on completing your " +globalSelection+ " assignment!", Toast.LENGTH_LONG);
	}

}