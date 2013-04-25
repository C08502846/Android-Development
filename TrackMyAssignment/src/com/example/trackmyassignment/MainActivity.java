package com.example.trackmyassignment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
	
	Button update, view, get, edit, delete;
	EditText assignTitle, assignModule, assignDescr, assignDueDate,
	rowID ;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		update = (Button) findViewById(R.id.update);
		view = (Button) findViewById(R.id.view);
		get = (Button) findViewById(R.id.get);
		edit = (Button) findViewById(R.id.edit);
		delete = (Button) findViewById(R.id.delete);
		
		assignTitle =  (EditText) findViewById(R.id.assignTitle);
		assignModule = (EditText) findViewById(R.id.assignModule);
		assignDescr = (EditText) findViewById(R.id.assignDescription);
		assignDueDate = (EditText) findViewById(R.id.assignDueDate);
		rowID = (EditText) findViewById(R.id.rowID);

		update.setOnClickListener(this);
		view.setOnClickListener(this);
		get.setOnClickListener(this);
		edit.setOnClickListener(this);
		delete.setOnClickListener(this);
	}
	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.update:

			    String title = assignTitle.getText().toString();
			    String module = assignModule.getText().toString();
			    String description = assignDescr.getText().toString();
			    String duedate = assignDueDate.getText().toString();
			    
			    
			        if (title.isEmpty())
			        {
			    	Toast toast = Toast.makeText(this, "Empty Title.", Toast.LENGTH_SHORT);
					toast.show();
			        }
					else if(module.isEmpty())
					{
						Toast toast3 = Toast.makeText(this, "Empty Module.", Toast.LENGTH_SHORT);
						toast3.show();
					}
					else if(description.isEmpty())
					{
						Toast toast4 = Toast.makeText(this, "Empty Desctiption.", Toast.LENGTH_SHORT);
						toast4.show();
					}
					else if( duedate.isEmpty())
					{
						Toast toast5 = Toast.makeText(this, "Empty Due Date.", Toast.LENGTH_SHORT);
						toast5.show();
					}
//					else if ( title.isEmpty() && module.isEmpty() && description.isEmpty() && duedate.isEmpty())
//					{
//						Toast toast6 = Toast.makeText(this, "All Fields Empty.", Toast.LENGTH_SHORT);
//						toast6.show();
//					}
						
					else
					{
						 DBManager myDB = new DBManager(this);
						    myDB.open();
						    myDB.addAssignment(title, module, description, duedate);
						    
						    assignTitle.setText(""); // Set EditTexts to appear blank when data is entered
						    assignModule.setText("");
						    assignDescr.setText("");
						    assignDueDate.setText("");

						    myDB.close();
							
							Dialog d = new Dialog(this);
							d.setTitle("Assignment Added.");
							TextView tv = new TextView(this);
							tv.setText("Best of luck in your Assignment!");
							d.setContentView(tv);
							Toast toast2 = Toast.makeText(this, "Assignment Added. \nGood Luck in your "+title+" Assignment!", Toast.LENGTH_SHORT);
							toast2.show();
							break;
					}
			    
			   					
		case R.id.get:
			try
			{
				String s = rowID.getText().toString(); // Convert whats in Editext into long type
				long l = Long.parseLong(s);
				DBManager myDB2 = new DBManager(this);
				myDB2.open();
				String rTitle = myDB2.getTitle(l);
				String rModule = myDB2.getModule(l);
				String rDescription = myDB2.getDescription(l);
				String rDueDate = myDB2.getDueDate(l) ;
				myDB2.close();
				
				assignTitle.setText(rTitle);
				assignModule.setText(rModule);
				assignDescr.setText(rDescription);
				assignDueDate.setText(rDueDate);
				Toast toast2 = Toast.makeText(this, "" +rTitle+ " found.", Toast.LENGTH_SHORT);
				toast2.show();
			}
			catch(NumberFormatException e)
			{
				System.out.println("Exception Caught!");
				Toast toast2 = Toast.makeText(this, "Please try again.", Toast.LENGTH_SHORT);
				toast2.show();				
			}
			break;
			
		case R.id.view:			
			    System.out.println("View Pressed");
			    Intent i = new Intent("com.example.trackmyassignment.ViewAssignments");
			    startActivity(i);			
			break;
	    }		
    }
}

	

