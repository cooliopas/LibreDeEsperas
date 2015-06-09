package com.uxor.turnos.util;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.PowerManager;
import android.util.Log;

import com.uxor.turnos.R;

public class Turnos extends Application{

    private static Context context;
    private static PowerManager.WakeLock wakeLock;
	public static final String PROPERTY_USER_TYPE = "userType";
	public static final String PROPERTY_SENDER_ID = "gcmSenderId";
	
	public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_USER_ID = "user_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String PROPERTY_EXPIRATION_TIME = "onServerExpirationTimeMs";
    public static final String PROPERTY_USER = "user";
    public static final long EXPIRATION_TIME_MS = 1000 * 3600 * 24 * 7;
	public static final String PROPERTY_USER_TOWN_ID = "userTownId";
	public static final String PROPERTY_USER_EMAIL = "userEmail";
	
	public static final String TURN_STATUS_HAB = "HAB";
	public static final String TURN_STATUS_SIN = "SIN";
	
	public static final int RESULT_CONFIGURATION_OK = 1;
	
	public static final String PROPERTY_MODE_UPDATE_TURNS_NOTIFICATIONS = "modeUpdateTurnsNotifications";
	public static final String PROPERTY_MODE_UPDATE_SERVER_NOTIFICATIONS = "modeUpdateServerNotifications";
	
	public static final String NOTIFICACION_TYPE_TTN = "TTN"; //Turn Type Notification
	public static final String NOTIFICACION_TYPE_STN = "STN"; //Server/Servicio Type Notification
	
	public static final String NOTIFICACION_STN_ERROR = "ERROR"; //Tipo de notificacion para Errores
	public static final String NOTIFICACION_TYPE_WARNING = "WARNING"; //Tipo de notificacion para Warnings
	public static final String NOTIFICACION_TYPE_INFO = "INFO"; //Tipo de notificacion para Información

	

    public Turnos(){
    	Log.i("main", "Constructor fired");
    }
    
    
    public void onCreate(){
        super.onCreate();
        Turnos.context = getApplicationContext();
        
    }

    public static Context getAppContext() {
        return Turnos.context;
    }
    
  //Function to display simple Alert Dialog
    public static void showAlertDialog(Context context, String title, String message,
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
    
    
    public static int getAppVersion(Context context){
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
    
    public static void acquireWakeLock(Context context) {
        if (wakeLock != null) wakeLock.release();
 
        PowerManager pm = (PowerManager) 
                          context.getSystemService(Context.POWER_SERVICE);
         
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "WakeLock");
         
        wakeLock.acquire();
    }
 
    public static void releaseWakeLock() {
        if (wakeLock != null) wakeLock.release(); wakeLock = null;
    }
    
    
    
 
}
