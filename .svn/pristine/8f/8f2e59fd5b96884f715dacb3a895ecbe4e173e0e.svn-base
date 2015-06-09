package com.uxor.turnos.presenter;

import java.util.HashMap;
import java.util.List;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.uxor.turnos.domain.Branch;
import com.uxor.turnos.domain.Client;
import com.uxor.turnos.domain.ClientType;
import com.uxor.turnos.domain.Country;
import com.uxor.turnos.domain.Service;
import com.uxor.turnos.domain.State;
import com.uxor.turnos.domain.Town;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.ConfigurationModel;
import com.uxor.turnos.model.IConfigurationModel;
import com.uxor.turnos.model.ITownModel;
import com.uxor.turnos.model.TownModel;
import com.uxor.turnos.presenter.helper.ApplicationHelper;
import com.uxor.turnos.presenter.helper.ConfigurationHelper;
import com.uxor.turnos.presenter.helper.IApplicationHelper;
import com.uxor.turnos.presenter.helper.IConfigurationHelper;
import com.uxor.turnos.util.TaskWSUpdateConfigurationData;
import com.uxor.turnos.util.Turnos;
import com.uxor.turnos.view.IConfigurationView;

public class ConfigurationPresenter {
	
                        
    static final String LOGTAG = "ConfigurationPresenter"; 
	
	private IConfigurationView confView;
	private ITownModel townModel;
	private IConfigurationModel confModel;
	private IApplicationHelper applicationHelper;
	private IConfigurationHelper confHelper;
	
	public ConfigurationPresenter(){
	}
	
	public ConfigurationPresenter(IConfigurationView view){
		this(view, new TownModel(), ApplicationHelper.getInstance(), new  ConfigurationModel(), ConfigurationHelper.getInstance());
	}
	
	public ConfigurationPresenter(IConfigurationView view, ITownModel townModel, IApplicationHelper applicationHelper, IConfigurationModel confModel, IConfigurationHelper confHelper){
		this.confView = view;
		this.townModel = townModel;
		this.applicationHelper = applicationHelper;
		this.confModel = confModel;
		this.confHelper = confHelper;
	}
	
		
	/*
	 * Devuelve el listado de ciudades para cargar en el combo
	 */
	public void retrieveTownsCbo() throws UxorException{
		//Actualizo la grilla de ciudades
		this.confView.setVcTownsCbo(this.townModel.retrieveTownsCbo());
	}
	
