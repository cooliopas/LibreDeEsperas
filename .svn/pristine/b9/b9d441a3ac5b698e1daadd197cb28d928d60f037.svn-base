package com.uxor.turnos.view;

import java.io.IOException;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.uxor.turnos.R;
import com.uxor.turnos.presenter.SplashPresenter;
import com.uxor.turnos.util.Turnos;

public class SplashScreenActivity extends Activity implements ISplashView {
	// Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 3000;
    private static final String TAG = "SplashScreenActivity";
  
    
    private GoogleCloudMessaging gcm;
    private ProgressDialog dialog;
    private SplashPresenter splashPresenter;
    private HashMap hmResult;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		//Obtengo el presenter asociado a la vista
		splashPresenter = new SplashPresenter(this);
		
		new Handler().postDelayed(new Runnable() {
			 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
            	
            	DisplayMetrics metrics = new DisplayMetrics();
        		getWindowManager().getDefaultDisplay().getMetrics(metrics);
        		switch(metrics.densityDpi){
        		     case DisplayMetrics.DENSITY_LOW:
        		    	 		Log.i(TAG, "Dpi:".concat(String.valueOf(metrics.densityDpi)));
        		                break;
        		     case DisplayMetrics.DENSITY_MEDIUM:
        		    	 		Log.i(TAG, "Dpi:".concat(String.valueOf(metrics.densityDpi)));
        		                 break;
        		     case DisplayMetrics.DENSITY_HIGH:
        		    	 		Log.i(TAG, "Dpi:".concat(String.valueOf(metrics.densityDpi)));
        		                 break;
        		     case DisplayMetrics.DENSITY_XHIGH:
        		    	 Log.i(TAG, "Dpi:".concat(String.valueOf(metrics.densityDpi)));
                         break;
        		     case DisplayMetrics.DENSITY_XXHIGH:
        		    	 Log.i(TAG, "Dpi:".concat(String.valueOf(metrics.densityDpi)));
                         break;
        		}
            	
            	 //Obtengo la SharePreferences
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                
            	//Recupero el id del usuario y el pushNotificationKey para verificar que el usuario este registrado
        	    Integer userId = prefs.getInt(Turnos.PROPERTY_USER_ID,-1);
        	    String pushNotificationKey = prefs.getString(Turnos.PROPERTY_REG_ID, "");
        	    
        	    
        	    //String pushNotificationKey = getRegistrationId(SplashScreenActivity.this);
        	    
        	    if (pushNotificationKey.equals("") || userId==-1){
        	    	
        	    	Intent i = new Intent(SplashScreenActivity.this, RegistrarGCMActivity.class);
//        	    	Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
        	    	startActivity(i);
        	            	    
        	    }else{
        	    	
        	    	if (splashPresenter.validateRegId(getContext()).equals("")){
        	    		
        	    		// Check if Internet Connection present
        	            if (splashPresenter.isConnectingToInternet()) {
        	            
	        	            //Chequemos si está instalado Google Play Services
	        	          if(splashPresenter.checkPlayServices(SplashScreenActivity.this)){
	        	    		registerBackground();
	        	          }else{
	        	        	  Log.e(TAG, "Error al verificar el servicio de Google Play Service.");
//	        	        	  splashPresenter.showAlertDialog(getContext(),
//		                                "Google Play Service",
//		                                "Error al verficiar el servicio de Google Play Service: no se encuentra instalado en el equipo", false);
	        	        	  
	        	        	//Finaliza la aplicación
//	        	        	  finish();
	        	          }
        	          
        	            }else{
        	            	Log.e(TAG, "Error en la conexión de Internet.");
//        	            	splashPresenter.showAlertDialog(getContext() ,
//	        	                      "Error en la conexión de Internet",
//	        	                      "Por favor, conecte internet para registrar el usuario en nuestros servidores", false);
	        	        	  
        	            	//Finaliza la aplicación
//        	            	finish();
        	            }
        	    	}
        	    	
        	    	Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
         	    	startActivity(i);
        	    }
        	    
                // close this activity
                finish();
            }
        }, SPLASH_SCREEN_DELAY);
		
	}
	
	
	/**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration id, app versionCode, and expiration time in the application's
     * shared preferences.
     */
    private void registerBackground(){
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params){
            	
            	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getContext());
                    }
                    String gcmSenderId = prefs.getString(Turnos.PROPERTY_SENDER_ID, "");
                    Integer userId = prefs.getInt(Turnos.PROPERTY_USER_ID, Integer.MIN_VALUE);
                    
                    //Registro el gcm code
                    String regId = gcm.register(gcmSenderId);
                    
                    msg = "Registro GCM actualizado, código:" + regId;

                    //Actualizo el gcm code en el servidor
                    Boolean result = splashPresenter.updateGcmCodeServer(getContext(), userId, regId);
                    
                    //Guardamos los datos del registro si fue registrado correctamente.
                    if(result){
                    	// Save the regid - no need to register again.
                    	splashPresenter.setRegistrationId(getContext(), regId);
                    }else{
                    	msg = "Error al actualizar el gcm code";
                    }
                    
                } catch (IOException ex) {
                    msg = "Error al actualizar el gcm code";
                }
                
                
                return msg;
            }
            
            
            protected void onPreExecute() {

            }

            @Override
            protected void onPostExecute(String msg) {
            	//Toast.makeText(SplashScreenActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        }.execute(null, null, null);
    }
	

	@Override
	public Context getContext() {
		return this;
	}


}
