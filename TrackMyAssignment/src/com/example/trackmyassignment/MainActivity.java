package com.example.trackmyassignment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
	
	Button add, update, view, find, delete, clear ;
	EditText assignTitle, assignModule, assignDueDate,
	rowID ;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{		
		//ActionBar actionBar = getActionBar();
		//actionBar.hide();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		add = (Button) findViewById(R.id.add);
		view = (Button) findViewById(R.id.view);
		find = (Button) findViewById(R.id.find);
		update = (Button) findViewById(R.id.update);
		delete = (Button) findViewById(R.id.delete);
		clear = (Button) findViewById(R.id.clear);

		
		assignTitle =  (EditText) findViewById(R.id.assignTitle);
		assignModule = (EditText) findViewById(R.id.assignModule);		
		assignDueDate = (EditText) findViewById(R.id.assignDueDate);
		rowID = (EditText) findViewById(R.id.rowID);

		add.setOnClickListener(this);
		view.setOnClickListener(this);
		find.setOnClickListener(this);
		update.setOnClickListener(this);
		delete.setOnClickListener(this);
		clear.setOnClickListener(this);
	}
	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.add:

			    String title = assignTitle.getText().toString();
			    String module = assignModule.getText().toString();			  
			    String duedate = assignDueDate.getText().toString();
			    
			    
			        if (title.isEmpty())
			        {
			    	    Toast toast = Toast.makeText(this, "Empty Title.", Toast.LENGTH_SHORT);
					    toast.show();
					    break;
			        }
					else if(module.isEmpty())
					{
						Toast toast1 = Toast.makeText(this, "Empty Module.", Toast.LENGTH_SHORT);
						toast1.show();
						break;
					}					
					else if( duedate.isEmpty())
					{
						Toast toast2 = Toast.makeText(this, "Empty Due Date.", Toast.LENGTH_SHORT);
						toast2.show();
						break;
					}

						
					else
					{
						 DBManager myDB = new DBManager(this);
						 myDB.open();
						 myDB.addAssignment(title, module, duedate);
						    
						    assignTitle.setText(""); // Set EditTexts to appear blank when data is entered
						    assignModule.setText("");						   
						    assignDueDate.setText("");

						    myDB.close();
							
							Dialog d = new Dialog(this);
							d.setTitle("Assignment Added.");
							TextView tv = new TextView(this);
							tv.setText("Best of luck in your Assignment!");
							d.setContentView(tv);
							Toast toast3 = Toast.makeText(this, "              " +
									"Assignment Added.\nGood Luck in your "+title+" Assignment!", Toast.LENGTH_SHORT);
							toast3.show();
							break;
					}		    
			   					
		case R.id.find:
			try
			{
				String s = rowID.getText().toString(); // Convert whats in Editext into long type
				long l = Long.parseLong(s);
				DBManager myDB2 = new DBManager(this);
				myDB2.open();
				String rTitle = myDB2.getTitle(l);
				String rModule = myDB2.getModule(l);				
				String rDueDate = myDB2.getDueDate(l) ;
				myDB2.close();
				
				assignTitle.setText(rTitle);
				assignModule.setText(rModule);				
				assignDueDate.setText(rDueDate);
				
				if(rTitle.isEmpty() && rModule.isEmpty()  && rDueDate.isEmpty())
				{
					Toast toast4 = Toast.makeText(this, "No Data Found.", Toast.LENGTH_SHORT);
					toast4.show();
					break;
				}
				else
				{
					Toast toast5 = Toast.makeText(this, "" +rTitle+ " found.", Toast.LENGTH_SHORT);
					toast5.show();
					break;
				}
				
			}
			catch(NumberFormatException e)
			{				
				Toast toast6 = Toast.makeText(this, "Please Search again.", Toast.LENGTH_SHORT);
				toast6.show();
				break;
			}
		case R.id.view:
			try
			{
				System.out.println("View Pressed.");
			    startActivity(new Intent(MainActivity.this, ViewAssignments.class));
			    break;
			}
			catch(ActivityNotFoundException e)
			{
				Toast toast7 = Toast.makeText(this, "Activity Not Found.", Toast.LENGTH_SHORT);
				toast7.show();
				break;
			}
		case R.id.update:
		    try {
				String eTitle = assignTitle.getText().toString();
				String eModule = assignModule.getText().toString();				
				String eDueDate = assignDueDate.getText().toString();
				
				String s = rowID.getText().toString(); // Convert whats in Editext into long type
				long l = Long.parseLong(s);
				
				DBManager myDB ;
				myDB = new DBManager(this) ;
				myDB.open();
				myDB.editAssignments(l, eTitle, eModule, eDueDate);	
				myDB.close();
				Toast toast8 = Toast.makeText(this, "Assignment successfully edited.", Toast.LENGTH_SHORT);
				toast8.show();	
				break;
			} 
		    catch (NumberFormatException e) 
			{				
				Toast toast9 = Toast.makeText(this, "Nothing to Update.", Toast.LENGTH_SHORT);
				toast9.show();
				e.printStackTrace();
				break;
			}
		case R.id.delete:
			try {
				String s = rowID.getText().toString(); // Convert whats in Editext into long type
				long l = Long.parseLong(s);
				DBManager myDB ;
				myDB = new DBManager(this) ;
				myDB.open();
				myDB.delete(l);
				myDB.close();
				Toast toast11 = Toast.makeText(this, "Assignment successfully deleted", Toast.LENGTH_SHORT);
				toast11.show();
				break;
			} 
			catch (NumberFormatException e) 
			{
				Toast toast2 = Toast.makeText(this, "Nothing to delete.", Toast.LENGTH_SHORT);
				toast2.show();
				e.printStackTrace();
				break;
			}
		case R.id.clear:
			
				assignTitle.setText(""); // Set EditTexts to appear blank when data is entered
			    assignModule.setText("");						   
			    assignDueDate.setText("");
			    rowID.setText("");
			    Toast toast12 = Toast.makeText(this, "Cleared.", Toast.LENGTH_SHORT);
				toast12.show();	
				break;
	    }		
    }

}

	

