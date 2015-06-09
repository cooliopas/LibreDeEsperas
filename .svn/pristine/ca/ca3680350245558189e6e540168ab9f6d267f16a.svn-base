package com.uxor.turnos.presenter;

import java.math.BigDecimal;
import java.util.List;

import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.ITurnosModel;
import com.uxor.turnos.model.TurnosModel;
import com.uxor.turnos.view.IOneTurnView;
import com.uxor.turnos.view.dto.LstTurnDto;

/**
 * @author alejandroespina
 *
 */
public class OneTurnPresenter {
	
	private IOneTurnView oneTurnView;
	private ITurnosModel turnosModel;

	
	public OneTurnPresenter(IOneTurnView view){
		this(view, new TurnosModel());
	}
	
	public OneTurnPresenter(IOneTurnView view, ITurnosModel model){
		this.oneTurnView = view;
		this.turnosModel = model;
		
	}
	
	
	/**
	 * @throws UxorException
	 */
	public void retrieveOneTurn() throws UxorException{
		//Seteo el turno a la vista
		List<LstTurnDto> vcTurns = this.turnosModel.getListadoTurnos();
		
		if (vcTurns!=null && vcTurns.size()>0){
		
			this.oneTurnView.setLstTurnDto(vcTurns.get(0));
			
			//Recupero el turno para actualizar datos en la vista
			//TODO: Cambiar clase LstTurnDto por la clase de dominio Turn. Dentro del componente presenter no se deben usar objetos Data Tranfer Object que son utilizados solo para pasarlos a la vista. 
			LstTurnDto turnDto = vcTurns.get(0);
					
			//Actualizo los campos en la vista
			//-----------------------------------------------------------------------------
			//TurnBefore
			this.oneTurnView.setTurnBefore(turnDto.getTurnBefore());
			
			//WaitingTime
			this.oneTurnView.setWaitingTime(turnDto.getWaitingTime());
			
			//TurnNumber
			String turnLetter = turnDto.getTurnLetter()!=null ? turnDto.getTurnLetter() : "";
			this.oneTurnView.setTurnNumber(turnLetter.concat(turnDto.getTurnNumber().toString()));
			
			//ServiceTypeDesc
			this.oneTurnView.setServiceTypeDesc(turnDto.getmServiceDesc());
			
			//ClientName
			this.oneTurnView.setClientName(turnDto.getClientName());
			
			//BranchAddress
			this.oneTurnView.setBranchAddress(turnDto.getBranchAddress());
			
			//ImageLogoClient
			this.oneTurnView.setImgLogoClient(turnDto.getClientImageName());
		
		}
	}
	
	
	
	
	
}
