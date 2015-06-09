package com.uxor.turnos.presenter.helper;

import java.util.ArrayList;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.util.Log;

import com.uxor.turnos.util.Util;

public class SplashHelper implements ISplashHelper{
	
    private static final String TAG = "SplashHelper"; 
	
	private static final SplashHelper instance = new SplashHelper();
	
	public SplashHelper(){		
	}
	
	public static SplashHelper getInstance(){
        return instance;
    }
	

	@Override
	public Boolean updateGcmCodeServer(Context context, Integer userId, String gcmCode) {
		
		Boolean result = Boolean.FALSE;
		
		final String NAMESPACE = Util.getPropertieValue(context, "config.properties", "gcmUpdateRegistration.webservice.namespace");
		final String URL = Util.getPropertieValue(context, "config.properties", "gcmUpdateRegistration.webservice.url");
		final String METHOD_NAME = Util.getPropertieValue(context, "config.properties", "gcmUpdateRegistration.webservice.methodName");
		final String SOAP_ACTION = Util.getPropertieValue(context, "config.properties", "gcmUpdateRegistration.webservice.soapAction");
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("userId", userId); 
		request.addProperty("pushNotificationKey", gcmCode);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);

		HttpTransportSE transporte = new HttpTransportSE(URL);
		
		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente. 
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));

		try{
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			
			SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
			result = Boolean.valueOf(resultado_xml.toString());
			
		}catch (Exception e){
			Log.d(TAG, "Error al actualizar el gcm code en el servidor de turnos: " + e.getMessage());
		} 
		
		return result;
	}

}
