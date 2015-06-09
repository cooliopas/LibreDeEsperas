package com.uxor.turnos.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.uxor.turnos.presenter.helper.ApplicationHelper;
import com.uxor.turnos.presenter.helper.IApplicationHelper;
import com.uxor.turnos.presenter.helper.ISplashHelper;
import com.uxor.turnos.presenter.helper.SplashHelper;
import com.uxor.turnos.util.Turnos;
import com.uxor.turnos.view.ISplashView;

public class SplashPresenter {

	private static final String TAG = "SplashPresenter";
	
	private ISplashView splashView;
	private ISplashHelper splashHelper;
	private IApplicationHelper applicationHelper;
	
	public SplashPresenter(){
	}
	
	public SplashPresenter(ISplashView view){
		this(view, SplashHelper.getInstance(), ApplicationHelper.getInstance());
	}
	
	public SplashPresenter(ISplashView view, ISplashHelper splashHelper, IApplicationHelper applicationHelper){
		this.splashView = view;
		this.splashHelper = splashHelper;
		this.applicationHelper = applicationHelper;
	}
	
	public ISplashView getSplashView() {
		return splashView;
	}
	
	
	public Boolean updateGcmCodeServer(Context context, Integer userId, String gcmCode) {
		return splashHelper.updateGcmCodeServer(context, userId, gcmCode);
	}
	
	public Boolean isConnectingToInternet(){
		return applicationHelper.isConnectingToInternet();
	}
	
	public Boolean checkPlayServices(Activity view) {
		return applicationHelper.checkPlayServices(view);
	}
	
	public void showAlertDialog(Context context, String title, String message,
			Boolean status){
		
		applicationHelper.showAlertDialog(context, title, message, status);
		
	}
	
	
	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	public String validateRegId(Context context) {
	    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    String registrationId = prefs.getString(Turnos.PROPERTY_REG_ID, "");
	    
	    
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing regID is not guaranteed to work with the new
	    // app version.
	    int registeredVersion = prefs.getInt(Turnos.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = Turnos.getAppVersion(context);
	    if (registeredVersion != currentVersion || isRegistrationExpired(context)) {
	        Log.i(TAG, "Registro gcm code expirado.");
	        return "";
	    }
	    
//	    return registrationId;
	    return "";
	}
	
	
	/**
     * Checks if the registration has expired.
     *
     * <p>To avoid the scenario where the device sends the registration to the
     * server but the server loses it, the app developer may choose to re-register
     * after REGISTRATION_EXPIRY_TIME_MS.
     *
     * @return true if the registration has expired.
     */
	public boolean isRegistrationExpired(Context context) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        // checks if the information is not stale
        long expirationTime = prefs.getLong(Turnos.PROPERTY_EXPIRATION_TIME, -1);
        return System.currentTimeMillis() > expirationTime;
    }
	
	
	/**
     * Stores the registration id, app versionCode, and expiration time in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration id
     */
	public void setRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    
	    int appVersion = ApplicationHelper.getInstance().getAppVersion(context);
	    
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(Turnos.PROPERTY_REG_ID, regId); //GCM Key
	    editor.putInt(Turnos.PROPERTY_APP_VERSION, appVersion); //Version de la aplicación
	    editor.putLong(Turnos.PROPERTY_EXPIRATION_TIME, System.currentTimeMillis() + Turnos.EXPIRATION_TIME_MS);  //Tiempo para verificar si el gcm key esta expirado
	    editor.commit();
	}

	
}
