package com.uxor.turnos.model;

/**
 * Implementación de los métodos de la interface IBranchModel
 * 
 * @author      Alejandro Espina <alejandroespina@uxorit.com>
 * @version     1.0                 (current version number of program)
 * @since       2014-02-06          (the version of the package this class was first added to)
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.uxor.turnos.domain.Branch;
import com.uxor.turnos.domain.Client;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.helper.TurnosSqlLiteHelper;
import com.uxor.turnos.util.Turnos;
import com.uxor.turnos.view.dto.BranchsQtyDto;

public class BranchModel implements IBranchModel {
	private TurnosSqlLiteHelper turnosSqlLiteHelper;
	
	public BranchModel(){
		this.turnosSqlLiteHelper = TurnosSqlLiteHelper.getInstance(Turnos.getAppContext());
	}
	
	
	@Override
	public List<Branch> retrieveBranchs(BigDecimal clientTypeId, Long townId, Boolean inMyTown)
			throws UxorException {
				List<Branch> branchList = new ArrayList<Branch>();
				String operator;
				
				try{
					//Obtengo la base de datos
					SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
					
					if (inMyTown){
						operator = "="; //En mi ciudad
					}else{
						operator = "<>"; //Resto del país
					}
					
					//Armo la consulta para recuperar las sucursales por ciudad y tipo de cliente
					Cursor c = db.rawQuery("Select b._id, " +
							"b.Alias, " +
							"b.Address, " +
							"b.City_id, " +
							"b.Client_id, " +
							"b.PhoneNumber, " +
							"c.Name as client_name, " +
							"c.Address as client_address, " +
							"c.ClientImage as client_image, " +
							"c.ClientType_id as client_type_id, " +
							"b.State as branch_state, " +
							"c.State as client_state " +
							"From Branchs as b, Clients as c " +
							"Where b.client_id = c._id and " +
							"b.State='HAB' and " +
							"c.ClientType_id=" + clientTypeId.toString().concat(" and ") +
							"b.City_id" + operator.concat(townId.toString()) + " Order by c.Name, b.Alias" ,null);
					
				
					//Nos aseguramos de que existe al menos un registro
					if (c.moveToFirst()) {
					     //Recorremos el cursor hasta que no haya más registros
					     do {
					    	 
					    	 //Creo la sucursal
					    	 Branch aBranch = new Branch(new BigDecimal(c.getInt(c.getColumnIndexOrThrow("_id"))), 
					    			 										c.getString(c.getColumnIndexOrThrow("Alias")),
					    			 										c.getString(c.getColumnIndexOrThrow("Address")),
					    			 										new BigDecimal(c.getInt(c.getColumnIndexOrThrow("City_id"))),
					    	 												c.getString(c.getColumnIndexOrThrow("PhoneNumber")),
					    	 												c.getString(c.getColumnIndexOrThrow("branch_state")));
					    	 
					    	 //Creo el cliente
					    	 Client aClient = new Client(new BigDecimal(c.getInt(c.getColumnIndexOrThrow("Client_id"))),
					    			 c.getString(c.getColumnIndexOrThrow("client_name")),
					    			 c.getString(c.getColumnIndexOrThrow("client_address")),
					    			 c.getString(c.getColumnIndexOrThrow("client_image")),
					    			 new BigDecimal(c.getInt(c.getColumnIndexOrThrow("client_type_id"))),
					    			 c.getString(c.getColumnIndexOrThrow("client_state")));
					    	 
					    	 //Seteo el cliente a la sucursal
					    	 aBranch.setClient(aClient);
					    	 
					    	 //Agrego la sucursal a la lista
					    	 branchList.add(aBranch);
					    	 
					     } while(c.moveToNext());
					}
				 
				
					//Cierro el cursor
					c.close();
					
					 //Cerramos la base de datos
			        db.close();
			        
				} catch (SQLException e) {
					Log.e("BranchModel", e.getMessage());
					throw new UxorException("Error de base de datos",e);
				}
		        
				return branchList;
	}
	
	@Override
	public List<Branch> retrieveAllBranchs()
			throws UxorException {
				List<Branch> branchList = new ArrayList<Branch>();
				
				
				try{
					//Obtengo la base de datos
					SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
					
					//Armo la consulta para recuperar todas las sucursales
					Cursor c = db.rawQuery("Select b._id, " +
							"b.Alias, " +
							"b.Address, " +
							"b.City_id, " +
							"b.Client_id, " +
							"b.PhoneNumber, " +
							"c.Name as client_name, " +
							"c.Address as client_address, " +
							"c.ClientType_id, " +
							"b.State as branch_state, " +
							"c.State as client_state " +
							"From Branchs as b, Clients as c " +
							"Where b.client_id = c._id",null);
				
				
					//Nos aseguramos de que existe al menos un registro
					if (c.moveToFirst()) {
					     //Recorremos el cursor hasta que no haya más registros
					     do {
					    	 
					    	 //Creo la sucursal
					    	 Branch aBranch = new Branch(new BigDecimal(c.getInt(c.getColumnIndexOrThrow("_id"))), 
					    			 										c.getString(c.getColumnIndexOrThrow("Alias")),
					    			 										c.getString(c.getColumnIndexOrThrow("Address")),
					    			 										new BigDecimal(c.getInt(c.getColumnIndexOrThrow("City_id"))),
					    			 										c.getString(c.getColumnIndexOrThrow("PhoneNumber")),
					    			 										c.getString(c.getColumnIndexOrThrow("branch_state")));
					    	 
					    	 //Creo el cliente
					    	 Client aClient = new Client(new BigDecimal(c.getInt(c.getColumnIndexOrThrow("Client_id"))),
					    			 c.getString(c.getColumnIndexOrThrow("client_name")),
					    			 c.getString(c.getColumnIndexOrThrow("client_address")),
					    			 null,
					    			 new BigDecimal(c.getInt(c.getColumnIndexOrThrow("ClientType_id"))),
					    			 c.getString(c.getColumnIndexOrThrow("client_state")));
					    	 
					    	 //Seteo el cliente a la sucursal
					    	 aBranch.setClient(aClient);
					    	 
					    	 //Agrego la sucursal a la lista
					    	 branchList.add(aBranch);
					    	 
					     } while(c.moveToNext());
					}
				 
				
					//Cierro el cursor
					c.close();
					
					 //Cerramos la base de datos
			        db.close();
			        
				} catch (SQLException e) {
					Log.e("BranchModel", e.getMessage());
					throw new UxorException("Error de base de datos",e);
				}
		        
				return branchList;
	}

	
	@Override
	public List<BranchsQtyDto> retrieveBranchsQtyList(Long townId, Boolean inMyTown) throws UxorException {
		List<BranchsQtyDto> branchsQtyList = new ArrayList<BranchsQtyDto>();
		Cursor c;
		
		try{
			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
			
			if (inMyTown){
				//Armo la consulta para recuperar las sucursales para la ciudad
				c = db.rawQuery("Select cli.ClientType_id as ClientType_id, " +
					"ct.description as ClientTypeDesc, " +
					"sum(cli.CantSucursales) as CantidadSucursales, " +
					"'T' as MyTown " +
					"FROM (Select c._id as _id, c.ClientType_id as ClientType_id, count(*) as CantSucursales, bra.City_id as City_id From Clients c, Branchs bra " +
					"Where bra.Client_id=c._id and bra.State='HAB' and bra.City_id=" + townId.toString().concat(" ") + 
					"Group by c._id Order by c.ClientType_id) as cli, Client_Types as ct " +
					"Where ct._id =  cli.ClientType_id " +
					"Group by ct.description, cli.ClientType_id " +
					"Having sum(cli.CantSucursales) > 0",null);
			}else{
				//Armo la consulta para recuperar las sucursales para el resto del país
				c = db.rawQuery("Select cli.ClientType_id as ClientType_id, " +
						"ct.description as ClientTypeDesc, " +
						"sum(cli.CantSucursales) as CantidadSucursales, " +
						"'F' as MyTown " +
 						"FROM (Select c._id as _id, c.ClientType_id as ClientType_id, count(*) as CantSucursales, bra.City_id as City_id From Clients c, Branchs bra " +
						"Where bra.Client_id=c._id and bra.State='HAB' and bra.City_id<>" + townId.toString().concat(" ") + 
						"Group by c._id Order by c.ClientType_id) as cli, Client_Types as ct " +
						"Where ct._id =  cli.ClientType_id " +
						"Group by ct.description, cli.ClientType_id " +
						"Having sum(cli.CantSucursales) > 0",null);
			}
		
			//Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
			     //Recorremos el cursor hasta que no haya más registros
			     do {
			    	 
			    	 //Creo el objeto de tipo BranchsQtyDto
			    	 Boolean myTown = c.getString(c.getColumnIndexOrThrow("MyTown")).equals("T") ? Boolean.TRUE : Boolean.FALSE;
			    	 BranchsQtyDto aBranchsQtyDto = new BranchsQtyDto(new BigDecimal(c.getInt(c.getColumnIndexOrThrow("ClientType_id"))), 
			    			 										c.getString(c.getColumnIndexOrThrow("ClientTypeDesc")),
			    			 										new BigDecimal(c.getInt(c.getColumnIndexOrThrow("CantidadSucursales"))),
			    			 										myTown);
			    	 
			    	 //Agrego el objeto a la lista
			    	 branchsQtyList.add(aBranchsQtyDto);
			    	 
			     } while(c.moveToNext());
			}
		 
		
			//Cierro el cursor
			c.close();
			
			 //Cerramos la base de datos
	        db.close();
	        
		} catch (SQLException e) {
			Log.e("BranchModel", e.getMessage());
			throw new UxorException("Error de base de datos",e);
		}
        
		return branchsQtyList;
	}

}
