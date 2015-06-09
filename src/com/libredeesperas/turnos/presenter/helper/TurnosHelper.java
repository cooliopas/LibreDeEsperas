package com.uxor.turnos.presenter.helper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.util.Log;

import com.uxor.turnos.domain.ServerNew;
import com.uxor.turnos.domain.Turno;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.util.Util;

public class TurnosHelper implements ITurnosHelper {

	private static final String LOGTAG = "TurnosHelper";
		
	private static final TurnosHelper instance = new TurnosHelper();
	
	private static final String TAG = "TurnosHelper"; 
	
	public TurnosHelper(){
	}
	
	public static TurnosHelper getInstance(){
        return instance;
    }
	
	@Override
	public HashMap retrieveInfoTurns(List<String> vcServerIds) {
		
		boolean result = false;
		Turno[] listaTurnos;
		HashMap hmResult = new HashMap();

		
		final String NAMESPACE = "http://webservice.infoturnos.uxor.com";
		final String URL="http://192.168.150.140:8080/wsinfoturnos/services/InfoTurnos?wsdl";
		final String METHOD_NAME = "retrieveInfoTurns";
		final String SOAP_ACTION = "http://webservice.infoturnos.uxor.com/retrieveInfoTurns";
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		SoapObject soapIds = new SoapObject(NAMESPACE, "serverIds");
		soapIds.addProperty("string", "1");
		soapIds.addProperty("string", "2");
		request.addSoapObject(soapIds);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);
		
		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente.
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));

		try {
			
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			 
	        SoapObject resSoap = (SoapObject) envelope.bodyIn;
	 
	        listaTurnos = new Turno[resSoap.getPropertyCount()];
	        
	        for (int i = 0; i < listaTurnos.length; i++){
	               SoapObject ic = (SoapObject)resSoap.getProperty(i);
	 
	               Turno turno = new Turno();
	               turno.setServerTurnId(new BigDecimal(ic.getProperty(0).toString()));
	               turno.setWaitingTime(new BigDecimal(ic.getProperty(1).toString()));
	               turno.setTurnsBefore(new BigDecimal(ic.getProperty(2).toString()));
	 
	               listaTurnos[i] = turno;
	        }	
	        
	        hmResult.put("listaTurnos", listaTurnos);
	        hmResult.put("result", Boolean.TRUE);
			
			
		} catch (Exception e){
			Log.e(LOGTAG, "Error registro en mi servidor: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("result", Boolean.FALSE);
			return hmResult;
		} 
		
		return hmResult;

	}
	
	@Override
	public HashMap newServerTurn(Context context, BigDecimal clientServiceId, String gcmCode, Timestamp turnDate, String turnNumber, Integer userId)  throws UxorException{
		boolean result = false;
		HashMap hmResult = new HashMap();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sfHour = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		hmResult.put("result", Boolean.FALSE);
		
		
		final String NAMESPACE = Util.getPropertieValue(context, "config.properties", "turnRegistration.webservice.namespace");
		final String URL= Util.getPropertieValue(context, "config.properties", "turnRegistration.webservice.url");
		final String METHOD_NAME = Util.getPropertieValue(context, "config.properties", "turnRegistration.webservice.methodName");
		final String SOAP_ACTION =  Util.getPropertieValue(context, "config.properties", "turnRegistration.webservice.soapAction");
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("userId", userId);
		request.addProperty("serviceID", clientServiceId.intValue()); 
		request.addProperty("pushNotificationKey", gcmCode);
		request.addProperty("turnDate", sf.format(turnDate));
		request.addProperty("turnNumber", turnNumber);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);

		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente.
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
		
		try {
			
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			
	        SoapObject response = (SoapObject)envelope.getResponse();
	        Turno serverTurn = new Turno();
	        
	        if (response.getProperty("exceptionMessage")!=null){
	        	serverTurn.setExceptionMessage(response.getProperty("exceptionMessage").toString());
	        }
	        
	        if (response.getProperty("turnHour")!=null){
	        	serverTurn.setTurnHour(new Timestamp(sfHour.parse(response.getProperty("turnHour").toString()).getTime()));
	        }
	        
	        if (response.getProperty("turnId")!=null){
	        	serverTurn.setServerTurnId(new BigDecimal(response.getProperty("turnId").toString()));
	        }
	        
	        if (response.getProperty("turnStatus")!=null){
	        	serverTurn.setStatus(response.getProperty("turnStatus").toString());
	        }
	        
	        if (response.getProperty("turnsBefore")!=null){
	        	serverTurn.setTurnsBefore(new BigDecimal(response.getProperty("turnsBefore").toString()));
	        }
	        
	        if (response.getProperty("waitingTime")!=null){
	        	serverTurn.setWaitingTime(new BigDecimal(response.getProperty("waitingTime").toString()));
	        }
            
            hmResult.put("serverTurn", serverTurn);
            
            if(serverTurn.getExceptionMessage()==null){
     	        hmResult.put("result", Boolean.TRUE);
     	        hmResult.put("msg", "El turno nro ".concat(turnNumber.toString()).concat(" fue registrado correctamente en el Servidor"));
     	        Log.d(TAG, "El turno fue registrado correctamente en el Servidor");
            }else{
     	        hmResult.put("result", Boolean.FALSE);
     	        hmResult.put("msg", serverTurn.getExceptionMessage());
     	        Log.d(TAG, "Error al registrar el turno en el Servidor");
            }
			
		} catch (Exception e){
			Log.e(LOGTAG, "Error registro en mi servidor: " + e.getMessage());
			throw new UxorException(e.getMessage()!=null?e.getMessage():"Se produjo un error", e);
		} 
		
		return hmResult;
	}
	
	@Override
	public HashMap delServerTurn(Context context, BigDecimal serverTurnId)  throws UxorException{
		boolean result = false;
		HashMap hmResult = new HashMap();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sfHour = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		hmResult.put("result", Boolean.FALSE);
		
		
		final String NAMESPACE = Util.getPropertieValue(context, "config.properties", "turnDelete.webservice.namespace");
		final String URL= Util.getPropertieValue(context, "config.properties", "turnDelete.webservice.url");
		final String METHOD_NAME = Util.getPropertieValue(context, "config.properties", "turnDelete.webservice.methodName");
		final String SOAP_ACTION =  Util.getPropertieValue(context, "config.properties", "turnDelete.webservice.soapAction");
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("serverTurnId", serverTurnId.intValue()); 
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);
		
		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente. 
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));

		try {
			
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			
	        SoapObject response = (SoapObject)envelope.getResponse();
	        Turno serverTurn = new Turno();
	        
	        if (response.getProperty("exceptionMessage")!=null){
	        	serverTurn.setExceptionMessage(response.getProperty("exceptionMessage").toString());
	        }
	        
            hmResult.put("serverTurn", serverTurn);
            
            if(serverTurn.getExceptionMessage()==null){
     	        hmResult.put("result", Boolean.TRUE);
     	        hmResult.put("msg", "El turno"/* nro ".concat(serverTurn.getTurnNumber().toString())*/.concat(" fue cancelado correctamente en el Servidor"));
     	        Log.d(TAG, "El turno fue cancelado correctamente en el Servidor");
            }else{
     	        hmResult.put("result", Boolean.FALSE);
     	        hmResult.put("msg", serverTurn.getExceptionMessage());
     	        Log.d(TAG, "Error al eliminar el turno en el Servidor");
            }
			
		} catch (Exception e){
			Log.e(LOGTAG, "Error registro en mi servidor: " + e.getMessage());
			throw new UxorException(e.getMessage()!=null?e.getMessage():"Se produjo un error", e);
		} 
		
		return hmResult;
	}
	
	
	@Override
	public HashMap getServiceCongestion(Context context, BigDecimal serviceId)  throws UxorException{
		boolean result = false;
		HashMap hmResult = new HashMap();
		hmResult.put("result", Boolean.FALSE);
		
		
		final String NAMESPACE = Util.getPropertieValue(context, "config.properties", "getCongestion.webservice.namespace");
		final String URL= Util.getPropertieValue(context, "config.properties", "getCongestion.webservice.url");
		final String METHOD_NAME = Util.getPropertieValue(context, "config.properties", "getCongestion.webservice.methodName");
		final String SOAP_ACTION =  Util.getPropertieValue(context, "config.properties", "getCongestion.webservice.soapAction");
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("serviceId", serviceId.intValue()); 
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);
		
		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente. 
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));

		try {
			
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			
	        SoapObject response = (SoapObject)envelope.getResponse();
	        Turno serverTurn = new Turno();
	        
	        if (response.getProperty("exceptionMessage")!=null){
	        	serverTurn.setExceptionMessage(response.getProperty(3).toString());
	        }
	        
	        if (response.getProperty("turnsBefore")!=null){
	        	serverTurn.setTurnsBefore(new BigDecimal(response.getProperty("turnsBefore").toString()));
	        }
	        
	        if (response.getProperty("waitingTime")!=null){
	        	serverTurn.setWaitingTime(new BigDecimal(response.getProperty("waitingTime").toString()));
	        }
            
            hmResult.put("serverTurn", serverTurn);
            
            if(serverTurn.getExceptionMessage()==null){
     	        hmResult.put("result", Boolean.TRUE);
     	        hmResult.put("msg", "La consulta de congestión fue realizada con exito en el servidor");
     	        Log.d(TAG, "La consulta de congestión fue realizada con exito");
            }else{
     	        hmResult.put("result", Boolean.FALSE);
     	        hmResult.put("msg", serverTurn.getExceptionMessage());
     	        Log.d(TAG, "Error al consultar la congestión el turno en el Servidor");
            }
			
		} catch (Exception e){
			Log.e(LOGTAG, "Error registro en mi servidor: " + e.getMessage());
			throw new UxorException(e.getMessage()!=null?e.getMessage():"Se produjo un error", e);
		} 
		
		return hmResult;
	}

	@Override
	public HashMap retrieveTurnsNotifications(Context context, String gcmCode)
			throws UxorException {

		HashMap hmResult = new HashMap();
		List<Turno> lstTurnsNotifications = new ArrayList<Turno>();
		
		final String NAMESPACE = Util.getPropertieValue(context, "config.properties", "updateTurnsNotifications.webservice.namespace");
		final String URL= Util.getPropertieValue(context, "config.properties", "updateTurnsNotifications.webservice.url");
		final String METHOD_NAME = Util.getPropertieValue(context, "config.properties", "updateTurnsNotifications.webservice.methodName");
		final String SOAP_ACTION =  Util.getPropertieValue(context, "config.properties", "updateTurnsNotifications.webservice.soapAction");
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("gcmCode", gcmCode);
		       
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);
		
		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente. 
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));

		try {
			
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			 
	        SoapObject resSoap = (SoapObject) envelope.bodyIn;
	 
	        for (int i = 0; i < resSoap.getPropertyCount(); i++){
	               SoapObject so = (SoapObject)resSoap.getProperty(i);
	 
	               Turno turn = new Turno();
	               if (so.getProperty("turnId")!=null){
	            	   turn.setServerTurnId(new BigDecimal(so.getProperty("turnId").toString()));
	               }
	               
	               if (so.getProperty("turnStatus")!=null){
	            	   turn.setStatus(so.getProperty("turnStatus").toString());
	               }
	               
	               if (so.getProperty("turnsBefore")!=null){
	            	   turn.setTurnsBefore(new BigDecimal(so.getProperty("turnsBefore").toString()));
	               }
	               
	               if (so.getProperty("waitingTime")!=null){
	               turn.setWaitingTime(new BigDecimal(so.getProperty("waitingTime").toString()));
	               }
	               
	               lstTurnsNotifications.add(turn);
	        }	
	        
	        hmResult.put("lstTurnsNotifications", lstTurnsNotifications);
	        hmResult.put("result", Boolean.TRUE);
			
			
		} catch (Exception e){
			Log.e(LOGTAG, "Error al recuperar las notificaciones de turnos: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("result", Boolean.FALSE);
			//hmResult.put("msg", "Error al recuperar las actualizaciones de turnos: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("msg", "Problemas de conexión. Intente nuevamente...");
		} 
		
		return hmResult;
	}
	
	@Override
	public HashMap retrieveServerNotifications(Context context, String gcmCode)
			throws UxorException {

		HashMap hmResult = new HashMap();
		List<ServerNew> lstServerNotifications = new ArrayList<ServerNew>();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

		
		final String NAMESPACE = Util.getPropertieValue(context, "config.properties", "updateServerNotifications.webservice.namespace");
		final String URL= Util.getPropertieValue(context, "config.properties", "updateServerNotifications.webservice.url");
		final String METHOD_NAME = Util.getPropertieValue(context, "config.properties", "updateServerNotifications.webservice.methodName");
		final String SOAP_ACTION =  Util.getPropertieValue(context, "config.properties", "updateServerNotifications.webservice.soapAction");
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("gcmCode", gcmCode);
		       
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);
		
		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente. 
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
		
		Boolean updateIntent = Boolean.TRUE; 
		int loopIndex=0;
		
		while (loopIndex<2 && updateIntent) //Para intentar 2 veces por si falla la primera.
		{
			try {
				
				transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
				 
		        SoapObject resSoap = (SoapObject) envelope.bodyIn;
		 
		        for (int i = 0; i < resSoap.getPropertyCount(); i++){
					  SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		    		
		               SoapObject so = (SoapObject)resSoap.getProperty(i);
	
		               ServerNew serverNew = new ServerNew();
		               if (so.getProperty("serverTurnId")!=null){
		            	   serverNew.setServerTurnId(new BigDecimal(so.getProperty("serverTurnId").toString()));
		               }
		               
		               if (so.getProperty("newsDate")!=null){ //VER ACA
		            	   Timestamp fecha = new Timestamp(dateFormat.parse(so.getProperty("newsDate").toString()).getTime()); 

		            	   serverNew.setRecivedDate(fecha);
		               }
		               
		               if (so.getProperty("typeAlert")!=null){
		            	   serverNew.setType(so.getProperty("typeAlert").toString());
		               }
		               
		               if (so.getProperty("msg")!=null){
		            	   serverNew.setMessage(so.getProperty("msg").toString());
		               }
		               
		               lstServerNotifications.add(serverNew);
		        }	
		        
		        hmResult.put("lstServerNotifications", lstServerNotifications);
		        hmResult.put("result", Boolean.TRUE);
		        
		        updateIntent=Boolean.FALSE;
				
			} catch (Exception e){
				Log.e(LOGTAG, "Error al recuperar las notificaciones de turnos: " + e.getCause() + " || " + e.getMessage());
				hmResult.put("result", Boolean.FALSE);
				//hmResult.put("msg", "Error al recuperar las notificaciones de turnos: " + e.getCause() + " || " + e.getMessage());
				hmResult.put("msg", "Problemas de conexión. Intente nuevamente...");
			} 
			
			loopIndex++;
		}
		
		return hmResult;
	}
	
	
	
	
	

}
