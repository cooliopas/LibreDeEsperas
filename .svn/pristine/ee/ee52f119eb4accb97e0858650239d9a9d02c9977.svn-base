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
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import com.uxor.turnos.domain.Branch;
import com.uxor.turnos.domain.Client;
import com.uxor.turnos.domain.ClientType;
import com.uxor.turnos.domain.Country;
import com.uxor.turnos.domain.Service;
import com.uxor.turnos.domain.State;
import com.uxor.turnos.domain.Town;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.util.Util;

public class ConfigurationHelper implements IConfigurationHelper {

	private static final String LOGTAG = "ConfigurationHelper";
		
	private static final ConfigurationHelper instance = new ConfigurationHelper();
	
	public ConfigurationHelper(){
	}
	
	public static ConfigurationHelper getInstance(){
        return instance;
    }

	@Override
	public HashMap retrieveConfigurationData(Context context, String gcmCode)
			throws UxorException {
			
		HashMap hmConfigurationData = new HashMap();
		
		
		//Llamo a los webservice para obtener las actualizaciones de la configuración de datos
		HashMap hmCountries = retrieveCountriesData(context, gcmCode);
		HashMap hmStates = retrieveStatesData(context, gcmCode);
		HashMap hmTowns = retrieveTownsData(context, gcmCode);
		HashMap hmClientTypes = retrieveClientTypesData(context, gcmCode);
		HashMap hmClients = retrieveClientsData(context, gcmCode);
		HashMap hmBranchs = retrieveBranchsData(context, gcmCode);
		HashMap hmServices = retrieveServicesData(context, gcmCode);
		

		//Guardo los datos de configuración obtenidos del servidor (Sucursales, Paises, Ciudades, Provincias, etc)
		hmConfigurationData.put("hmCountries", hmCountries);
		hmConfigurationData.put("hmStates", hmStates);
		hmConfigurationData.put("hmTowns", hmTowns);
		hmConfigurationData.put("hmClientTypes", hmClientTypes);
		hmConfigurationData.put("hmClients", hmClients);		
		hmConfigurationData.put("hmBranchs", hmBranchs);
		hmConfigurationData.put("hmServices", hmServices);
		
		return hmConfigurationData;
	}
	
	
	public HashMap retrieveCountriesData(Context context, String gcmCode)
			throws UxorException {
		
		HashMap hmResult = new HashMap();
		List<Country> lstCountries = new ArrayList<Country>();
		
		final String NAMESPACE = Util.getPropertieValue(context, "config.properties", "updateCountries.webservice.namespace");
		final String URL= Util.getPropertieValue(context, "config.properties", "updateCountries.webservice.url");
		final String METHOD_NAME = Util.getPropertieValue(context, "config.properties", "updateCountries.webservice.methodName");
		final String SOAP_ACTION =  Util.getPropertieValue(context, "config.properties", "updateCountries.webservice.soapAction");
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("gcmCode", gcmCode);
		       
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL,3000);
		
		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente. 
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
		
