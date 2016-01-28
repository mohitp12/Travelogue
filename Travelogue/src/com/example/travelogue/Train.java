package com.example.travelogue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class Train extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train);
		WebView wv=(WebView)findViewById(R.id.webView1);
		wv.setWebViewClient(new viewClient());
		wv.getSettings().setJavaScriptEnabled(true);
		wv.loadUrl("http://www.indianrail.gov.in/know_Station_Code.html");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.train, menu);
		return true;
	}

}
