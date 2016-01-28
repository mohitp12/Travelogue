package com.example.travelogue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class AddPlace extends Activity implements OnClickListener {

	EditText personsEmail, intro, placeName, placeDesc,lat,longi;
	String emailAdd, description, name, latitude,longitude;
	Button sendEmail,help;
	boolean click = true;
	LinearLayout mainLayout,layout;
	LayoutParams params;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_place);
		initializeVars();
		sendEmail.setOnClickListener(this);
	}

	private void initializeVars() 
	{
		// TODO Auto-generated method stub
		personsEmail = (EditText) findViewById(R.id.etEmail);
		placeName = (EditText) findViewById(R.id.placeName);
		placeDesc = (EditText) findViewById(R.id.placeDesc);
		sendEmail = (Button) findViewById(R.id.bSentEmail);
		longi=(EditText)findViewById(R.id.longitude);
		lat=(EditText)findViewById(R.id.latitude);
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String message;
		SendRequest();
		String emailaddress[] = { "travelogue79@gmail.com" };
		if(latitude !=null && longitude!=null)
		{
		message = "Well hello I"+ emailAdd
				+ " just wanted to add "
				+ name
				+ " as a new place in your application. "
				+ " Here is the description about the place: "
				+ '\n' + description
				+ "And it's geographic co-ordinates are"
				+ latitude +"&" +longitude
				+ '\n' + "Thank You!!!";
		}
		else
		{
		message = "Well hello I"+ emailAdd
					+ " just wanted to add "
					+ name
					+ " as a new place in your application. "
					+ " Here is the description about the place: "
					+ '\n' + description
					+ '\n' + "Thank You!!!";
			}
		
		Intent emailSend = new Intent(android.content.Intent.ACTION_SEND);
		emailSend.putExtra(android.content.Intent.EXTRA_EMAIL, emailaddress);
		emailSend.putExtra(android.content.Intent.EXTRA_SUBJECT, "Request for new place entry");
		emailSend.setType("plain/text");
		emailSend.putExtra(android.content.Intent.EXTRA_TEXT, message);
		startActivity(emailSend);
	}
	
	private void SendRequest() {
		// TODO Auto-generated method stub
		emailAdd = personsEmail.getText().toString();
		description = placeDesc.getText().toString();
		name = placeName.getText().toString();
		latitude=lat.getText().toString();
		longitude=longi.getText().toString();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_place, menu);
		return true;
	}
	
	public void help(View v)
	{
		initiatePopupWindow();
		
	}
	
	private PopupWindow pwindo;
	Button btnClosePopup=(Button)findViewById(R.id.closeBtn);
	
	private void initiatePopupWindow() {
		try {
		// We need to get the instance of the LayoutInflater
		LayoutInflater inflater = (LayoutInflater) AddPlace.this
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.screen_popup,
		(ViewGroup) findViewById(R.id.popup_element));
		
		pwindo = new PopupWindow(layout, 400, 570, true);
		pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
		TextView tv = (TextView)findViewById(R.id.txtView1);
		tv.setText("Hello,please fill this form in order to request for new place."+'\n'+
        		"1.Give your email id for any further contact"+'\n'+
        		"2.Give the name of place you want to add"+'\n'+
        		"3.Give a detailes description of place"+'\n'+
        		"4.Give the georaphic coordinates for the place"+'\n'+
        		"Your request will be verified and added to the appliaction." +
        		"Notification will be sent when the place is added successfully to the application");
		btnClosePopup = (Button) layout.findViewById(R.id.closeBtn);
		//btnClosePopup.setOnClickListener(cancel_button_click_listener);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		}

		private void ClosePop(View v) 
		{
				pwindo.dismiss();
		}

}
