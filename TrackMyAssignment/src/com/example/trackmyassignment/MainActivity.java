package com.example.trackmyassignment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener
{
	
	Button addAssign, viewAssign;
	EditText assignTitle, assignModule, assignDescr, assignDueDate ;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addAssign = (Button) findViewById(R.id.addAssign);
		viewAssign = (Button) findViewById(R.id.viewAssign);
		
		assignTitle = (EditText) findViewById(R.id.assignTitle);
		assignModule = (EditText) findViewById(R.id.assignModule);
		assignDescr = (EditText) findViewById(R.id.assignDescription);
		assignDueDate = (EditText) findViewById(R.id.assignDueDate);
		
		addAssign.setOnClickListener(this);
		viewAssign.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.addAssign:
			boolean success = true ;
			try
			{
			    String title = assignTitle.getText().toString();
			    String module = assignModule.getText().toString();
			    String description = assignDescr.getText().toString();
			    String duedate = assignDueDate.getText().toString();
			
			    DBManager addToMyDB = new DBManager(this);
			    addToMyDB.open();
			    addToMyDB.addAssignment(title, module, description, duedate);
			    addToMyDB.close();
			}
			catch (Exception e)
			{
				success = false ;
				String error = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Error");
				TextView tv = new TextView(this);
				tv.setText(error);
				d.setContentView(tv);
				d.show();
			}
			finally
			{
				if (success)
				{
					Dialog d = new Dialog(this);
					d.setTitle("Assignment Added.");
					TextView tv = new TextView(this);
					tv.setText("Success");
					d.setContentView(tv);
					d.show();					
				}
			}
		case R.id.viewAssign:
			Intent i = new Intent("com.example.trackmyassignment.ViewAssignments");
			startActivity(i);
			break;
	    }
		
	}
		

}
	

