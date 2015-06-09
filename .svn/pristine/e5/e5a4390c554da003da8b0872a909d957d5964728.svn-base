package com.uxor.turnos.model;

/**
 * Interface para realizar consultas relacionadas con los turnos
 * 
 * @author      Alejandro Espina <alejandroespina@uxorit.com>
 * @version     1.0                 (current version number of program)
 * @since       2014-02-06          (the version of the package this class was first added to)
 */

import java.math.BigDecimal;
import java.util.List;

import com.uxor.turnos.domain.ServerNew;
import com.uxor.turnos.domain.Service;
import com.uxor.turnos.domain.Turno;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.view.dto.LstTurnDto;

public interface ITurnosModel {

	/*
	 * Devuelve el listado de turnos habilitados (Status='Hab' y Turn_Date>=Fecha Actual)
	 * @return Turnos habilitados 
	 * @see com.uxor.turnos.model.ITurnosModel#getListadoTurnos()
	 */
	public List<LstTurnDto> getListadoTurnos() throws UxorException;
	
	public void updateTurnos(BigDecimal serverTurnId, BigDecimal waitingTime, BigDecimal turnsBefore, String turnStatus) throws UxorException;
	
	public BigDecimal insertServerNew(ServerNew serverNew) throws UxorException;
	
	public int getCountTurnos() throws UxorException;
	
	public List<Service> retrieveServicesCbo() throws UxorException;
	
	public List<Service> retrieveServicesCbo(BigDecimal branchId) throws UxorException;

	
	/*
	 * Inserta un turno.
	 * @param turn Turno a insertar
	 * @see com.uxor.turnos.model.ITurnosModel#insertTurn(Turno turn)
	 * 
	 */
	public BigDecimal insertTurn(Turno turn) throws UxorException;
	
	
	/*
	 * Actualiza el estado de un turno.
	 * @param turnId Id del turno insertado
	 * @param turnStatus Estado nuevo
	 * @see com.uxor.turnos.model.ITurnosModel#updateTurnStatus(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)
	 * 
	 */
	public void updateTurnStatus(BigDecimal turnId, String turnStatus) throws UxorException;
	
	/*
	 * Actualiza el turno con la respuesta enviada del servidor (último paso cuando se agrega un turno manualmente)
	 * @param turnId Id del turno insertado
	 * @param serverTurnId Id del turno en el servidor
	 * @param waitingTime Tiempo estimado de espera
	 * @param turnsBefore Cantidad de turnos antes del turno actual
	 * @param turnStatus Estado del turno 
	 * @see com.uxor.turnos.model.ITurnosModel#updateTurnos(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)
	 * 
	 */
	public void updateTurn(BigDecimal turnId, BigDecimal serverTurnId, BigDecimal waitingTime, BigDecimal turnsBefore, String turnStatus) throws UxorException;
	
	/*
	 * Devuelve la cantidad de turnos habilitados
	 * @return Cantidad de turnos pendientes 
	 * @see com.uxor.turnos.model.ITurnosModel#retrieveAllBranchs()
	 */
	public int getPendingTurnsQty() throws UxorException;
	
	
	/*
	 * Devuelve la cantidad de turnos deshabilitados
	 * @return Cantidad de turnos deshabilitados 
	 * @see com.uxor.turnos.model.ITurnosModel#retrieveAllBranchs()
	 */
	public int getDisabledTurnsQty() throws UxorException;
	
	
	/*
	 * Devuelve el listado de turnos vencidos
	 * @return Listado de turnos vencidos 
	 * @see com.uxor.turnos.model.ITurnosModel#retrieveAllBranchs()
	 */
	public List<LstTurnDto> getInactiveTurns(Boolean currentMonth) throws UxorException;
	
	
	/*
	 * Devuelve la cantidad de turnos activos del dia actual
	 * @return Cantidad de turnos activos 
	 * @see com.uxor.turnos.model.ITurnosModel#retrieveAllBranchs()
	 */
	public int getActiveTurnsQty() throws UxorException;
	
	/*
	 * Obtiene un turno.
	 * @param turnId ID del Turno a obtener
	 */
	public Turno getTurn(Long turnId) throws UxorException;
	
	public List<ServerNew> getServerNewsByServerTurnId(Long turnId) throws UxorException;
	
	public void updateServiceStatus(BigDecimal serviceId, Boolean serviceStatus) throws UxorException;

	public BigDecimal getServiceIdByServerTurnId(Long serverTurnId) throws UxorException;
}
