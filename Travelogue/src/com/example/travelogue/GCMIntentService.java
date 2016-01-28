package com.example.travelogue;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService
{
	public  GCMIntentService()
	{
		super("<google application number>");
	}

	@Override
	protected void onError(Context arg0, String arg1) 
	{
		
		
	}

	@Override
	protected void onMessage(Context context, Intent arg1) 
	{
		String message = arg1.getExtras().getString("price");
		generateNotification(context, message);
	}

	@Override
	protected void onRegistered(Context arg0, String arg1) 
	{
		
		Log.i("uuid is ", ""+arg1);
//		SendRequest objSendRequest = new SendRequest();
//		int result = objSendRequest.sendData(arg1);
//		Log.i("Regester on local Server", ""+result);
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) 
	{
		// TODO Auto-generated method stub
		
	}

	 @SuppressWarnings("deprecation")
	private static void generateNotification(Context context, String message) 
	 {
	        int icon = R.drawable.icon;
	        long when = System.currentTimeMillis();
	      
	        NotificationManager notificationManager = (NotificationManager)
	                context.getSystemService(Context.NOTIFICATION_SERVICE);
	        
			Notification notification = new Notification(icon, message, when);
	        
	        String title = context.getString(R.string.app_name);
	        
	        Intent notificationIntent = new Intent(context, MainActivity.class);
	        // set intent so it does not start a new activity
	       
	        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
	                Intent.FLAG_ACTIVITY_SINGLE_TOP);
	       
	        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
	        
	        notification.setLatestEventInfo(context, title, message, intent);
	        
	        notification.flags |= Notification.FLAG_AUTO_CANCEL;
	        
	        // Play default notification sound
	        notification.defaults |= Notification.DEFAULT_SOUND;
	        
	        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
	        
	        // Vibrate if vibrate is enabled
//	        notification.defaults |= Notification.DEFAULT_VIBRATE;
	        notificationManager.notify(0, notification);      
	        
	       
	    }

}
