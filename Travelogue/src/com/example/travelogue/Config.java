package com.example.travelogue;

import android.content.Context;
import android.content.Intent;

public final class Config 
{
	static final String SENDER_ID = "597802302750";
	static final String DISPLAY_MESSAGE_ACTION = "com.example.pushandroidaman.DISPLAY_MESSAGE";
	
	public static void DisplayMessage(Context context,String message)
	{
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra("Message", message);
		context.sendBroadcast(intent);
	}
}
