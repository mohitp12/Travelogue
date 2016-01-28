package com.example.travelogue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class Bus extends Activity {
	GPSTracker gps;
	String mSource,mDestination,destination=null,source=null;
    double latitude,longitude;       					
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus);
		
		Intent it = getIntent();
		destination = it.getStringExtra("destination");
		
		Bundle b=getIntent().getExtras();
		mSource=b.getString("mSource");
		mDestination=b.getString("mDestination");
		if(mSource != null && mDestination!=null)
		{
			ArrayList<Model> info = DBConnection.getBusDetail(mSource,mDestination);
			ArrayList<String> src = new ArrayList<String>();
			ArrayList<String> via = new ArrayList<String>();
			ArrayList<String> destin = new ArrayList<String>();
			ArrayList<String> time =new ArrayList<String>();
			for(int j=0;j<info.size();j++)
			{
				Model m = info.get(j);
				src.add(m.getSource());
				destin.add(m.getDestination());
				via.add(m.getVia());
				time.add(m.getTime());
			}
			
			ListView lv=(ListView)findViewById(R.id.listView1);
			BusListCustomAdapter listcustom=new BusListCustomAdapter(this,src,via,destin,time);
			lv.setAdapter(listcustom);
		}
		else
		{
        gps = new GPSTracker(Bus.this);
        String city = null;
        // check if GPS enabled    
        if(gps.canGetLocation())
        {     
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try
            {
            	addresses = geocoder.getFromLocation(latitude, longitude, 1);
            	addresses.get(0).getAddressLine(0);
            	city = addresses.get(0).getAddressLine(1);
            	addresses.get(0).getAddressLine(2);
            }
            catch(Exception e)
            {
            	//tv1.setText("Your Location is - \nLatitude: " + latitude + "\nLongitude: " + longitude+ "\n Address is Unavailable");
            	Log.i("MAPS", e.toString());
            }
            
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();   
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
         
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);

        if(city!=null && city.contains(","))
        {
        	source= city.substring(0, city.indexOf(",")); 
        }
        if(source==null)
        {
        	source="Gandhinagar";
        }
        ArrayList<Model> info = DBConnection.getBusDetail(source,destination);
		ArrayList<String> src = new ArrayList<String>();
		ArrayList<String> via = new ArrayList<String>();
		ArrayList<String> destin = new ArrayList<String>();
		ArrayList<String> time =new ArrayList<String>();
		for(int j=0;j<info.size();j++)
		{
			Model m = info.get(j);
			src.add(m.getSource());
			destin.add(m.getDestination());
			via.add(m.getVia());
			time.add(m.getTime());
		}
		
		ListView lv=(ListView)findViewById(R.id.listView1);
		BusListCustomAdapter listcustom=new BusListCustomAdapter(this,src,via,destin,time);
		lv.setAdapter(listcustom);
	}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bus, menu);
		return true;
	}

}
