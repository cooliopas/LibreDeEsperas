package com.uxor.turnos.presenter;

import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.BranchModel;
import com.uxor.turnos.model.IBranchModel;
import com.uxor.turnos.view.IBranchsQtyView;

public class BranchsQtyPresenter {

	
	private IBranchsQtyView branchsQtyView;
	private IBranchModel branchModel;
	
	
	public BranchsQtyPresenter(IBranchsQtyView view){
		this(view, new BranchModel());
	}
	
	public BranchsQtyPresenter(IBranchsQtyView view, IBranchModel model){
		this.branchsQtyView = view;
		this.branchModel = model;
	}
	
	/*
	 * Devuelve el listado de sucursales con su cliente asociado
	 */
	public void retrieveBranchsQtyList(Long townId, Boolean inMyTown) throws UxorException{
		
		if (inMyTown){
			//Actualizo la grilla de sucursales en Mi Ciudad
			this.branchsQtyView.setVcBranchsQty(this.branchModel.retrieveBranchsQtyList(townId, inMyTown));
		}else{
			//Actualizo la grilla de sucursales Resto del País
			this.branchsQtyView.setVcBranchsQtyAllRest(this.branchModel.retrieveBranchsQtyList(townId, inMyTown));
		}
	}
	
}
