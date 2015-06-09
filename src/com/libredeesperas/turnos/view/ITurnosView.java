package com.uxor.turnos.view;

import java.util.List;

import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.view.dto.LstTurnDto;

public interface ITurnosView {

	public void setLstTurnos(List<LstTurnDto> listadoTurnos) throws UxorException;
	public void updateListView();
	
}
