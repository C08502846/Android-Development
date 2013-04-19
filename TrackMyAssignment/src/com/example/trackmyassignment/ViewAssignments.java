package com.example.trackmyassignment;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewAssignments extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_assignments);
		TextView tv = (TextView) findViewById(R.id.info);
		DBManager info = new DBManager(this);
		info.open();
		String data = info.getData();
		info.close();
		tv.setText(data);
	}

}
