package com.uxor.turnos.model;

/**
 * Interface para realizar consultas relacionadas con las ciudades
 * 
 * @author      Alejandro Espina <alejandroespina@uxorit.com>
 * @version     1.0                 (current version number of program)
 * @since       2014-02-06          (the version of the package this class was first added to)
 */

import java.util.List;

import com.uxor.turnos.domain.Town;
import com.uxor.turnos.domain.exception.UxorException;

public interface ITownModel {

	
	/*
	 * Devuelve las ciudades para cargar en un combo
	 * @return Lista de ciudades 
	 * @see com.uxor.turnos.model.ITownModel#retrieveTownsCbo()
	 */
	public List<Town> retrieveTownsCbo() throws UxorException;
	public Town getTownById(Long townId) throws UxorException;

	
}
