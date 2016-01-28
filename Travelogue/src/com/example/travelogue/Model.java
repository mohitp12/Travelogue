package com.example.travelogue;

import java.io.Serializable;

import android.graphics.Bitmap;

@SuppressWarnings("serial")
public class Model implements Serializable
{

	private String PName;
	private String CName;
	private String intro;
	private String lat;
	private String longi;
	private String Source;
	private String Via;
	private String Destination;
	private String Time;
	public Bitmap Image;
	public String getPlName() 
	{
		return PName;
	}
	public void setPlName(String pName) 
	{
		PName = pName;
	}
	
	public String getCPlace()
	{
		return CName;
	}
	public void setCPlace(String cName) 
	{
		CName = cName;
	}
	public String getintro()
	{
		return intro;
	}
	public void setintro(String Intro) 
	{
		intro = Intro;
	}

	public String getlat()
	{
		return lat;
	}
	public void setlat(String latitude) 
	{
		lat = latitude;
	}
	public String getlong()
	{
		return longi;
	}
	public void setlong(String longitude) 
	{
		longi = longitude;
	}
	public String getSource() 
	{
		return Source;
	}
	public void setSource(String source) 
	{
		Source = source;
	}
	public String getVia() 
	{
		return Via;
	}
	public void setVia(String via) 
	{
		Via = via;
	}
	public String getDestination() 
	{
		return Destination;
	}
	public void setDestination(String destination) 
	{
		Destination = destination;
	}
	public String getTime() 
	{
		return Time;
	}
	public void setTime(String time) 
	{
		Time = time;
	}
	
	public Bitmap getImage() {
	       return Image;
	    }

	    public void setImage(Bitmap ImageIn) {
	         Image = ImageIn;
	    }
}
