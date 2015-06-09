package com.uxor.turnos.model;

/**
 * Interface para realizar consultas relacionadas con las sucursales
 * 
 * @author      Alejandro Espina <alejandroespina@uxorit.com>
 * @version     1.0                 (current version number of program)
 * @since       2014-02-06          (the version of the package this class was first added to)
 */

import java.math.BigDecimal;
import java.util.List;

import com.uxor.turnos.domain.Branch;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.view.dto.BranchsQtyDto;

public interface IBranchModel {

	
	/*
	 * Devuelve todas las sucursales del sistema
	 * @return Lista de todas las sucursales 
	 * @see com.uxor.turnos.model.IBranchModel#retrieveAllBranchs()
	 */
	public List<Branch> retrieveAllBranchs() throws UxorException;
	
	
	/*
	 * Devuelve el listado de sucursales para una ciudad y tipo de cliente
	 * 
	 * @param clientTypeId Tipo de cliente
	 * @param townId Ciudad configurada por defecto
	 * @return Lista de Sucursales
	 * @see com.uxor.turnos.model.IBranchModel#retrieveBranchs()
	 */
	public List<Branch> retrieveBranchs(BigDecimal clientTypeId, Long townId, Boolean inMyTown) throws UxorException;
	
	
	/*
	 * Devuelve la cantidad de sucursales por tipo de cliente
	 * 
	 * @param townId Ciudad configurada por defecto
	 * @param inMyTown Flag para determinar si la consulta se realiza para la ciudad o para el resto del país
	 * @return  
	 * @see com.uxor.turnos.model.IBranchModel#retrieveBranchsBySupplier()
	 */
	public List<BranchsQtyDto> retrieveBranchsQtyList(Long townId, Boolean inMyTown) throws UxorException;
	
}
