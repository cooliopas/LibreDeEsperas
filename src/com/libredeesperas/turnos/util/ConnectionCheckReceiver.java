package com.uxor.turnos.util;

import com.uxor.turnos.R;
import com.uxor.turnos.presenter.helper.ApplicationHelper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

public class ConnectionCheckReceiver extends BroadcastReceiver  {
	
	private static Activity activity;
		
	public static void setActivity(Activity activity) {
		ConnectionCheckReceiver.activity=activity;
	}
	
	public static Activity getActivity() {
		return ConnectionCheckReceiver.activity;
	}

    @Override
    public void onReceive(final Context context, final Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean appIsActive = prefs.getBoolean("active", Boolean.FALSE);
        if (appIsActive){
	    	try
	    	{
	            if (!NetworkUtil.isConnectionAvailable(context))
	            {
	    			ApplicationHelper helper = new ApplicationHelper();
	    	        helper.showAlertDialog(activity, activity.getResources().getString(R.string.alertNoConnectionTitle), activity.getResources().getString(R.string.alertNoConnectionMessage),false);
	            }
	            else
	            {
	                Toast.makeText(activity,  activity.getResources().getString(R.string.restoringConnection), Toast.LENGTH_SHORT).show();
	            }
	            
	            //Actualizo la Action Bar
	    		ActivityCompat.invalidateOptionsMenu(activity);  
	    	
		    } catch (ActivityNotFoundException activityException) {
		        Log.e("ConnectionCheckReceiver error", "Call failed");
		    }
        }
      	
    }
	

}
