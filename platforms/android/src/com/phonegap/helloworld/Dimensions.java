package com.phonegap.helloworld;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;


public class Dimensions extends CordovaPlugin {
	
    @Override
    public boolean execute(String action, JSONArray args,
        CallbackContext callbackContext) throws JSONException {

        
		int width = 0;
		int height = 0;
		Point size = new Point();
		WindowManager w = this.cordova.getActivity().getWindowManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			w.getDefaultDisplay().getSize(size);

			width = size.x;
			height = size.y;
		} else {
			Display d = w.getDefaultDisplay();
			width = d.getWidth();
			height = d.getHeight();
		}

		webView.sendJavascript("setDimensions(" + width + "," + height	+ ");");
		Log.e("Dimensions", width + "x" + height);
		
		
		
		
		
        callbackContext.success();
        return true;
    }

    
}