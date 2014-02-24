package com.phonegap.helloworld;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewClient;

import android.util.Log;
import android.webkit.WebView;

public class SWWebViewClient extends CordovaWebViewClient
{
	public HelloWorld	cordova;

	public SWWebViewClient(HelloWorld cordova, CordovaWebView view)
	{
		super(cordova, view);
		this.cordova = cordova;
	}

	public static int		DELAYZOOMOUT		= 250;
	public static int		CONSECUTIVEZOOMOUT	= 10;
	public static boolean	zoomingOut			= false;

	@Override
	public void onScaleChanged(final WebView view, float oldScale, float newScale)
	{
		if (newScale > oldScale && !zoomingOut)
		{
			zoomingOut = true;
			Log.v("SW", "Calling zoom out");
			view.zoomOut();
			view.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					Log.v("SW", "Calling delayed zoom out");
					for (int i = 0; i < CONSECUTIVEZOOMOUT; i++)
					{
						view.zoomOut();
					}
					zoomingOut = false;
				}
			}, DELAYZOOMOUT);
		}
	}
}
