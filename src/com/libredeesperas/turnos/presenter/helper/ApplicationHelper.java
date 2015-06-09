package com.uxor.turnos.presenter.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.uxor.turnos.R;
import com.uxor.turnos.util.Turnos;


public class ApplicationHelper implements IApplicationHelper{
	
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	private static final ApplicationHelper instance = new ApplicationHelper();
	
	public ApplicationHelper(){		
	}
	
	public static ApplicationHelper getInstance(){
        return instance;
    }

	@Override
	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		 AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		  
         // Set Dialog Title
         alertDialog.setTitle(title);
  
         // Set Dialog Message
         alertDialog.setMessage(message);
  
         if(status != null)
             // Set alert dialog icon
             alertDialog.setIcon((status) ? R.drawable.ic_action_good : R.drawable.ic_action_error);
  
         // Set OK Button
         alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int which) {
                  
             }
         });
  
         // Show Alert Message
         alertDialog.show();
		
	}

	@Override
	public int getAppVersion(Context context) {
		try
	    {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	 
	        return packageInfo.versionCode;
	    }
	    catch (NameNotFoundException e)
	    {
	        throw new RuntimeException("Error al obtener versión: " + e);
	    }
	}
	
	
	// Checking for all possible internet providers
    public Boolean isConnectingToInternet(){
         
        ConnectivityManager connectivity = 
                             (ConnectivityManager) Turnos.getAppContext().getSystemService(
                              Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return Boolean.TRUE;
                      }
  
          }
          
          try {
              Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
              int returnVal = p1.waitFor();
              boolean reachable = (returnVal==0);
              return reachable;
          } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
          
          return Boolean.FALSE;
    }
    
    public Boolean checkPlayServices(Activity view) {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(Turnos.getAppContext());
	    if (resultCode != ConnectionResult.SUCCESS)
	    {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
	        {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, view,
	                    Turnos.PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        }
	        else
	        {
	            Log.i(view.getClass().getName(), "Dispositivo no soportado.");
	        }
	        return Boolean.FALSE;
	    }
	    return Boolean.TRUE;
	}
    
    public Boolean checkInternet(){
    	
    	try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
	

}
