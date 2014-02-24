/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.phonegap.helloworld;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;

public class HelloWorld extends CordovaActivity
{

/*	@Override
	public void init()
	{
		CordovaWebView webView = new SWWebView(this);
		CordovaWebViewClient webViewClient;
		webViewClient = new SWWebViewClient(this, webView);
		this.init(webView, webViewClient, new CordovaChromeClient(this, webView));
	}*/

	private int getScale()
	{
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		Double val = new Double(width) / 800d;
		val = val * 100d;
		return val.intValue();
	}

	private void forceFixedViewport()
	{
		CordovaWebView myWebView = appView;
		WebSettings settings = myWebView.getSettings();

		settings.setLoadWithOverviewMode(false);
		// Activating viewport on Android 2.x will deactivate stretching
		if (true||android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
		{
			// Set default zoom to unzoom
			// It seems to glitch on Android 2.x, a white screen will appear after enabling this option
			// Not for me.. without it.. it doesn't scale right and zooms -joe f.
			settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
			// Force using viewport html statement, sadly it activates double tap to zoom
			settings.setUseWideViewPort(true);
		}
		// Try not to use default zoom (useful ?)
		settings.setSupportZoom(false);
		settings.setBuiltInZoomControls(false);
		// Set scale on devices that supports it
		/* 
		 * creates different problems for different browsers.. need consistency - do this with javascript if width doesnt match what we want.
		myWebView.setPadding(0, 0, 0, 0);

		int percentScale = getScale();
		Log.v("sw", "percentScale=" + percentScale);
		myWebView.setInitialScale(percentScale);*/

	}
	
	private void defaultFixedViewport()
	{
		CordovaWebView myWebView = appView;
		WebSettings settings = myWebView.getSettings();
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.init();
		super.loadUrl(Config.getStartUrl());
		//appView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
		//Choose between two methods
		//defaultFixedViewport();
		forceFixedViewport();
	}
}
