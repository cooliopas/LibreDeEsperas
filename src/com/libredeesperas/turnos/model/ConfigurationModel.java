package com.uxor.turnos.model;

/**
 * Implementación de los métodos de la interface IBranchModel
 * 
 * @author      Alejandro Espina <alejandroespina@uxorit.com>
 * @version     1.0                 (current version number of program)
 * @since       2014-02-06          (the version of the package this class was first added to)
 */

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.uxor.turnos.domain.Branch;
import com.uxor.turnos.domain.Client;
import com.uxor.turnos.domain.ClientType;
import com.uxor.turnos.domain.Country;
import com.uxor.turnos.domain.Service;
import com.uxor.turnos.domain.State;
import com.uxor.turnos.domain.Town;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.helper.TurnosSqlLiteHelper;
import com.uxor.turnos.util.Turnos;

public class ConfigurationModel implements IConfigurationModel {
	private TurnosSqlLiteHelper turnosSqlLiteHelper;
	
	public ConfigurationModel(){
		this.turnosSqlLiteHelper = TurnosSqlLiteHelper.getInstance(Turnos.getAppContext());
	}

	

	@Override
	public void updateCountries(List<Country> lstCountry) throws UxorException {
		
		Log.d("updateCountries", "Actualización de Países");
		
		try {
			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();

			
			for (Country country : lstCountry) {
								
				//Consulta sql
				String sql = "Select * " + 
						"From MCountries " +
						"Where _id=?";
				
				String[] args = new String[] {country.getId().toString()};
				//Armo la consulta para determinar si ya existe la sucursal en la base
				Cursor cursor = db.rawQuery(sql,args);
				
				ContentValues countryValues = new ContentValues();
				countryValues.put("description", country.getDescription());

				
				//Si existe en la base se actualiza los datos si no se inserta.
				if (cursor.moveToFirst()) {
					
					String[] argsUpdate = new String[]{country.getId().toString()};
					db.update("MCountries", countryValues, "_id=?", argsUpdate);
					
				}else{
					db.insert("MCountries", null, countryValues);
				}
				
				cursor.close();
				
			}
			
		} catch(SQLiteException e) {
	        Log.d("updateCountries", e.getMessage());
	        throw new UxorException("Error de base de datos");
		}
		
	}

	@Override
	public void updateStates(List<State> lstStates) throws UxorException {
		Log.d("updateStates", "Actualización de Provincias");
		
		
		try {
			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();

			
			for (State state : lstStates) {
								
				//Consulta sql
				String sql = "Select * " + 
						"From MProvinces " +
						"Where _id=?";
				
				String[] args = new String[] {state.getId().toString()};
				//Armo la consulta para determinar si ya existe la sucursal en la base
				Cursor cursor = db.rawQuery(sql,args);
				
				ContentValues stateValues = new ContentValues();
				stateValues.put("description", state.getDescription());
				stateValues.put("countryId", state.getCountryId().toString());

				
				//Si existe en la base se actualiza los datos si no se inserta.
				if (cursor.moveToFirst()) {
					
					String[] argsUpdate = new String[]{state.getId().toString()};
					db.update("MProvinces", stateValues, "_id=?", argsUpdate);
					
				}else{
					db.insert("MProvinces", null, stateValues);
				}
				
				cursor.close();
				
			}
			
		} catch(SQLiteException e) {
	        Log.d("updateStates", e.getMessage());
	        throw new UxorException("Error de base de datos");
		}
	}
	
	@Override
	public void updateTowns(List<Town> lstTowns) throws UxorException {
		Log.d("updateTowns", "Actualización de Ciudades");
		
		try {
			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();

			
			Log.d("Tamaño vector ciudades: ", String.valueOf(lstTowns.size()));
			
			int i =0;
			for (Town town : lstTowns) {
				
				
				//Consulta sql
				String sql = "Select * " + 
						"From MTowns " +
						"Where _id=?";
				
				String[] args = new String[] {town.getId().toString()};
				
				//Armo la consulta para determinar si ya existe la sucursal en la base
				Cursor cursor = db.rawQuery(sql,args);
				
				ContentValues townValues = new ContentValues();
				townValues.put("description", town.getDescription());
				townValues.put("provinceId", town.getProvinceId().toString());

				
				//Si existe en la base se actualiza los datos si no se inserta.
				if (cursor.moveToFirst()) {
					
					String[] argsUpdate = new String[]{town.getId().toString()};
					db.update("MTowns", townValues, "_id=?", argsUpdate);
					
				}else{
					db.insert("MTowns", null, townValues);
				}
				
				i++;
				Log.d("Ciudad Nro: ", String.valueOf(i));
				
				cursor.close();
				
			}
			
		} catch(SQLiteException e) {
	        Log.d("updateTowns", e.getMessage());
	        throw new UxorException("Error de base de datos");
		}
	}
	
	
	@Override
	public void updateClientTypes(List<ClientType> lstClientTypes)
			throws UxorException {
		
		Log.d("updateClientTypes", "Actualización de Tipos de Clientes");
		
		try {
			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();

			for (ClientType clientType : lstClientTypes) {
								
				//Consulta sql
				String sql = "Select * " + 
						"From Client_Types " +
						"Where _id=?";
				
				String[] args = new String[] {clientType.getId().toString()};
				//Armo la consulta para determinar si ya existe la sucursal en la base
				Cursor cursor = db.rawQuery(sql,args);
				
				ContentValues clientTypeValues = new ContentValues();
				clientTypeValues.put("description", clientType.getDescription());

				
				//Si existe en la base se actualiza los datos si no se inserta.
				if (cursor.moveToFirst()) {
					
					String[] argsUpdate = new String[]{clientType.getId().toString()};
					db.update("Client_Types", clientTypeValues, "_id=?", argsUpdate);
					
				}else{
					db.insert("Client_Types", null, clientTypeValues);
				}
				
				cursor.close();
				
			}
			
		} catch(SQLiteException e) {
	        Log.d("updateClientTypes", e.getMessage());
	        throw new UxorException("Error de base de datos");
		}
		
	}