	public void updateConfigurationData() throws UxorException{
		
		//Obtengo la SharePreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(confView.getContext());
        
        //Recupero el gcmCode registrado en el celular
        String gcmCode = prefs.getString(Turnos.PROPERTY_REG_ID, "");
		
		// Check if Internet Connection present
        if (!applicationHelper.isConnectingToInternet()) {
             
        	applicationHelper.showAlertDialog(confView.getContext(),
                    "Error en la conexión de internet",
                    "Por favor, contecte internet para poder sincronizar los datos de configuración...", false);
        	
        }else{
        	Boolean result = null;

			//Llamar tarea asincronica para recuperar las actualizaciones de los turnos activos
			TaskWSUpdateConfigurationData taskWSUpdateConfData = new TaskWSUpdateConfigurationData(this);
			taskWSUpdateConfData.execute(gcmCode);

        }
        
		
	}
	
	
	public IConfigurationView getConfView() {
		return this.confView;
	}
	
	
	public HashMap getWSUpdateConfigurationData(String gcmCode) throws UxorException{
		
	    //Llamo al webservice para recuperar la actualización de los datos de configuración
		HashMap hmServerResult = confHelper.retrieveConfigurationData(confView.getContext(), gcmCode);
		
		//Países
		HashMap hmCountries = (HashMap)hmServerResult.get("hmCountries");
		List<Country> lstCountries = (List<Country>)hmCountries.get("lstCountries");
		Boolean resultWSCountryError = (Boolean)hmCountries.get("resultWSCountryError");
		
		//Provincias
		HashMap hmStates = (HashMap)hmServerResult.get("hmStates");
		List<State> lstStates = (List<State>)hmStates.get("lstStates");
		Boolean resultWSStateError = (Boolean)hmStates.get("resultWSStateError");
		
		//Ciudades
		HashMap hmTowns = (HashMap)hmServerResult.get("hmTowns");
		List<Town> lstTowns = (List<Town>)hmTowns.get("lstTowns");
		Boolean resultWSTownError = (Boolean)hmTowns.get("resultWSTownError");
		
		//Tipos de Clientes
		HashMap hmClientTypes = (HashMap)hmServerResult.get("hmClientTypes");
		List<ClientType> lstClientTypes = (List<ClientType>)hmClientTypes.get("lstClientTypes");
		Boolean resultWSClientTypeError = (Boolean)hmClientTypes.get("resultWSClientTypeError");
		
		
		//Clientes
		HashMap hmClients = (HashMap)hmServerResult.get("hmClients");
		List<Client> lstClients = (List<Client>)hmClients.get("lstClients");
		Boolean resultWSClientError = (Boolean)hmClients.get("resultWSClientError");
		
		//Sucursales
		HashMap hmBranchs = (HashMap)hmServerResult.get("hmBranchs");
		List<Branch> lstBranchs = (List<Branch>)hmBranchs.get("lstBranchs");
		Boolean resultWSBranchError = (Boolean)hmBranchs.get("resultWSBranchError");
		
		//Servicios
		HashMap hmServices = (HashMap)hmServerResult.get("hmServices");
		List<Service> lstServices = (List<Service>)hmServices.get("lstServices");
		Boolean resultWSServiceError = (Boolean)hmServices.get("resultWSServiceError");
		
		//Recorre los Países y actualiza/inserta la información.
		if (resultWSCountryError!=null && !resultWSCountryError){
			Log.d(LOGTAG, "Actualización de países");
			if (lstCountries.size()>0){
				confModel.updateCountries(lstCountries);
			}
		}
				
		//Recorre los Provincias y actualiza/inserta la información.
		if (resultWSStateError!=null && !resultWSStateError){
			Log.d(LOGTAG, "Actualización de Provincias");
    		if (lstStates.size()>0){
    			confModel.updateStates(lstStates);
    		}
		}
		
		
		//Recorre las Ciudades y actualiza/inserta la información.
		if (resultWSTownError!=null && !resultWSTownError){
			Log.d(LOGTAG, "Actualización de Ciudades");
    		if (lstTowns.size()>0){
    			confModel.updateTowns(lstTowns);
    		}
		}
		
		//Recorre los Tipos de Clientes y actualiza/inserta la información.
		if (resultWSClientTypeError!=null && !resultWSClientTypeError){
			Log.d(LOGTAG, "Actualización de Tipos de Clientes");
    		if (lstClientTypes.size()>0){
    			confModel.updateClientTypes(lstClientTypes);
    		}
		}
		
				
		//Recorre los Clientes y actualiza/inserta la información.
		if (resultWSClientError!=null && !resultWSClientError){
			Log.d(LOGTAG, "Actualización de Clientes");
    		if (lstClients.size()>0){
    			confModel.updateClients(lstClients);
    		}
		}
		
		//Recorre las Sucursales y actualiza/inserta la información.
		if (resultWSBranchError!=null && !resultWSBranchError){
			Log.d(LOGTAG, "Actualización de Sucursales");
			confModel.updateBranchs(lstBranchs);
		}
		
		//Recorre los Servicios y actualiza/inserta la información.
		if (resultWSServiceError!=null && !resultWSServiceError){
			Log.d(LOGTAG, "Actualización de Servicios");
			confModel.updateServices(lstServices);
		}
		
		
		
		if (resultWSCountryError || resultWSStateError || 
				resultWSTownError || resultWSClientTypeError || 
				resultWSClientError || resultWSBranchError || resultWSServiceError){
			hmServerResult.put("resultWithErrors", Boolean.TRUE);
			hmServerResult.put("msg", "Problemas de conexión. Intente nuevamente...");
		}else{
			hmServerResult.put("resultWithErrors", Boolean.FALSE);
		}
		
		return hmServerResult;
		
	}


	
	
}
