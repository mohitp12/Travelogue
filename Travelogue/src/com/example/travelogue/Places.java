package com.example.travelogue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Places extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ListView list;
				
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_places);
		list = (ListView) findViewById(R.id.Placelist);
		final String districts[] =getResources().getStringArray(R.array.districts);
		ArrayAdapter<String> adp=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, districts);
		list.setAdapter(adp);

		list.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {

	                // selected item
	        	
	                String selected = districts[position];
	                Toast toast=Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT);
	                toast.show();
	                	               
	                Intent i=new Intent(Places.this,Tour.class);
	                i.putExtra("firstPlace",selected);
	                startActivity(i);
	            }
	          });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.places, menu);
		return true;
	} 

}
