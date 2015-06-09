package com.uxor.turnos.model;

/**
 * Interface para actualizar los datos de configuración del servidor
 * 
 * @author      Alejandro Espina <alejandroespina@uxorit.com>
 * @version     1.0                 (current version number of program)
 * @since       2015-02-09          (the version of the package this class was first added to)
 */

import java.util.List;

import com.uxor.turnos.domain.Branch;
import com.uxor.turnos.domain.Client;
import com.uxor.turnos.domain.ClientType;
import com.uxor.turnos.domain.Country;
import com.uxor.turnos.domain.Service;
import com.uxor.turnos.domain.State;
import com.uxor.turnos.domain.Town;
import com.uxor.turnos.domain.exception.UxorException;

public interface IConfigurationModel {

	
	/*
	 * Actualiza los Países
	 * 
	 * @param lstCountries Lista con los países actualizados
	 * @return void
	 * @see com.uxor.turnos.model.IConfigurationModel#updateCountries()
	 */
	public void updateCountries(List<Country> lstCountry) throws UxorException;
	
	/*
	 * Actualiza las Provincias
	 * 
	 * @param lstStates Lista con las provincias actualizadas
	 * @return void
	 * @see com.uxor.turnos.model.IConfigurationModel#updateStates()
	 */
	public void updateStates(List<State> lstStates) throws UxorException;
	
	/*
	 * Actualiza las Ciudades
	 * 
	 * @param lstStates Lista con las ciudades actualizadas
	 * @return void
	 * @see com.uxor.turnos.model.IConfigurationModel#updateTowns()
	 */
	public void updateTowns(List<Town> lstTowns) throws UxorException;
	
	/*
	 * Actualiza los Tipos de Clientes
	 * 
	 * @param lstClientTypes Lista con los Tipos de Clientes
	 * @return void
	 * @see com.uxor.turnos.model.IConfigurationModel#updateClientTypes()
	 */
	public void updateClientTypes(List<ClientType> lstClientTypes) throws UxorException;
	
	/*
	 * Actualiza los Clientes
	 * 
	 * @param lstClients Lista con los Clientes
	 * @return void
	 * @see com.uxor.turnos.model.IConfigurationModel#updateClients()
	 */
	public void updateClients(List<Client> lstClients) throws UxorException;
	
	
	/*
	 * Actualiza las Sucursales
	 * 
	 * @param lstBranchs Lista con las sucursales actualizadas
	 * @return void
	 * @see com.uxor.turnos.model.IConfigurationModel#updateBranchs()
	 */
	public void updateBranchs(List<Branch> lstBranchs) throws UxorException;
	
	
	/*
	 * Actualiza los Servicios
	 * 
	 * @param lstServices Lista con los Servicios
	 * @return void
	 * @see com.uxor.turnos.model.IConfigurationModel#updateClients()
	 */
	public void updateServices(List<Service> lstServices) throws UxorException;
	
	
}