		try {
			
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			 
        	SoapObject resSoap = (SoapObject) envelope.bodyIn;
        	
	        
        	for (int i = 0; i < resSoap.getPropertyCount(); i++){
	               SoapObject so = (SoapObject)resSoap.getProperty(i);
	               
	 
	               Country country = new Country();
	               if (so.hasProperty("countryId") && so.getProperty("countryId")!=null){
	            	   country.setId(new BigDecimal(so.getProperty("countryId").toString()));
	               }
	               
	               if (so.hasProperty("name") && so.getProperty("name")!=null){
	            	   country.setDescription((so.getProperty("name").toString()));
	               }
	               
	               lstCountries.add(country);
	        }
        	
        	hmResult.put("lstCountries", lstCountries);
  	        hmResult.put("resultWSCountryError", Boolean.FALSE);
			
			
		} catch (Exception e){
			Log.e(LOGTAG, "Error al recuperar los países: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("resultWSCountryError", Boolean.TRUE);
			//hmResult.put("msg", "Error al recuperar las actualizaciones de turnos: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("msg", "Problemas de conexión. Intente nuevamente...");
		} 
		
		return hmResult;
	}
	
	
	public HashMap retrieveStatesData(Context context, String gcmCode)
			throws UxorException {
		
		HashMap hmResult = new HashMap();
		List<State> lstStates = new ArrayList<State>();
		
		final String NAMESPACE = Util.getPropertieValue(context, "config.properties", "updateStates.webservice.namespace");
		final String URL= Util.getPropertieValue(context, "config.properties", "updateStates.webservice.url");
		final String METHOD_NAME = Util.getPropertieValue(context, "config.properties", "updateStates.webservice.methodName");
		final String SOAP_ACTION =  Util.getPropertieValue(context, "config.properties", "updateStates.webservice.soapAction");
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("gcmCode", gcmCode);
		       
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL, 3000);
		
		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente. 
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));

		try {
			
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			 
	        SoapObject resSoap = (SoapObject) envelope.bodyIn;
	 
	        for (int i = 0; i < resSoap.getPropertyCount(); i++){
	               SoapObject so = (SoapObject)resSoap.getProperty(i);
	               
	               State state = new State();
	               if (so.hasProperty("stateId") && so.getProperty("stateId")!=null){
	            	   state.setId(new BigDecimal(so.getProperty("stateId").toString()));
	               }
	               
	               if (so.hasProperty("name") && so.getProperty("name")!=null){
	            	   state.setDescription((so.getProperty("name").toString()));
	               }
	               
	               if (so.hasProperty("countryId") && so.getProperty("countryId")!=null){
	            	   state.setCountryId(new BigDecimal(so.getProperty("countryId").toString()));
	               }
	               
	               lstStates.add(state);
	        }	
	        
	        hmResult.put("lstStates", lstStates);
	        hmResult.put("resultWSStateError", Boolean.FALSE);
			
			
		} catch (Exception e){
			Log.e(LOGTAG, "Error al recuperar las provincias: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("resultWSStateError", Boolean.TRUE);
			//hmResult.put("msg", "Error al recuperar las actualizaciones de turnos: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("msg", "Problemas de conexión. Intente nuevamente...");
		} 
		
		return hmResult;
	}
	
	
	
	public HashMap retrieveTownsData(Context context, String gcmCode)
			throws UxorException {
		
		HashMap hmResult = new HashMap();
		List<Town> lstTowns = new ArrayList<Town>();
		
		final String NAMESPACE = Util.getPropertieValue(context, "config.properties", "updateTowns.webservice.namespace");
		final String URL= Util.getPropertieValue(context, "config.properties", "updateTowns.webservice.url");
		final String METHOD_NAME = Util.getPropertieValue(context, "config.properties", "updateTowns.webservice.methodName");
		final String SOAP_ACTION =  Util.getPropertieValue(context, "config.properties", "updateTowns.webservice.soapAction");
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("gcmCode", gcmCode);
		       
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL, 3000);
		
		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente. 
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
		
		try {
			
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			 
        	SoapObject resSoap = (SoapObject) envelope.bodyIn;
        	
	        
        	for (int i = 0; i < resSoap.getPropertyCount(); i++){
	               SoapObject so = (SoapObject)resSoap.getProperty(i);
	               
	               Town town = new Town();
	               if (so.hasProperty("cityId") && so.getProperty("cityId")!=null){
	            	   town.setId(new Long(so.getProperty("cityId").toString()));
	               }
	               
	               if (so.hasProperty("name") && so.getProperty("name")!=null){
	            	   town.setDescription((so.getProperty("name").toString()));
	               }
	               
	               if (so.hasProperty("stateId") && so.getProperty("stateId")!=null){
	            	   town.setProvinceId(new Long(so.getProperty("stateId").toString()));
	               }
	               
	               lstTowns.add(town);
	        }
        	
        	hmResult.put("lstTowns", lstTowns);
  	        hmResult.put("resultWSTownError", Boolean.FALSE);
			
			
		} catch (Exception e){
			Log.e(LOGTAG, "Error al recuperar las ciudades: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("resultWSTownError", Boolean.TRUE);
			//hmResult.put("msg", "Error al recuperar las actualizaciones de turnos: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("msg", "Problemas de conexión. Intente nuevamente...");
		} 
		
		return hmResult;
	}
	
	
	public HashMap retrieveClientTypesData(Context context, String gcmCode)
			throws UxorException {
		
		HashMap hmResult = new HashMap();
		List<ClientType> lstClientTypes = new ArrayList<ClientType>();
		
		final String NAMESPACE = Util.getPropertieValue(context, "config.properties", "updateClientTypes.webservice.namespace");
		final String URL= Util.getPropertieValue(context, "config.properties", "updateClientTypes.webservice.url");
		final String METHOD_NAME = Util.getPropertieValue(context, "config.properties", "updateClientTypes.webservice.methodName");
		final String SOAP_ACTION =  Util.getPropertieValue(context, "config.properties", "updateClientTypes.webservice.soapAction");
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("gcmCode", gcmCode);
		       
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL, 3000);
		
		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente. 
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
		
		try {
			
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			 
        	SoapObject resSoap = (SoapObject) envelope.bodyIn;
        	
	        
        	for (int i = 0; i < resSoap.getPropertyCount(); i++){
	               SoapObject so = (SoapObject)resSoap.getProperty(i);
	               
	               ClientType clientType = new ClientType();
	               if (so.hasProperty("clientTypeId") && so.getProperty("clientTypeId")!=null){
	            	   clientType.setId(new BigDecimal(so.getProperty("clientTypeId").toString()));
	               }
	               
	               if (so.hasProperty("description") && so.getProperty("description")!=null){
	            	   clientType.setDescription((so.getProperty("description").toString()));
	               }
	               
	               lstClientTypes.add(clientType);
	        }
        	
        	hmResult.put("lstClientTypes", lstClientTypes);
  	        hmResult.put("resultWSClientTypeError", Boolean.FALSE);
			
			
		} catch (Exception e){
			Log.e(LOGTAG, "Error al recuperar los Tipos de Clientes: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("resultWSClientTypeError", Boolean.TRUE);
			//hmResult.put("msg", "Error al recuperar las actualizaciones de turnos: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("msg", "Problemas de conexión. Intente nuevamente...");
		} 
		
		return hmResult;
	}
	
	
	public HashMap retrieveClientsData(Context context, String gcmCode)
			throws UxorException {
		
		HashMap hmResult = new HashMap();
		List<Client> lstClients = new ArrayList<Client>();
		
		final String NAMESPACE = Util.getPropertieValue(context, "config.properties", "updateClient.webservice.namespace");
		final String URL= Util.getPropertieValue(context, "config.properties", "updateClient.webservice.url");
		final String METHOD_NAME = Util.getPropertieValue(context, "config.properties", "updateClient.webservice.methodName");
		final String SOAP_ACTION =  Util.getPropertieValue(context, "config.properties", "updateClient.webservice.soapAction");
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("gcmCode", gcmCode);
		       
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL, 3000);
		
		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente. 
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
		
		try {
			
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			 
        	SoapObject resSoap = (SoapObject) envelope.bodyIn;
        	
        	for (int i = 0; i < resSoap.getPropertyCount(); i++){
	               SoapObject so = (SoapObject)resSoap.getProperty(i);
	               
	               Client client = new Client();
	               if (so.hasProperty("clientId") && so.getProperty("clientId")!=null){
	            	   client.setId(new BigDecimal(so.getProperty("clientId").toString()));
	               }
	               
	               if (so.hasProperty("clientTypeId") && so.getProperty("clientTypeId")!=null){
	            	   client.setClientTypeId(new BigDecimal(so.getProperty("clientTypeId").toString()));
	               }
	               
	               if (so.hasProperty("name") && so.getProperty("name")!=null){
	            	   client.setName((so.getProperty("name").toString()));
	               }
	               
	               if (so.hasProperty("logo") && so.getProperty("logo")!=null){
	            	   client.setImageId((so.getProperty("logo").toString()));
	               }else{
	            	   client.setImageId("logo_white");
	               }
	               
	               if (so.hasProperty("state") && so.getProperty("state")!=null){
	            	   client.setState((so.getProperty("state").toString()));
	               }
	               
	               lstClients.add(client);
	        }
        	
        	hmResult.put("lstClients", lstClients);
  	        hmResult.put("resultWSClientError", Boolean.FALSE);
			
			
		} catch (Exception e){
			Log.e(LOGTAG, "Error al recuperar los Clientes: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("resultWSClientError", Boolean.TRUE);
			//hmResult.put("msg", "Error al recuperar las actualizaciones de turnos: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("msg", "Problemas de conexión. Intente nuevamente...");
		} 
		
		return hmResult;
	}
	

	public HashMap retrieveBranchsData(Context context, String gcmCode) throws UxorException {
		
		HashMap hmResult = new HashMap();
		List<Branch> lstBranchs = new ArrayList<Branch>();
		
		final String NAMESPACE = Util.getPropertieValue(context, "config.properties", "updateBranchs.webservice.namespace");
		final String URL= Util.getPropertieValue(context, "config.properties", "updateBranchs.webservice.url");
		final String METHOD_NAME = Util.getPropertieValue(context, "config.properties", "updateBranchs.webservice.methodName");
		final String SOAP_ACTION =  Util.getPropertieValue(context, "config.properties", "updateBranchs.webservice.soapAction");
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("gcmCode", gcmCode);
		       
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL, 3000);
		
		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente. 
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));

		try {
			
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			 
	        SoapObject resSoap = (SoapObject) envelope.bodyIn;
	 
	        for (int i = 0; i < resSoap.getPropertyCount(); i++){
	               SoapObject so = (SoapObject)resSoap.getProperty(i);
	               
	 
	               Branch branch = new Branch();
	               if (so.hasProperty("branchId") && so.getProperty("branchId")!=null){
	            	   branch.setId(new BigDecimal(so.getProperty("branchId").toString()));
	               }
	               
	               if (so.hasProperty("clientId") && so.getProperty("clientId")!=null){
	            	   Client client = new Client();
	            	   client.setId(new BigDecimal(so.getProperty("clientId").toString()));
	            	   branch.setClient(client);
	               }
	               
	               if (so.hasProperty("alias") && so.getProperty("alias")!=null){
	            	   branch.setAlias(so.getProperty("alias").toString());
	               }
	               
	               if (so.hasProperty("address") && so.getProperty("address")!=null){
	            	   branch.setAddress(so.getProperty("address").toString());
	               }
	               
	               if (so.hasProperty("cityId") && so.getProperty("cityId")!=null){
	            	   branch.setCityId(new BigDecimal(so.getProperty("cityId").toString()));
	               }
	               
	               if (so.hasProperty("phoneNumber") && so.getProperty("phoneNumber")!=null){
	            	   branch.setPhoneNumber(so.getProperty("phoneNumber").toString());
	               }
	               
	               if (so.hasProperty("state") && so.getProperty("state")!=null){
	            	   branch.setState(so.getProperty("state").toString());
	               }
	               
	               lstBranchs.add(branch);
	        }	
	        
	        hmResult.put("lstBranchs", lstBranchs);
	        hmResult.put("resultWSBranchError", Boolean.FALSE);
			
			
		} catch (Exception e){
			Log.e(LOGTAG, "Error al recuperar las notificaciones de turnos: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("resultWSBranchError", Boolean.TRUE);
			//hmResult.put("msg", "Error al recuperar las actualizaciones de turnos: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("msg", "Problemas de conexión. Intente nuevamente...");
		} 
		
		return hmResult;
	}
	
	
	public HashMap retrieveServicesData(Context context, String gcmCode)
			throws UxorException {
		
		HashMap hmResult = new HashMap();
		List<Service> lstServices = new ArrayList<Service>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		
		final String NAMESPACE = Util.getPropertieValue(context, "config.properties", "updateServices.webservice.namespace");
		final String URL= Util.getPropertieValue(context, "config.properties", "updateServices.webservice.url");
		final String METHOD_NAME = Util.getPropertieValue(context, "config.properties", "updateServices.webservice.methodName");
		final String SOAP_ACTION =  Util.getPropertieValue(context, "config.properties", "updateServices.webservice.soapAction");
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("gcmCode", gcmCode);
		       
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL, 3000);
		
		//Tip para que funcione correctamente la llamada al webservice en algunos celulares. 
		//Anteriormente fallaba en la primer llamada y luego en la segunda funcionaba correctamente. 
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
		
		try {
			
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			 
        	SoapObject resSoap = (SoapObject) envelope.bodyIn;
        	
        	for (int i = 0; i < resSoap.getPropertyCount(); i++){
	               SoapObject so = (SoapObject)resSoap.getProperty(i);
	               
	               
	               Service service = new Service();
	               
	               if (so.hasProperty("serviceId") && so.getProperty("serviceId")!=null){
	            	   service.setId(new BigDecimal((so.getProperty("serviceId").toString())));
	               }
	               
	               if (so.hasProperty("aditional_info") && so.getProperty("aditional_info")!=null){
	            	   service.setAditionalInfo(so.getProperty("aditional_info").toString());
	               }
	               
	               if (so.hasProperty("backLog") && so.getProperty("backLog")!=null){
	            	   if (Boolean.valueOf(so.getProperty("backLog").toString())){
	            		   service.setBackLog(Boolean.TRUE);
	            	   }else{
	            		   service.setBackLog(Boolean.FALSE);
	            	   }
	               }
	               
	               if (so.hasProperty("branchId") && so.getProperty("branchId")!=null){
	            	   service.setBranchId(new BigDecimal((so.getProperty("branchId").toString())));
	               }
	               
	               if (so.hasProperty("confirmCancelTurn") && so.getProperty("confirmCancelTurn")!=null){
	            	   service.setConfirmCancelTurn(new BigDecimal((so.getProperty("confirmCancelTurn").toString())));
	               }
	               
	               if (so.hasProperty("description") && so.getProperty("description")!=null){
	            	   service.setDescription(so.getProperty("description").toString());
	               }
	               
	               if (so.hasProperty("enabled") && so.getProperty("enabled")!=null){
	            	   if (Boolean.valueOf(so.getProperty("enabled").toString())){
	            		   service.setEnabled(Boolean.TRUE);
	            	   }else{
	            		   service.setEnabled(Boolean.FALSE);
	            	   }
	               }
	               
	               if (so.hasProperty("estimatedAvgQueue") && so.getProperty("estimatedAvgQueue")!=null){
	            	   service.setEstimatedAvgQueue(new BigDecimal((so.getProperty("estimatedAvgQueue").toString())));
	               }
	               
	               if (so.hasProperty("futureTurns") && so.getProperty("futureTurns")!=null){
	            	   if (Boolean.valueOf(so.getProperty("futureTurns").toString())){
	            		   service.setFutureTurns(Boolean.TRUE);
	            	   }else{
	            		   service.setFutureTurns(Boolean.FALSE);
	            	   }
	               }
	               
	               if (so.hasProperty("initialNumber") && so.getProperty("initialNumber")!=null){
	            	   service.setInitialNumber(new BigDecimal((so.getProperty("initialNumber").toString())));
	               }
	               
	               
	               if (so.hasProperty("lastUpdate") && so.getProperty("lastUpdate")!=null){
	            	   service.setLastUpadate(new Timestamp(sf.parse(so.getProperty("lastUpdate").toString()).getTime()));
	               }
	               
	               if (so.hasProperty("letter") && so.getProperty("letter")!=null){
	            	   service.setLetter(so.getPrimitiveProperty("letter").toString());
	               }
	               
	               if (so.hasProperty("numberQty") && so.getProperty("numberQty")!=null){
	            	   if (Boolean.valueOf(so.getProperty("numberQty").toString())){
	            		   service.setNumberQty(Boolean.TRUE);
	            	   }else{
	            		   service.setNumberQty(Boolean.FALSE);
	            	   }
	               }
	               
	               if (so.hasProperty("ticketMaxNumber") && so.getProperty("ticketMaxNumber")!=null){
	            	   service.setTicketMaxNumber(new BigDecimal((so.getProperty("ticketMaxNumber").toString())));
	               }
	               
	               if (so.hasProperty("waitingTime") && so.getProperty("waitingTime")!=null){
	            	   if (Boolean.valueOf(so.getProperty("waitingTime").toString())){
	            		   service.setWaitingTime(Boolean.TRUE);
	            	   }else{
	            		   service.setWaitingTime(Boolean.FALSE);
	            	   }
	               }
	               
	               if (so.hasProperty("state") && so.getProperty("state")!=null){
	            	   service.setState(so.getProperty("state").toString());
	               }
	               
	               
	               lstServices.add(service);
	        }
        	
        	hmResult.put("lstServices", lstServices);
  	        hmResult.put("resultWSServiceError", Boolean.FALSE);
			
			
		} catch (Exception e){
			Log.e(LOGTAG, "Error al recuperar los Servicios: " + e.getCause() + " || " + e.getMessage());
			hmResult.put("resultWSServiceError", Boolean.TRUE);
			hmResult.put("msg", "Problemas de conexión. Intente nuevamente...");
		} 
		
		return hmResult;
	}
	

	
}
