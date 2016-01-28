package com.example.travelogue;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.location.Address;
import android.location.Geocoder;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class PhotoHandler implements PictureCallback
{
	GPSTracker gps;
	double latitude,longitude;
    private final Context context;
	String city=null;
	
	public PhotoHandler(Context applicationContext)
	{
		// TODO Auto-generated constructor stub
		context = applicationContext;
		gps = new GPSTracker(this.context);
		
        // check if GPS enabled    
        if(gps.canGetLocation())
        {     
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            
            Geocoder geocoder;
            List<Address> addresses;
           // geocoder= new google.maps.Geocoder();
            geocoder = new Geocoder(this.context, Locale.getDefault());
            try
            {
            	addresses = geocoder.getFromLocation(23.2457, 72.4967, 1);
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
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) 
	{
		// TODO Auto-generated method stub
	
		File pictureFileDir = new File(Environment.getExternalStorageDirectory()+"/Travelogue");
		if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
			try{
			Toast.makeText(context, "Can't create directory to save image.",
					Toast.LENGTH_LONG).show();
			return;
			}
			catch(Exception e)
			{
				Log.i("photo", e.toString());
			}
		}

		
        
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
		String date = dateFormat.format(new Date());
		String photoFile=null;
		if(city!=null)
		{
			photoFile= city + date + ".jpg";
		}
		else
		{
			photoFile = date + ".jpg";
		}
		String filename = pictureFileDir.getPath() + File.separator + photoFile;
		File pictureFile = new File(filename);

		try 
		{
			FileOutputStream fos = new FileOutputStream(pictureFile);
			fos.write(data);
			fos.close();
			Toast.makeText(context, "New Image saved:" + photoFile,
					Toast.LENGTH_LONG).show();
		}
		catch (Exception error) 
		{
			Log.i("album", error.toString());
			Toast.makeText(context, "Image could not be saved.",Toast.LENGTH_LONG).show();
		}
	}
	
	@SuppressWarnings("unused")
	private File getDir() 
	{
		File sdDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		return new File(sdDir, "CameraAPIDemo");
	}
}
