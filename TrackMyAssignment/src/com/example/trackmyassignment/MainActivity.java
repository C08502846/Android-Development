/*MOBILE SOFTWARE DEVELOPMENT ASSIGNMENT
 * STUDENT NUMBER: C08502846
 * 
 * Description: The purpose of this assignment is to manage and track your assignments in a database.
 * The user can add an assignment by entering in the title of the assignment, the module and the date
 * that it is due. The user can view the assignments, oldest first. The user can then click on each
 * item in the list of Modules which brings up a dialog which displays the title of the assignment
 * and its due date. 
 * This Dialog Has 2 buttons. Complete and Close. User can press Complete which removes the
 * assignment from the list. They can click close to resume browsing their list. If User
 * presses complete, they are greeted with a friendly congratulations message on completing
 * their project, which I believe is important to keep students motivated and coming back
 * to use the app. Once finished browsing, the user can hit the one button that is under the list
 * 'Manage Assignments'.
 * This page is a lot more complex than the view page. There are 6 buttons.
 * All buttons give a response when first clicked to alert the user of a misclick or mistake.
 * Add Button: User enters details. They cannot leave fields blank as appropriate error checking was implemented.
 * View Button: Starts the View Activity in which I explained earlier.
 * Then there is a Find By Title EditText field. The user can search assignment by title. They enter the title
 * and then press the Find Button. This carries out a unique search by 'title' and returns any fields 
 * found back into the EditTexts that are on the screen. You can then see all of the assignment details
 * back in the EditText boxes just like when you entered them in the first place.
 * The user is allowed to change the returned info. Once altered, they can then press the 
 * Update button. This button updates the assignment if any changes needed to be made.
 * Users can also delete assignments in this Activity, simliar to that of the View activity.
 * Once deleted, the fields are set to blank again to alert the user of completeness
 * Then there is a clear button which clears all of the EditTexts in case the user wants to 
 * clear a field or two after a mistake.
 * */



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
		switch(v.getId()) // Switch statement to identify which button is clicked by button id
		{
		case R.id.add: // When add button is clicked

			    String title = assignTitle.getText().toString(); 
			    String module = assignModule.getText().toString();			  
			    String duedate = assignDueDate.getText().toString();			    
			    
			        if (title.isEmpty()) // Checking for empty fields so that user is notified by toast message
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
						
					else // When fields are not empty, add Assignment
					{
						 DBManager myDB = new DBManager(this);
						 myDB.open(); 
						 myDB.addAssignment(title, module, duedate); // Call function in DBManager class, adds 3 items
						    
						 assignTitle.setText(""); // Set EditTexts to appear blank when data is entered to illustrate completeness
						 assignModule.setText("");						   
						 assignDueDate.setText("");

						 myDB.close();	
						 
					     Dialog d = new Dialog(this);
						 d.setTitle("Assignment Added.");					 
						 Toast toast3 = Toast.makeText(this, "              " +
						 "Assignment Added.\nGood Luck in your "+title+" Assignment!", Toast.LENGTH_SHORT);
						 toast3.show(); 
						 break;
					}		    
			   					
		case R.id.find: // When find button is pressed
			try // try and get data after entering a specific rowID
			{
				String s = rowID.getText().toString(); // Convert whats in Editext into long type
				long l = Long.parseLong(s);
				DBManager myDB2 = new DBManager(this);
				myDB2.open();
				String rTitle = myDB2.getTitle(l);
				String rModule = myDB2.getModule(l);				
				String rDueDate = myDB2.getDueDate(l) ;
				myDB2.close();
				
				assignTitle.setText(rTitle); // set EditTexts to the values from our functions results if found
				assignModule.setText(rModule);				
				assignDueDate.setText(rDueDate);
				
				if(rTitle.isEmpty() && rModule.isEmpty()  && rDueDate.isEmpty()) // If no data returned from any fields display toast
				{
					Toast toast4 = Toast.makeText(this, "No Data Found.", Toast.LENGTH_SHORT);
					toast4.show();
					break;
				}
				else // Otherwise Data is found, alert the user
				{
					Toast toast5 = Toast.makeText(this, "" +rTitle+ " found.", Toast.LENGTH_SHORT);
					toast5.show();
					break;
				}
				
			}
			catch(NumberFormatException e) // When Nothing was returned to the EditTexts Alert User
			{				
				Toast toast6 = Toast.makeText(this, "Please Search again.", Toast.LENGTH_SHORT);
				toast6.show();
				break;
			}
		case R.id.view:                     // When View button pressed, Switch to View Activity
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
				myDB.updateAssignments(l, eTitle, eModule, eDueDate);	
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
				assignTitle.setText(""); // Set EditTexts to appear blank when data is entered
			    assignModule.setText("");						   
			    assignDueDate.setText("");
			    rowID.setText("");
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

	

