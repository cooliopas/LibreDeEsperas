package com.uxor.turnos.presenter;

import java.math.BigDecimal;
import java.util.HashMap;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.uxor.turnos.domain.Turno;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.ITurnosModel;
import com.uxor.turnos.model.TurnosModel;
import com.uxor.turnos.presenter.helper.ApplicationHelper;
import com.uxor.turnos.presenter.helper.GcmHelper;
import com.uxor.turnos.presenter.helper.IApplicationHelper;
import com.uxor.turnos.presenter.helper.IGcmHelper;
import com.uxor.turnos.presenter.helper.ITurnosHelper;
import com.uxor.turnos.presenter.helper.TurnosHelper;
import com.uxor.turnos.util.TaskWSGetServiceCongestion;
import com.uxor.turnos.util.TaskWSNewServerTurn;
import com.uxor.turnos.util.Turnos;
import com.uxor.turnos.view.IAddTurnView;

public class AddTurnPresenter {

	private IAddTurnView addTurnView;
	private ITurnosModel turnosModel;
	private IGcmHelper gcmHelper;
	private IApplicationHelper applicationHelper;
	private ITurnosHelper turnosHelper;
	
	
	public AddTurnPresenter(IAddTurnView view){
		this(view, new TurnosModel(), GcmHelper.getInstance(), ApplicationHelper.getInstance(), TurnosHelper.getInstance());
	}
	
	public AddTurnPresenter(IAddTurnView addTurnView, ITurnosModel turnosModel, IGcmHelper gcmHelper, IApplicationHelper applicationHelper, ITurnosHelper turnosHelper){
		this.addTurnView = addTurnView;
		this.turnosModel = turnosModel;
		this.gcmHelper = gcmHelper;
		this.applicationHelper = applicationHelper;
		this.turnosHelper = turnosHelper;
	}
	
	/*
	 * Devuelve el listado de servicios para cargar en el combo
	 */
	public void retrieveServicesCbo(BigDecimal branchId) throws UxorException{
		
		//Actualizo la grilla de sucursales
		this.addTurnView.setVcServicesCbo(this.turnosModel.retrieveServicesCbo(branchId));
	}
	
	public void insertTurn(Turno turn) throws UxorException{
		
		//Inserto el turno
		//BigDecimal turnId = this.turnosModel.insertTurn(turn);
		
		//Le seteo el id del turno
		//turn.setTurnId(turnId);
		
		// Check if Internet Connection present
        if (!gcmHelper.isConnectingToInternet()) {
             
        	applicationHelper.showAlertDialog(addTurnView.getContext() ,
                    "Error en la conexión de internet",
                    "Por favor, contecte internet para poder sincronizar el turno...", false);
        	
        	//Cambia el estado del turno a "Sin sincronizar (SIN)"
        	//this.turnosModel.updateTurnStatus(turnId, Turnos.TURN_STATUS_SIN);
        }else{
        	//Llamar tarea asincronica para agregar el turno en el servidor
        	TaskWSNewServerTurn taskWSNewServerTurn = new TaskWSNewServerTurn(this);
        	taskWSNewServerTurn.execute(turn);
        }
		
		//return turnId;
	}
	
	public HashMap newServerTurn(Turno turn) throws UxorException{
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(addTurnView.getContext());
		
		//Recupero el gcmCode y el id del usuario de la sharePreferences
	    String gcmCode = prefs.getString(Turnos.PROPERTY_REG_ID, "");
	    Integer userId = prefs.getInt(Turnos.PROPERTY_USER_ID, Integer.MIN_VALUE);
		
	    //Llamo al webservice Alta de Turnos
		HashMap hmServerTurn = turnosHelper.newServerTurn(addTurnView.getContext(), turn.getClientServiceId(), gcmCode, turn.getTurnDate(), turn.getTurnNumber(), userId);
		Turno serverTurn = (Turno)hmServerTurn.get("serverTurn");
		Boolean result = (Boolean)hmServerTurn.get("result");
		
		if (result){
			
			//Inserto el turno en la base local del celular
			BigDecimal turnId = this.turnosModel.insertTurn(turn);
					
			//Le seteo el id del turno
			turn.setTurnId(turnId);
			
			//Actualiza el turno con la info recibida del servidor
			this.turnosModel.updateTurn(turn.getTurnId(), serverTurn.getServerTurnId(), serverTurn.getWaitingTime(), serverTurn.getTurnsBefore(), Turnos.TURN_STATUS_HAB);
			
			//TODO: Cambiar estado del turno a "Habilitado"
	    	//this.turnosModel.updateTurnStatus(turn.getTurnId(), TURN_STATUS_HAB);
		}
		
		return hmServerTurn;
	}

	public IAddTurnView getAddTurnView() {
		return addTurnView;
	}
	
	
	public void serviceCongestion(BigDecimal serviceId) throws UxorException{		
		// Check if Internet Connection present
        if (!gcmHelper.isConnectingToInternet()) {
             
        	applicationHelper.showAlertDialog(addTurnView.getContext() ,
                    "Error en la conexión de internet",
                    "Por favor, contecte internet para poder obtener la congestión del servicio...", false);
        	
        }else{
        	//Llamar tarea asincronica para agregar el turno en el servidor
        	TaskWSGetServiceCongestion taskWSGetServiceCongestion = new TaskWSGetServiceCongestion(this);
        	taskWSGetServiceCongestion.execute(serviceId);
        }
		
		//return turnId;
	}
	
	public HashMap getServiceCongestion(BigDecimal serviceId) throws UxorException {
		try
		{
			// Llamo al webservice Cancelar Turnos
			HashMap hmServiceCongestion = turnosHelper.getServiceCongestion(
					addTurnView.getContext(), serviceId);
					    			
			return hmServiceCongestion;
			
		} catch (Exception e) {
			Log.e("AddTurnPresenter", e.getMessage());
			throw new UxorException("Se produjo un error",e);
		}
	}

	
	
}
