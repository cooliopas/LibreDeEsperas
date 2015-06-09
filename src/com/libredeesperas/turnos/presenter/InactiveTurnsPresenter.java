package com.uxor.turnos.presenter;

import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.ITurnosModel;
import com.uxor.turnos.model.TurnosModel;
import com.uxor.turnos.view.IInactiveTurnsView;

public class InactiveTurnsPresenter {
	
	private IInactiveTurnsView inactiveTurnsView;
	private ITurnosModel turnosModel;

	
	public InactiveTurnsPresenter(IInactiveTurnsView view){
		this(view, new TurnosModel());
	}
	
	public InactiveTurnsPresenter(IInactiveTurnsView view, ITurnosModel model){
		this.inactiveTurnsView = view;
		this.turnosModel = model;
	}
	
	
	public void getInactiveTurns() throws UxorException{
		
		//Actualizo la grilla de turnos inactivos del mes actual
		this.inactiveTurnsView.setInactiveCurrentMonthTurnsList((this.turnosModel.getInactiveTurns(Boolean.TRUE)));
		
		
		//Actualizo la grilla de turnos inactivos de meses anteriores
		this.inactiveTurnsView.setInactiveOtherstMonthTurnsList((this.turnosModel.getInactiveTurns(Boolean.FALSE)));
				
		
	}
	
	
	
	
	
}
