package com.example.travelogue;

import java.util.List;
import java.util.Locale;

import model.Weather;
import model.WeatherForecast;

import org.json.JSONException;

import adapter.DailyForecastPageAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherInfo extends FragmentActivity 
{
	private TextView cityText;
	private TextView condDescr;
	private TextView temp;
	private TextView press;
	private TextView windSpeed;
	private TextView windDeg;
	private TextView unitTemp;

	private TextView hum;
	private ImageView imgView;

	private static String forecastDaysNum = "4";
	private ViewPager pager;

	GPSTracker gps;
	double latitude,longitude;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_info);
		
		gps = new GPSTracker(WeatherInfo.this);
		String currcity=null;
		String country="India";
		
		Intent i=getIntent();
		currcity=i.getStringExtra("district");
		
		if(currcity==null)
		{
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
					String address = addresses.get(0).getAddressLine(0);
					currcity = addresses.get(0).getAddressLine(1);
					country = addresses.get(0).getAddressLine(2);
					
					 Toast toast=Toast.makeText(getApplicationContext(), "\nAddress:"+address +
			        			"\nCity" +currcity+ "\nCountry" +country, Toast.LENGTH_SHORT);
		              toast.show();
					/*tv1.setText("Your Location is - \nLatitude: " + latitude + "\nLongitude: " + longitude + "\nAddress:"+address +
        			"\nCity" +city+ "\nCountry" +country);*/
				}
				catch(Exception e)
				{
					//tv1.setText("Your Location is - \nLatitude: " + latitude + "\nLongitude: " + longitude+ "\n Address is Unavailable");
					Log.i("MAPS", e.toString());
				}
			}
		}
		String city =currcity+","+country;
		if(currcity==null)
		{
			city ="Gandhinagar"+","+country;
		}
		String lang = "en";
		
		cityText = (TextView) findViewById(R.id.cityText);
		temp = (TextView) findViewById(R.id.temp);
		unitTemp = (TextView) findViewById(R.id.unittemp);
		unitTemp.setText("°C");
		condDescr = (TextView) findViewById(R.id.skydesc);

		pager = (ViewPager) findViewById(R.id.pager);
		imgView = (ImageView) findViewById(R.id.condIcon);
		
		//condDescr = (TextView) findViewById(R.id.condDescr);
		
		hum = (TextView) findViewById(R.id.hum);
		press = (TextView) findViewById(R.id.press);
		windSpeed = (TextView) findViewById(R.id.windSpeed);
		windDeg = (TextView) findViewById(R.id.windDeg);
		
		
		JSONWeatherTask task = new JSONWeatherTask();
		task.execute(new String[]{city,lang});

		JSONForecastWeatherTask task1 = new JSONForecastWeatherTask();
		task1.execute(new String[]{city,lang, forecastDaysNum});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	private class JSONWeatherTask extends AsyncTask<String, Void, Weather> 
	{

		@Override
		protected Weather doInBackground(String... params) 
		{
			Weather weather = new Weather();
			String data = ( (new WeatherHttpClient()).getWeatherData(params[0], params[1]));

			try 
			{
				weather = JSONWeatherParser.getWeather(data);
				System.out.println("Weather ["+weather+"]");
				// Let's retrieve the icon
				weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));
			} 
			catch (JSONException e) 
			{				
				e.printStackTrace();
			}
			return weather;

		}


		@Override
		protected void onPostExecute(Weather weather)
		{			
			super.onPostExecute(weather);

			if (weather.iconData != null && weather.iconData.length > 0) 
			{
				Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length); 
				imgView.setImageBitmap(img);
			}

			cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
			temp.setText("" + Math.round((weather.temperature.getTemp() - 275.15)));
			condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");		
			//temp.setText("" + Math.round((weather.temperature.getTemp() - 275.15)) + "°C");
			hum.setText("Humid:" + weather.currentCondition.getHumidity() + "%");
			press.setText("Pres.:" + weather.currentCondition.getPressure() + " hPa");
			windSpeed.setText("Wind:" + weather.wind.getSpeed() + " mps");
			windDeg.setText("," + weather.wind.getDeg() + "°");
				
		}

  }


	private class JSONForecastWeatherTask extends AsyncTask<String, Void, WeatherForecast> {

		@Override
		protected WeatherForecast doInBackground(String... params) {

			String data = ( (new WeatherHttpClient()).getForecastWeatherData(params[0], params[1], params[2]));
			WeatherForecast forecast = new WeatherForecast();
			try 
			{
				forecast = JSONWeatherParser.getForecastWeather(data);
				System.out.println("Weather ["+forecast+"]");
			}
			catch (JSONException e) 
			{				
				e.printStackTrace();
			}
			return forecast;

	}


	@Override
		protected void onPostExecute(WeatherForecast forecastWeather) {			
			super.onPostExecute(forecastWeather);

			DailyForecastPageAdapter adapter = new DailyForecastPageAdapter(Integer.parseInt(forecastDaysNum), getSupportFragmentManager(), forecastWeather);

			pager.setAdapter(adapter);
		}
  }
}