package com.uxor.turnos.presenter;

import java.math.BigDecimal;

import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.BranchModel;
import com.uxor.turnos.model.IBranchModel;
import com.uxor.turnos.view.IBranchView;

public class BranchPresenter {

	
	private IBranchView branchView;
	private IBranchModel branchModel;
	
	
	public BranchPresenter(IBranchView view){
		this(view, new BranchModel());
	}
	
	public BranchPresenter(IBranchView view, IBranchModel model){
		this.branchView = view;
		this.branchModel = model;
	}
	
	/*
	 * Devuelve el listado de sucursales con su cliente asociado
	 */
	public void retrieveBranchList(BigDecimal clientTypeId, Long townId, Boolean myTown) throws UxorException{
		
		//Actualizo la grilla de sucursales
		this.branchView.setVcBranchs(this.branchModel.retrieveBranchs(clientTypeId, townId, myTown));
	}
	
}
