package com.uxor.turnos.presenter;

import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.ITurnosModel;
import com.uxor.turnos.model.TurnosModel;
import com.uxor.turnos.presenter.helper.ITurnosHelper;
import com.uxor.turnos.presenter.helper.TurnosHelper;
import com.uxor.turnos.view.ITurnosView;

public class TurnosPresenter {
	
	private ITurnosView turnosView;
	private ITurnosModel turnosModel;
	private ITurnosHelper turnosHelper;

	
	public TurnosPresenter(ITurnosView view){
		this(view, new TurnosModel(), new TurnosHelper());
	}
	
	public TurnosPresenter(ITurnosView view, ITurnosModel model, ITurnosHelper turnosHelper){
		this.turnosView = view;
		this.turnosModel = model;
		this.turnosHelper = turnosHelper;
	}
	
	public void obtenerListadoTurnos() throws UxorException{
		
		//Actualizo la grilla de turnos
		this.turnosView.setLstTurnos(this.turnosModel.getListadoTurnos());
	}
	
	
	public void getInactiveTurns(Boolean currentMonth) throws UxorException{
		
		//Actualizo la grilla de turnos inactivos
		this.turnosView.setLstTurnos(this.turnosModel.getInactiveTurns(currentMonth));
	}
	
	
	
	
	
}
