package com.example.travelogue;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class viewClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView v,String url)
	{
		v.loadUrl(url);
		return true;
	}
}
