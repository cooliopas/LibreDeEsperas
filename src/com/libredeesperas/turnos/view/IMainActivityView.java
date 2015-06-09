package com.uxor.turnos.view;

import java.util.List;

import com.uxor.turnos.domain.Menu;
import com.uxor.turnos.domain.exception.UxorException;

public interface IMainActivityView {

	public void setLstOpcionesMenuIzquierdo(List<Menu> opcionesMenu) throws UxorException;
		
}
