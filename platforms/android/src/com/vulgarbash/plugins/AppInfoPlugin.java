package com.vulgarbash.plugins;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class AppInfoPlugin extends CordovaPlugin {

    public final String ACTION_GET_VERSION = "GetVersion";
    public final String ACTION_GET_NAME = "GetName";

    @Override
    public boolean execute(String action, JSONArray args,
        CallbackContext callbackContext) throws JSONException {
        PackageManager packageManager = this.cordova.getActivity().getPackageManager();
        ApplicationInfo ai;
        CharSequence al;

        if(action.equals(ACTION_GET_VERSION)) {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0);
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK,  packageInfo.versionName));
            }
            catch (NameNotFoundException nnfe) {
                Log.e("AppInfoPlugin", "Errpr occurred calling plugin: " + nnfe.getMessage());
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
                return false;
            }
        } else if(action.equals(ACTION_GET_NAME)) {
            try {
                ai = packageManager.getApplicationInfo(this.cordova.getActivity().getPackageName(), 0);
                al = packageManager.getApplicationLabel(ai);
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, (String) al));
            }
            catch (NameNotFoundException nnfe) {
                Log.e("AppInfoPlugin", "Errpr occurred calling plugin: " + nnfe.getMessage());
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
                return false;
            }
        }
        callbackContext.success();
        return true;
    }
}