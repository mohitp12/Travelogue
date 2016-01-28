package com.example.travelogue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class DBConnection 
{
	public static ArrayList<Model> getAllPlace(String cityname)
	{
		
		StringBuilder respoceData=null;
		ArrayList<Model> model = new ArrayList<Model>();
		try
		{	
			String json = "{\"cityName\":\""+cityname+"\"}";
			StringEntity entity = new StringEntity(json);
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httpPost = new HttpPost("http://192.168.0.15/travel11/Service1.svc/list");
		    httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(entity);
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity responceEntity = response.getEntity();
		    
		    InputStream is = responceEntity.getContent();

		    BufferedReader reader = new BufferedReader(new InputStreamReader(is), 8);
		    respoceData = new StringBuilder();
		    String line = null;
		   
		    while ((line = reader.readLine()) != null)
		    {
		    	respoceData.append(line + "\n");
		    }
		    
		    Log.i("Responce",respoceData.toString());
		    is.close();    
		    
		    JSONObject j = new JSONObject(respoceData.toString());	    
		    JSONArray jData = j.getJSONArray("getallplaceResult");
		    for(int i = 0 ;i< jData.length() ;i++)
		    {
		    	JSONObject data = jData.getJSONObject(i);
		    	String plname = data.getString("CPlace");
		    	Model md = new Model();
		    	md.setPlName(plname);
		    	model.add(md);
		    }
		    return model;
		}
		catch(Exception e)
		{
			 Log.i("Exception",e.toString());
			return null;
		}
		
	}
	
	public static ArrayList<Model> getInfo(String placename)
	{
		StringBuilder respoceData=null;
		ArrayList<Model> model = new ArrayList<Model>();
		
		try
		{
			String intro=null;
			String latitude=null;
			String longitude=null;
			String json = "{\"placeName\":\""+placename+"\"}";
			StringEntity entity = new StringEntity(json);
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httpPost = new HttpPost("http://192.168.0.15/travel11/Service1.svc/info");
		    httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(entity);
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity responceEntity = response.getEntity();
		    
		    InputStream is = responceEntity.getContent();

		    BufferedReader reader = new BufferedReader(new InputStreamReader(is), 8);
		    respoceData = new StringBuilder();
		    String line = null;
		
		    while ((line = reader.readLine()) != null) 
		    {
		    	respoceData.append(line + "\n");
		    }
		    
		    Log.i("Responce",respoceData.toString());
		    is.close();    
		   
		    JSONObject j = new JSONObject(respoceData.toString());
			JSONArray jData = j.getJSONArray("getinfoResult");
			      
			   
			   for(int i = 0 ;i< jData.length() ;i++)
			   {
			    	JSONObject data = jData.getJSONObject(i);
			    	intro = data.getString("intro");
			    	latitude=data.getString("lat");
			    	longitude=data.getString("longi");
			    	Model md = new Model();
			    	md.setintro(intro);
			    	md.setlat(latitude);
			    	md.setlong(longitude);
			    	model.add(md);
			   }
			    return model;

		}
		catch(Exception e)
		{
			Log.i("Exception",e.toString());
			return null;
		}
	
	}
	
	public static ArrayList<Model> getBusDetail(String source,String destination)
	{
		StringBuilder respoceData=null;
		ArrayList<Model> model = new ArrayList<Model>();
		try
		{	
			String json = "{\"source\":\""+source+"\",\"destination\":\""+destination+"\"}";
			StringEntity entity = new StringEntity(json);
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httpPost = new HttpPost("http://192.168.0.15/travel11/Service1.svc/bus");
		    httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(entity);
            HttpResponse response = httpclient.execute(httpPost);   //Bad request error HTTP://400
            HttpEntity responceEntity = response.getEntity();
		    
		    InputStream is = responceEntity.getContent();

		    BufferedReader reader = new BufferedReader(new InputStreamReader(is), 8);
		    respoceData = new StringBuilder();
		    String line = null;
		   
		    while ((line = reader.readLine()) != null)
		    {
		    	respoceData.append(line + "\n");
		    }
		    
		    Log.i("Responce",respoceData.toString());
		    is.close();    
		    
		    JSONObject j = new JSONObject(respoceData.toString());	    
		    JSONArray jData = j.getJSONArray("getbusdetailResult");
		    for(int i = 0 ;i< jData.length() ;i++)
		    {
		    	JSONObject data = jData.getJSONObject(i);
		    	String Source = data.getString("source");
		    	String Via = data.getString("via");
		    	String Destination = data.getString("destination");
		    	String Time=data.getString("time");
		    	Model md = new Model();
		    	md.setSource(Source);
		    	md.setVia(Via);
		    	md.setDestination(Destination);
		    	md.setTime(Time);
		    	model.add(md);
		    }
		    return model;
		}
		catch(Exception e)
		{
			 Log.i("Exception",e.toString());
			return null;
		}
		
	}
}
