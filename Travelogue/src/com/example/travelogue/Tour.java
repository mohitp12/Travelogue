package com.example.travelogue;

import java.util.ArrayList;

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

public class Tour extends Activity
{
	ListView list;
	ArrayList<Model> model = new ArrayList<Model>();
	String district;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour);
		
		Intent it = getIntent();
		district = it.getStringExtra("firstPlace");
		
		model=(ArrayList<Model>) getIntent().getSerializableExtra("list");
		//ListViewCustomAdapter listcustom=new ListViewCustomAdapter(this,model);
		
		//DBConnection objDBConnection = new DBConnection();
		ArrayList<Model> placeList =  DBConnection.getAllPlace(district);
			
		ArrayList<String> places = new ArrayList<String>();
		
		for(int i=0 ;i<placeList.size();i++)
		{
			Model m = placeList.get(i);
			places.add(m.getPlName());
		}
		final ListView lv=(ListView)findViewById(R.id.listView1);
		ArrayAdapter<String> adt = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places);
		lv.setAdapter(adt);
		
		lv.setOnItemClickListener(new OnItemClickListener() 
		{
	        public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
	        {

	                // selected item	
	                String selected = lv.getItemAtPosition(position).toString();
	                Toast toast=Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT);
	                toast.show();
	                
	               
	                Intent i=new Intent(Tour.this,PlaceDetail.class);
	                i.putExtra("header",selected);
	                i.putExtra("district", district);
	                startActivity(i);
	                
	            }
	          });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tour, menu);
		return true;
	}

}
