 /* ViewAssignments
 * The user can then click on each
 * item in the list of Modules which brings up a dialog which displays the title of the assignment
 * and its due date. 
 * This Dialog Has 2 buttons. Complete and Close. User can press Complete which removes the
 * assignment from the list. They can click close to resume browsing their list. If User
 * presses complete, they are greeted with a friendly congratulations message on completing
 * their project, which I believe is important to keep students motivated and coming back
 * to use the app. Once finished browsing, the user can hit the one button that is under the list
 * 'Manage Assignments'.
*/

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
		  
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.view_assignments);
	    manageAssignments = (Button) findViewById(R.id.manageAssignments);
	    manageAssignments.setOnClickListener(this);
	    
		myDB.open();
		// Pass in modules found in Module Column in Assignments table.
		String[] assignments = myDB.getModuleForList() ; 
		// Populates each row according to the string array assignments
		setListAdapter(new ArrayAdapter<String>(this, R.layout.row, R.id.assignments, assignments));		
		myDB.close();
	}
	
	  protected void onListItemClick(ListView l, View v, int position, long id)
	    {	
		  // Returns which row is selected and converts its' position into a string
		  final String selection = l.getItemAtPosition(position).toString();		  
		  globalSelection = selection ; // made for showToast method below
		  myDB.open();	
		  String data = null ;		  
		  data = myDB.getDataByModule(selection);
		  //Creates a dialog for when a row is pressed
		  AlertDialog.Builder builder = new AlertDialog.Builder(this);
		  builder.setTitle(selection).setMessage(myDB.getDataByModule(selection)).setCancelable(false).
		  setNegativeButton("No", new DialogInterface.OnClickListener(){
			   public void onClick(DialogInterface dialog, int id)
			     {
				   Log.i("TEST", "Close Clicked!");
			    	 // Closes Dialog by default
			     }
			  })
		  .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
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

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.manageAssignments: // When button is pressed, move to the MainActivity class
			System.out.println("Manage Assignments");
			startActivity(new Intent(ViewAssignments.this, MainActivity.class));
		    break;
		}		
	}	
	public void showToast()
	{
		Toast toast14 = Toast.makeText(this, "Congratulations on completing your " +globalSelection+ " assignment!", Toast.LENGTH_SHORT);
		toast14.show();
	}
}