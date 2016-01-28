package com.example.travelogue;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceDetail extends Activity 
{
	String lat,longi,h,district;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_detail);
		
		TextView header;
		TextView body=null;
		ImageView img =(ImageView) findViewById(R.id.imageView1);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
	        h = extras.getString("header"); //this worked
	        district = extras.getString("district");    
		}
		//h=i.getStringExtra("header");
//		header=(TextView)findViewById(R.id.header);
		body=(TextView)findViewById(R.id.body);
//		header.setText(h);

		//DBConnection db=new DBConnection();
		ArrayList<Model> info = DBConnection.getInfo(h);
		String intro=null,latitude=null,longitude=null;
		
		for(int j=0;j<info.size();j++)
		{
			Model m = info.get(j);
			intro=m.getintro();
			latitude=m.getlat();
			longitude=m.getlong();
		}
		
		body.setText(intro);
		lat=latitude;
		longi=longitude;
		
		String temp=h.replaceAll("\\s", "");
	
		try
		{
			Bitmap bitmap = DownloadImage("http://192.168.0.15/travel11/"+temp+".jpg");
			img.setImageBitmap(bitmap);
		 }
		catch(Exception e)
			{
				Log.i("Exception",e.toString());
			}
	}

	 	private InputStream OpenHttpConnection(String urlString)
			    throws IOException
			    {
			        InputStream in = null;
			        int response = -1;
			                 
			        URL url = new URL(urlString);
			        URLConnection conn = url.openConnection();
			                   
			        if (!(conn instanceof HttpURLConnection))                    
			            throw new IOException("Not an HTTP connection");
			          
			        try
			        {
			            HttpURLConnection httpConn = (HttpURLConnection) conn;
			            httpConn.setAllowUserInteraction(false);
			            httpConn.setInstanceFollowRedirects(true);
			            httpConn.setRequestMethod("GET");
			            httpConn.connect();
			  
			            response = httpConn.getResponseCode();                
			            if (response == HttpURLConnection.HTTP_OK) 
			            {
			                in = httpConn.getInputStream();                                
			            }                    
			        }
			        catch (Exception ex)
			        {
			            throw new IOException("Error connecting");           
			        }
			        return in;    
			    }
			    
	 		private Bitmap DownloadImage(String URL)
			    {       
			        Bitmap bitmap = null;
			        InputStream in = null;       
			        try {
			            in = OpenHttpConnection(URL);
			            bitmap = BitmapFactory.decodeStream(in);
			            in.close();
			        } catch (IOException e1) {
			            // TODO Auto-generated catch block
			            e1.printStackTrace();
			        }
			        return bitmap;               
			    }
			

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.place_detail, menu);
		return true;
	}

	public void capture(View v)
	{
		try
		{
			startActivity(new Intent(PlaceDetail.this, Capture.class));
		}
		catch(Exception e)
		{
			Log.i("FACEBOOK", e.toString());
		}
	}

	@SuppressWarnings("unused")
	public void getWeather(View v)
	{	
		Intent in=getIntent();
		String h=in.getStringExtra("header");
		Intent i=new Intent(PlaceDetail.this,WeatherInfo.class);
		i.putExtra("district", district);
		startActivity(i);
	}
	
	public void getRoutes(View v)
	{
		Intent i=new Intent(PlaceDetail.this,Routes.class);
		i.putExtra("Latitude",lat);
		i.putExtra("Longitude", longi);
		i.putExtra("EndPlace",h);
		startActivity(i);
	}
	
	public void getTransport(View v)
	{
		Intent i=new Intent(PlaceDetail.this,Transport.class);
		i.putExtra("district", district);
		startActivity(i);
		/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final CharSequence train = getResources().getString(R.string.action_train);
		final CharSequence bus = getResources().getString(R.string.action_bus);
		builder.setCancelable(true).setItems(new CharSequence[] {train, bus}, new DialogInterface.OnClickListener() {
			@Override
               public void onClick(DialogInterface dialogInterface, int i) 
			{
                   if (i == TRAIN) 
                   {
                       startTrainActivity();
                   } 
                   else if (i == BUS) 
                   {
                	   startBusActivity();
                   }
			}
			
		});	
		builder.show();*/
	}
	
	
}

