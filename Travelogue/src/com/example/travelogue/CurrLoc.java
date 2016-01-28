package com.example.travelogue;

import java.util.List;
import java.util.Locale;
import org.w3c.dom.Document;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CurrLoc extends Activity 
{
    
    // GPSTracker class
    GPSTracker gps;
    double latitude,longitude;
    private GoogleMap googleMap;
    boolean previousMark = false;
  
  Document doc = null;
	LatLng startCoordinate;
	LatLng endCoordinate;
	Marker preMarker = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_curr_loc);
		
               
                // create class object
                gps = new GPSTracker(CurrLoc.this);
 
                // check if GPS enabled    
                if(gps.canGetLocation())
                {     
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    
                    Geocoder geocoder;
                    List<Address> addresses;
                   // geocoder= new google.maps.Geocoder();
                    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try
                    {
                    	addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    	addresses.get(0).getAddressLine(0);
                    	addresses.get(0).getAddressLine(1);
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
      	      initilizeMap() ; 
    }
        
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.curr_loc, menu);
		return true;
	}

	
	public void initilizeMap() 
	{
		 if (googleMap == null)
		 {
			 try
			 {
				 MapFragment mf = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
				 googleMap = mf.getMap();
				 googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
				 googleMap.setMyLocationEnabled(true);
				 Criteria criteria = new Criteria();
				 final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				 String provider = locationManager.getBestProvider(criteria, false);
				 Location location = locationManager.getLastKnownLocation(provider);
				 startCoordinate = new LatLng(location.getLatitude(),location.getLongitude());
				 CameraUpdate center=CameraUpdateFactory.newLatLng(startCoordinate);
				 CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
				 googleMap.moveCamera(center);
				 googleMap.animateCamera(zoom);
				 preMarker = googleMap.addMarker(new MarkerOptions().position(startCoordinate).title("Current Location"));
			 }
			 catch(Exception e)
			 {
				 Log.i("MAPS", e.toString());
			 }
		            
	            if(endCoordinate==null)
	            {
	            	endCoordinate = new LatLng(latitude, longitude);
	            }
	           
	            
	            if (googleMap == null) 
	            {
	                Toast.makeText(getApplicationContext(),"Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
	            }
	        }
	 }
}