	@Override
	public void updateClients(List<Client> lstClients) throws UxorException {
		
		Log.d("updateClients", "Actualización de Clientes");
		
		try {
			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();

			for (Client client : lstClients) {
								
				//Consulta sql
				String sql = "Select * " + 
						"From Clients " +
						"Where _id=?";
				
				String[] args = new String[] {client.getId().toString()};
				//Armo la consulta para determinar si ya existe la sucursal en la base
				Cursor cursor = db.rawQuery(sql,args);
				
				ContentValues clientValues = new ContentValues();
				clientValues.put("Name", client.getName());
				clientValues.put("Address", client.getAddress());
				clientValues.put("ClientImage", client.getImageId());
				clientValues.put("ClientType_id", client.getClientTypeId().toString());
				clientValues.put("State", client.getState());

				
				//Si existe en la base se actualiza los datos si no se inserta.
				if (cursor.moveToFirst()) {
					
					String[] argsUpdate = new String[]{client.getId().toString()};
					db.update("Clients", clientValues, "_id=?", argsUpdate);
					
				}else{
					db.insert("Clients", null, clientValues);
				}
				
				cursor.close();
				
			}
			
		} catch(SQLiteException e) {
	        Log.d("updateClients", e.getMessage());
	        throw new UxorException("Error de base de datos");
		}
		
	}

	
	@Override
	public void updateBranchs(List<Branch> lstBranchs) throws UxorException {
		
		
		try {
			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();

			
			for (Branch branch : lstBranchs) {
								
				//Consulta sql
				String sql = "Select * " + 
						"From Branchs " +
						"Where _id=?";
				
				String[] args = new String[] {branch.getId().toString()};
				//Armo la consulta para determinar si ya existe la sucursal en la base
				Cursor cursor = db.rawQuery(sql,args);
				
				ContentValues branchValues = new ContentValues();
				branchValues.put("Alias", branch.getAlias());
				branchValues.put("Address", branch.getAddress());
				branchValues.put("City_id", branch.getCityId().toString());
				branchValues.put("PhoneNumber", branch.getPhoneNumber());
				branchValues.put("Client_id", branch.getClient().getId().toString());
				branchValues.put("State", branch.getState());
				
				//Si existe en la base se actualiza los datos si no se inserta.
				if (cursor.moveToFirst()) {
					
					String[] argsUpdate = new String[]{branch.getId().toString()};
					db.update("Branchs", branchValues, "_id=?", argsUpdate);
					
				}else{
					db.insert("Branchs", null, branchValues);
				}
				
				cursor.close();
				
			}
			
		} catch(SQLiteException e) {
	        Log.d("updateTurnos", e.getMessage());
	        throw new UxorException("Error de base de datos");
		}
		
	}
	
	
	@Override
	public void updateServices(List<Service> lstServices) throws UxorException {
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
		
		try {
			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();

			
			for (Service service : lstServices) {
								
				//Consulta sql
				String sql = "Select * " + 
						"From MServices " +
						"Where _id=?";
				
				String[] args = new String[] {service.getId().toString()};
				//Armo la consulta para determinar si ya existe la sucursal en la base
				Cursor cursor = db.rawQuery(sql,args);
				
				
				ContentValues serviceValues = new ContentValues();
				serviceValues.put("_id", service.getId().toString());
				serviceValues.put("Aditional_Info", service.getAditionalInfo());
				serviceValues.put("BackLog", service.getBackLog());
				serviceValues.put("Branch_id", service.getBranchId()!=null ? service.getBranchId().toString() : null);
				serviceValues.put("confirm_Cancel_Turn", service.getConfirmCancelTurn()!=null ? service.getConfirmCancelTurn().toString() : null);
				serviceValues.put("Description", service.getDescription());
				serviceValues.put("Enabled", service.getEnabled().toString());						
				serviceValues.put("Last_Update", sf.format(service.getLastUpadate()));
				serviceValues.put("Letter", service.getLetter());
				serviceValues.put("Number_Qty", service.getNumberQty().toString());
				serviceValues.put("Waiting_Time", service.getWaitingTime()!=null ? service.getWaitingTime().toString() : null);
				serviceValues.put("State", service.getState());
				
				
				//Si existe en la base se actualiza los datos si no se inserta.
				if (cursor.moveToFirst()) {
					
					String[] argsUpdate = new String[]{service.getId().toString()};
					db.update("MServices", serviceValues, "_id=?", argsUpdate);
					
				}else{
					db.insert("MServices", null, serviceValues);
				}
				
				cursor.close();
				
			}
			
		} catch(SQLiteException e) {
	        Log.d("updateTurnos", e.getMessage());
	        throw new UxorException("Error de base de datos");
		}
		
	}

	
	

}
