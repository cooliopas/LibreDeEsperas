package com.uxor.turnos.presenter.helper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.uxor.turnos.domain.FinalUser;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.util.Turnos;
import com.uxor.turnos.util.Util;
import com.uxor.turnos.view.MainActivity;

public class GcmHelper implements IGcmHelper{
	
    private static final String TAG = "GcmHelper"; 
	
	private static final GcmHelper instance = new GcmHelper();
	
	public GcmHelper(){		
	}
	
	public static GcmHelper getInstance(){
        return instance;
    }
	
	
	// Checking for all possible internet providers
    public boolean isConnectingToInternet(){
         
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
                          return true;
                      }
  
          }
          return false;
    }
    
    public boolean checkPlayServices(Activity view) {
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
	        return false;
	    }
	    return true;
	}

	@Override
	public String getRegistrationId(Context context, String nombre){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
	    String registrationId = prefs.getString(Turnos.PROPERTY_REG_ID, "");
	 
	    if (registrationId.length() == 0){
	        Log.d(TAG, "Registro GCM no encontrado.");
	        return "";
	    }
	 
	    String registeredUser = prefs.getString(Turnos.PROPERTY_USER, "user");
	 
	    int registeredVersion = prefs.getInt(Turnos.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	 
	    long expirationTime = prefs.getLong(Turnos.PROPERTY_EXPIRATION_TIME, -1);
	 
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
	    String expirationDate = sdf.format(new Date(expirationTime));
	 
	    Log.d(TAG, "Registro GCM encontrado (usuario=" + registeredUser +
	    ", version=" + registeredVersion +
	    ", expira=" + expirationDate + ")");
	 
	    int currentVersion = Turnos.getAppVersion(context);
	 
	    if (registeredVersion != currentVersion){
	        Log.d(TAG, "Nueva versión de la aplicación.");
	        return "";
	    }else if (System.currentTimeMillis() > expirationTime){
	        Log.d(TAG, "Registro GCM expirado.");
	        return "";
	    }else if (!nombre.equals(registeredUser)){
	        Log.d(TAG, "Nuevo nombre de usuario.");
	        return "";
	    }
	 
	    return registrationId;
	}

	@Override
	public void saveRegistrationId(Context context, String user, String regId, Long townId, String email) {
//		 	SharedPreferences prefs = context.getSharedPreferences(
//		    		MainActivity.class.getSimpleName(), 
//		            Context.MODE_PRIVATE);
		 
		 
		 	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		    
		 	int appVersion = ApplicationHelper.getInstance().getAppVersion(context);
		    
		    SharedPreferences.Editor editor = prefs.edit();
		    editor.putString(Turnos.PROPERTY_USER, user);     //Nombre de usuario
		    editor.putString(Turnos.PROPERTY_REG_ID, regId); //GCM Key 
		    editor.putInt(Turnos.PROPERTY_APP_VERSION, appVersion); //Version de la aplicación
		    editor.putLong(Turnos.PROPERTY_EXPIRATION_TIME, System.currentTimeMillis() + Turnos.EXPIRATION_TIME_MS);  //Tiempo para verificar si el gcm key esta expirado
//		    editor.putLong(Turnos.PROPERTY_USER_TOWN_ID, townId); //Ciudad seleccionada por el usuario
		    editor.putString(Turnos.PROPERTY_USER_TOWN_ID, townId.toString()); //Ciudad seleccionada por el usuario
		    editor.putString(Turnos.PROPERTY_USER_EMAIL, email);  //Email de la cuenta principal del usuario
		    editor.putString(Turnos.PROPERTY_USER_TYPE, "ADD");   //Tipo de usuario
		    
		    
		    editor.commit();
		
	}

	@Override
	public HashMap registroServidor(Context context, String usuario, String regId, Long townId, String email) {
		HashMap hmResult = new HashMap();
		
//		SharedPreferences prefs = context.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    String userType = prefs.getString(Turnos.PROPERTY_USER_TYPE, "");
		
		
		final String NAMESPACE = Util.getPropertieValue(context, "config.properties", "gcmRegistration.webservice.namespace");
		final String URL = Util.getPropertieValue(context, "config.properties", "gcmRegistration.webservice.url");
		final String METHOD_NAME = Util.getPropertieValue(context, "config.properties", "gcmRegistration.webservice.methodName");
		final String SOAP_ACTION = Util.getPropertieValue(context, "config.properties", "gcmRegistration.webservice.soapAction");
		

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("usuario", usuario); 
		request.addProperty("pushNotificationKey", regId);
		request.addProperty("userType", userType);
		request.addProperty("cityID", townId.toString());
		request.addProperty("email", email);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);

		HttpTransportSE transporte = new HttpTransportSE(URL);
		
		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente. 
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));

		try{
			
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			
//			SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
//			String res = resultado_xml.toString();

			//Version con el webservice de Mariano
			SoapObject response = (SoapObject)envelope.getResponse();
			FinalUser finalUser = new FinalUser();
			if (response.getProperty(0)!=null){
				finalUser.setExceptionMessage(response.getProperty(0).toString());
			}
			if (response.getProperty(1)!=null){
				finalUser.setId(new BigDecimal(response.getProperty(1).toString()));
			}
			
			if(finalUser.getExceptionMessage()==null){
				Log.d(TAG, "Registrado en mi servidor.");
				
				//Guardo el id del usuario registrado en la sharepreferences
				SharedPreferences.Editor editor = prefs.edit();
				editor.putInt(Turnos.PROPERTY_USER_ID, finalUser.getId().intValue());
				editor.commit();
				
				hmResult.put("result", Boolean.TRUE);
				hmResult.put("msg", "El usuario fue registrado correctamente en el Servidor de Turnos");
				Log.d(TAG, "El usuario fue registrado correctamente en el Servidor de Turnos");
			}else{
				hmResult.put("result", Boolean.FALSE);
				hmResult.put("msg", finalUser.getExceptionMessage());
				Log.d(TAG, "Error al registrar el usuario en el Servidor de Turnos");
			}

//Version con el webservice inicial
//			SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
//			String res = resultado_xml.toString();
			
//			if(!res.equals("0")){
//				Log.d(TAG, "Registrado en mi servidor.");
//				
//				//Guardo el id del usuario registrado en la sharepreferences
//				SharedPreferences.Editor editor = prefs.edit();
//				editor.putInt(PROPERTY_USER_ID, Integer.valueOf(res));
//				editor.commit();
//				
//				hmResult.put("result", Boolean.TRUE);
//				hmResult.put("msg", "El usuario fue registrado correctamente en el Servidor de Turnos");
//				Log.d(TAG, "El usuario fue registrado correctamente en el Servidor de Turnos");
//				reg = true;
//			}
			
			
			
			
		}catch (Exception e){
			Log.d(TAG, "Error al registrar el usuario en el Servidor de Turnos: " + e.getMessage());
			hmResult.put("result", Boolean.FALSE);
			hmResult.put("msg", "Error al registrar el usuario en el Servidor de Turnos: " + e.getMessage());
		} 
		
		return hmResult;
	}
	
	
	public String getEmail(Context context) throws UxorException {
	    AccountManager accountManager = AccountManager.get(context); 
	    Account account = getAccount(accountManager);

	    if (account == null) {
	      return null;
	    } else {
	      return account.name;
	    }
	}

	public Account getAccount(AccountManager accountManager) throws UxorException {
	    Account[] accounts = accountManager.getAccountsByType("com.google");
	    Account account;
	    if (accounts.length > 0) {
	      account = accounts[0];      
	    } else {
	      account = null;
	    }
	    return account;
	}

}
