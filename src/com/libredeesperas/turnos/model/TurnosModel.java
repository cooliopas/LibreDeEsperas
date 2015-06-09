package com.uxor.turnos.model;

/**
 * Implementación de los métodos de la interface ITurnModel
 * 
 * @author      Alejandro Espina <alejandroespina@uxorit.com>
 * @version     1.0                 (current version number of program)
 * @since       2014-02-06          (the version of the package this class was first added to)
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.uxor.turnos.domain.ServerNew;
import com.uxor.turnos.domain.Service;
import com.uxor.turnos.domain.Turno;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.helper.TurnosSqlLiteHelper;
import com.uxor.turnos.util.Turnos;
import com.uxor.turnos.view.dto.LstTurnDto;

public class TurnosModel implements ITurnosModel {
	private TurnosSqlLiteHelper turnosSqlLiteHelper;
	
	public TurnosModel(){
		this.turnosSqlLiteHelper = TurnosSqlLiteHelper.getInstance(Turnos.getAppContext());
	}

	
	
	@Override
	public List<LstTurnDto> getListadoTurnos() throws UxorException {
		// TODO Auto-generated method stub
		List<LstTurnDto> listadoTurnos = new ArrayList<LstTurnDto>();
		
		//Obtengo la base de datos
		SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
		
		//Armo la consulta para recuperar los turnos
		Cursor c = db.rawQuery("Select b.Alias as BranchAlias, " +
				"b.Address as BranchAddress, " +
				"ct.Description as ClientTypeDesc, " +
				"c.Name as ClientName," +
				"c.ClientImage as ClientImageName," +
				"ms.Description as MServiceDesc, " +
				"t._id as TurnId, " +
				"ms.letter as TurnLetter, " + 
				"ms.Enabled as ServiceStatus, " +
				"strftime('%s', t.Turn_Date) * 1000 as TurnDate, " +
				"strftime('%s', t.Turn_Hour) * 1000 as TurnHour, " +
				"t.Turn_Number as TurnNumber, " +
				"t.Turns_Before as TurnBefore, " +
				"t.Waiting_Time as WaitingTime, " +
				"t.ServerTurn_id as ServerTurnId, " +
				"t.Status as TurnStatus, " +
				"b.PhoneNumber as BranchPhoneNumber " +
				"From Branchs b, " +
				"Client_Types ct, " +
				"Clients c, " +
				"MServices ms, " +
				"MTurns t " +
				"Where b.Client_id = c._id " +
				"and ct._id = c.ClientType_id " +
				"and ms.Branch_id = b._id " +
				"and t.Status='HAB' " +
				"and t.Turn_Date=date('now') " +
				"and t.Turns_Before!=0 " +
				"and t.ClientService_id = ms._id " +
				"Order by Turns_Before",null);
		
		
		//Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
		     //Recorremos el cursor hasta que no haya más registros
		     do {
		    	 
		    	 LstTurnDto aLstTurnoDto = new LstTurnDto(c.getString(c.getColumnIndexOrThrow("BranchAlias")), 
		    			 										c.getString(c.getColumnIndexOrThrow("BranchAddress")),
		    			 										c.getString(c.getColumnIndexOrThrow("ClientTypeDesc")), 
		    			 										c.getString(c.getColumnIndexOrThrow("MServiceDesc")), 
		    			 										new BigDecimal(c.getInt(c.getColumnIndexOrThrow("TurnId"))),
		    			 										c.getString(c.getColumnIndexOrThrow("TurnLetter")),
		    			 										new Timestamp(c.getLong(c.getColumnIndexOrThrow("TurnDate"))),
		    			 										new Timestamp(c.getLong(c.getColumnIndexOrThrow("TurnHour"))),
		    			 										c.getString(c.getColumnIndexOrThrow("TurnNumber")), 
		    			 										new BigDecimal(c.getInt(c.getColumnIndexOrThrow("TurnBefore"))),
		    	 												new BigDecimal(c.getInt(c.getColumnIndexOrThrow("WaitingTime"))),
		    	 												new BigDecimal(c.getInt(c.getColumnIndexOrThrow("ServerTurnId"))),
		    	 												c.getString(c.getColumnIndexOrThrow("BranchPhoneNumber")),
		    	 												c.getString(c.getColumnIndexOrThrow("ClientName")),
		    	 												c.getString(c.getColumnIndexOrThrow("TurnStatus")),
		    	 												c.getString(c.getColumnIndexOrThrow("ClientImageName")),
		    	 												new Boolean(c.getInt(c.getColumnIndexOrThrow("ServiceStatus"))>0));

		    	 
		    	  listadoTurnos.add(aLstTurnoDto);
		     } while(c.moveToNext());
		}
		 
		//Cierro el cursor
		c.close();
		
		 //Cerramos la base de datos
        //db.close();
        
		return listadoTurnos;
	}



	@Override
	/*
	 * (non-Javadoc)
	 * @see com.uxor.turnos.model.ITurnosModel#updateTurnos(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)
	 * Actualiza el turno cuando se recibe una notificación
	 */
	public void updateTurnos(BigDecimal serverTurnId, BigDecimal waitingTime, BigDecimal turnsBefore, String turnStatus) throws UxorException {
		
		
		try {

			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
		
			//Armo la consulta para recuperar los turnos
			db.execSQL("Update MTurns " +
					"Set Waiting_Time="+ waitingTime.toString()+
					", Turns_Before=" + turnsBefore.toString() + 
					", Status='" + turnStatus + "'" +
					" Where ServerTurn_id="+serverTurnId);
					
			//Cerramos la base de datos
			//db.close();
		
		} catch(SQLiteException e) {
	        Log.d("updateTurnos", e.getMessage());
	        throw new UxorException("Error de base de datos");
		}
		
	}
	
	
	public int getCountTurnos() throws UxorException {
		//sql=Select Turn_Date From MTurns Where Turn_Date=date('now')  --> Turnos del día actual
	    
		String countQuery = "SELECT * FROM MTurns";
	    
		//Obtengo la base de datos
		SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
				
	    Cursor cursor = db.rawQuery(countQuery, null);
	    
	    int count = cursor.getCount();
	    
	    cursor.close();
	    
	    //db.close();
	    
	    return count;
	}



	@Override
	public List<Service> retrieveServicesCbo() throws UxorException {
		List<Service> serviceCboList = new ArrayList<Service>();
		
		try{
			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
			
			//Armo la consulta para recuperar los servicios para cargar el combo
			Cursor c = db.rawQuery("Select _id, " +
					"Branch_id, " +
					"Description " +
					"From MServices",null);
			
			
			//Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
			     //Recorremos el cursor hasta que no haya más registros
			     do {
			    	 
			    	 //Creo la sucursal
			    	 Service aService = new Service(new BigDecimal(c.getInt(c.getColumnIndexOrThrow("_id"))), 
			    			 						new BigDecimal(c.getInt(c.getColumnIndexOrThrow("Branch_id"))),
			    			 						c.getString(c.getColumnIndexOrThrow("Description")));
			    	 
			    	 //Agrego la sucursal a la lista
			    	 serviceCboList.add(aService);
			    	 
			     } while(c.moveToNext());
			}
			 
			//Cierro el cursor
			c.close();
			
			 //Cerramos la base de datos
	        //db.close();
	        
		} catch (SQLException e) {
			Log.e("TurnModel", e.getMessage());
			throw new UxorException("Error de base de datos",e);
		} catch (Exception e) {
			Log.e("TurnModel", e.getMessage());
			throw new UxorException("Se produjo un error",e);
		}
		return serviceCboList;
	}
	
	@Override
	public List<Service> retrieveServicesCbo(BigDecimal branchId) throws UxorException {
		List<Service> serviceCboList = new ArrayList<Service>();
		
		try{
			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
			String[] args = new String[] {branchId.toString()};
			//Armo la consulta para recuperar los servicios para cargar el combo
			Cursor c = db.rawQuery("Select _id,Branch_id,Description FROM MServices WHERE Branch_id=? and State='HAB' Order by Description",args);
			
			
			//Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
			     //Recorremos el cursor hasta que no haya más registros
			     do {
			    	 
			    	 //Creo la sucursal
			    	 Service aService = new Service(new BigDecimal(c.getInt(c.getColumnIndexOrThrow("_id"))), 
			    			 						new BigDecimal(c.getInt(c.getColumnIndexOrThrow("Branch_id"))),
			    			 						c.getString(c.getColumnIndexOrThrow("Description")));
			    	 
			    	 //Agrego la sucursal a la lista
			    	 serviceCboList.add(aService);
			    	 
			     } while(c.moveToNext());
			}
			 
			//Cierro el cursor
			c.close();
			
			 //Cerramos la base de datos
	        //db.close();
	        
		} catch (SQLException e) {
			Log.e("TurnModel", e.getMessage());
			throw new UxorException("Error de base de datos",e);
		} catch (Exception e) {
			Log.e("TurnModel", e.getMessage());
			throw new UxorException("Se produjo un error",e);
		}
		return serviceCboList;
	}



	@Override
	public BigDecimal insertTurn(Turno turn) throws UxorException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
		long turnId =0;
		try {

			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
			
			//Creamos el registro a insertar como objeto ContentValues
			ContentValues newTurn = new ContentValues();
			newTurn.put("ClientService_id", turn.getClientServiceId().toString());
			newTurn.put("Turn_Date", sf.format(turn.getTurnDate()));
			newTurn.put("Turn_Number", turn.getTurnNumber());
			newTurn.put("GcmId", turn.getGcmId());
			newTurn.put("Status", "HAB");
			 
			//Insertamos el registro en la base de datos
			turnId = db.insert("MTurns", null, newTurn);
			
		
			//Inserto el turno
//			db.execSQL("INSERT INTO [MTurns] ([ClientService_id], [Turn_Date], [Turn_Number], [GcmId]) " +
//					"VALUES ("+ turn.getClientServiceId().toString() + ", '" + sf.format(turn.getTurnDate()) + 
//					"', " + turn.getTurnNumber().toString() + ", '" + turn.getGcmId() + "')");
					
			//Cerramos la base de datos
			//db.close();
		
		} catch(SQLiteException e) {
	        Log.d("TurnModel", e.getMessage());
	        throw new UxorException("Error de base de datos");
		} catch (Exception e) {
			Log.e("TurnModel", e.getMessage());
			throw new UxorException("Se produjo un error",e);
		}
		
		return BigDecimal.valueOf(turnId);
		
	}
		
	public Turno getTurn(Long turnId) throws UxorException {
		try {

			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
			Turno turno=null;			 
			//Obtenemos el registro en la base de datos
			String selectQuery = "SELECT * FROM MTurns WHERE _id=?";
			Cursor c = db.rawQuery(selectQuery, new String[] { turnId.toString() });
			if (c.moveToFirst()) {
				turno = new Turno (new BigDecimal (c.getInt(c.getColumnIndexOrThrow("_id"))), new BigDecimal (c.getInt(c.getColumnIndexOrThrow("ServerTurn_id"))), new BigDecimal (c.getInt(c.getColumnIndexOrThrow("ClientService_id"))),
						/*c.getString(c.getColumnIndexOrThrow("TurnLetter"))*/null, new Timestamp(c.getLong(c.getColumnIndexOrThrow("Turn_Date"))), new Timestamp(c.getLong(c.getColumnIndexOrThrow("Turn_Hour"))),
						c.getString(c.getColumnIndexOrThrow("Turn_Number")), c.getString(c.getColumnIndexOrThrow("Status")), new BigDecimal (c.getInt(c.getColumnIndexOrThrow("Waiting_Time"))), 
						new BigDecimal (c.getInt(c.getColumnIndexOrThrow("Turns_Before"))), null/*c.getString(c.getColumnIndexOrThrow("ExceptionMessage"))*/);
			}

			c.close();
			//Cerramos la base de datos
			//db.close();
			
			return turno;
		
		} catch(SQLiteException e) {
	        Log.d("TurnModel", e.getMessage());
	        throw new UxorException("Error de base de datos");
		} catch (Exception e) {
			Log.e("TurnModel", e.getMessage());
			throw new UxorException("Se produjo un error",e);
		}
	}
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.uxor.turnos.model.ITurnosModel#updateTurnos(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)
	 * Actualiza el estado del turno
	 */
	public void updateTurnStatus(BigDecimal turnId, String turnStatus) throws UxorException {
		
		
		try {

			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
		
			//Armo la consulta para actualizar el estado del turno
//			db.execSQL("Update MTurns " +
//					"Set Status="+ turnStatus+
//					" Where _id="+turnId);
			
			ContentValues newValues = new ContentValues();
			newValues.put("Status", turnStatus);

//			String[] args = new String[]{"user1", "user2"};  //Si tiene mas de un argumento para identificar la fila a actualizar
			String[] args = new String[]{turnId.toString()};
			db.update("MTurns", newValues, "_id=?", args);
					
			//Cerramos la base de datos
			//db.close();
		
		} catch(SQLiteException e) {
	        Log.d("updateTurnos", e.getMessage());
	        throw new UxorException("Error de base de datos");
		}
		
	}
	
	
	@Override
	public void updateTurn(BigDecimal turnId, BigDecimal serverTurnId, BigDecimal waitingTime, BigDecimal turnsBefore, String turnStatus) throws UxorException {
		
		
		try {

			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
		
			ContentValues newValues = new ContentValues();
			newValues.put("ServerTurn_id", serverTurnId.toString());
			newValues.put("Waiting_Time", waitingTime.toString());
			newValues.put("Turns_Before", turnsBefore.toString());
			newValues.put("Status", turnStatus);
			
			String[] args = new String[]{turnId.toString()};
			db.update("MTurns", newValues, "_id=?", args);
			
			//Armo la consulta para recuperar los turnos
//			db.execSQL("Update MTurns " +
//					"Set Waiting_Time="+ waitingTime.toString()+
//					", Turns_Before=" + turnsBefore.toString() + 
//					" Where ServerTurn_id="+serverTurnId);
					
			//Cerramos la base de datos
			//db.close();
		
		} catch(SQLiteException e) {
	        Log.d("updateTurnos", e.getMessage());
	        throw new UxorException("Error de base de datos");
		}
		
	}


	@Override
	public int getPendingTurnsQty() throws UxorException {
		
		//Consulta sql
		String sql = "Select * " + 
					"From MTurns " +
					"Where Status='HAB' " +
					"and Turn_Date=date('now')";
		
		//Obtengo la base de datos
		SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
				
	    Cursor cursor = db.rawQuery(sql, null);
	    
	    int count = cursor.getCount();
	    
	    cursor.close();
	    
	    //Cerramos la base de datos
        //db.close();
	    
	    return count;
	    
	}
	
	
	@Override
	public int getDisabledTurnsQty() throws UxorException {
		
		String sql = "Select * " + 
					"From MTurns " +
					"Where Status='DES'";
	    
		//Obtengo la base de datos
		SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
				
	    Cursor cursor = db.rawQuery(sql, null);
	    
	    int count = cursor.getCount();
	    
	    cursor.close();
	    
	    //Cerramos la base de datos
        //db.close();
	    
	    return count;
	    
	}
	
	
	public List<LstTurnDto> getInactiveTurns(Boolean currentMonth) throws UxorException {
		// TODO Auto-generated method stub
		List<LstTurnDto> listInactiveTurns = new ArrayList<LstTurnDto>();
		String sqlCurrentMonth="";
		
		//Verifico si es para el mes actual
		if (currentMonth){
			sqlCurrentMonth = sqlCurrentMonth.concat("cast(strftime('%m', t.turn_date) as integer) = cast(strftime('%m', date('now')) as integer) ");
		}else {
			sqlCurrentMonth = sqlCurrentMonth.concat("cast(strftime('%m', turn_date) as integer) < cast(strftime('%m', date('now')) as integer) ");
		}
		
		//Obtengo la base de datos
		SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
		
		//Armo la consulta para recuperar los turnos
		Cursor c = db.rawQuery("Select b.Alias as BranchAlias, " +
				"b.Address as BranchAddress, " +
				"ct.Description as ClientTypeDesc, " +
				"c.Name as ClientName," +
				"c.ClientImage as ClientImageName," +
				"ms.Description as MServiceDesc, " +
				"t._id as TurnId, " +
				"ms.letter as TurnLetter, " + 
				"ms.Enabled as ServiceStatus, " +
				"strftime('%s', t.Turn_Date) * 1000 as TurnDate, " +
				"strftime('%s', t.Turn_Hour) * 1000 as TurnHour, " +
				"t.Turn_Number as TurnNumber, " +
				"t.Turns_Before as TurnBefore, " +
				"t.Waiting_Time as WaitingTime, " +
				"t.ServerTurn_id as ServerTurnId, " +
				"t.Status as TurnStatus, " +
				"b.PhoneNumber as BranchPhoneNumber " +
				"From Branchs b, " +
				"Client_Types ct, " +
				"Clients c, " +
				"MServices ms, " +
				"MTurns t " +
				"Where b.Client_id = c._id " +
				"and ct._id = c.ClientType_id " +
				"and ms.Branch_id = b._id " +
				"and t.Status='DES' " +
				"and t.ClientService_id = ms._id " +
//				"and t.Turn_Date<date('now') " +
				"and " + sqlCurrentMonth + 
				"Order by TurnBefore",null);
		
		
		//Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
		     //Recorremos el cursor hasta que no haya más registros
		     do {
		    	 
		    	 LstTurnDto aLstTurnoDto = new LstTurnDto(c.getString(c.getColumnIndexOrThrow("BranchAlias")), 
		    			 										c.getString(c.getColumnIndexOrThrow("BranchAddress")),
		    			 										c.getString(c.getColumnIndexOrThrow("ClientTypeDesc")), 
		    			 										c.getString(c.getColumnIndexOrThrow("MServiceDesc")), 
		    			 										new BigDecimal(c.getInt(c.getColumnIndexOrThrow("TurnId"))),
		    			 										c.getString(c.getColumnIndexOrThrow("TurnLetter")),
		    			 										new Timestamp(c.getLong(c.getColumnIndexOrThrow("TurnDate"))),
		    			 										new Timestamp(c.getLong(c.getColumnIndexOrThrow("TurnHour"))),
		    			 										c.getString(c.getColumnIndexOrThrow("TurnNumber")), 
		    			 										new BigDecimal(c.getInt(c.getColumnIndexOrThrow("TurnBefore"))),
		    	 												new BigDecimal(c.getInt(c.getColumnIndexOrThrow("WaitingTime"))),
		    	 												new BigDecimal(c.getInt(c.getColumnIndexOrThrow("ServerTurnId"))),
		    	 												c.getString(c.getColumnIndexOrThrow("BranchPhoneNumber")),
		    	 												c.getString(c.getColumnIndexOrThrow("ClientName")),
		    	 												c.getString(c.getColumnIndexOrThrow("TurnStatus")),
		    	 												c.getString(c.getColumnIndexOrThrow("ClientImageName")),
		    	 												new Boolean(c.getInt(c.getColumnIndexOrThrow("ServiceStatus"))>0));
		    	 
		    	 listInactiveTurns.add(aLstTurnoDto);
		     } while(c.moveToNext());
		}
		 
		//Cierro el cursor
		c.close();
		
		 //Cerramos la base de datos
        //db.close();
        
		return listInactiveTurns;
	}



	@Override
	public int getActiveTurnsQty() throws UxorException {
		
		//Consulta sql
		String sql = "Select * " + 
				"From MTurns " +
				"Where Status='HAB' " +
				"and Turn_Date=date('now') " +
				"and Turns_Before!=0";
    
		//Obtengo la base de datos
		SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
				
	    Cursor cursor = db.rawQuery(sql, null);
	    
	    //Cantidad de turnos activos
	    int count = cursor.getCount();
	    
	    cursor.close();
	    
	    //Cerramos la base de datos
        //db.close();
	    
	    return count;
		
	}
	
	@Override
	public BigDecimal insertServerNew(ServerNew serverNew) throws UxorException {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		long serverNewId =0;
		try {

			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
			
			//Creamos el registro a insertar como objeto ContentValues
			ContentValues newServerNew = new ContentValues();
			newServerNew.put("ServerTurn_id", serverNew.getServerTurnId().toString());
			newServerNew.put("Recived_Date", serverNew.getRecivedDate().getTime()/*dateFormat.format(serverNew.getRecivedDate().getTime())*/);
			newServerNew.put("Type", serverNew.getType());
			newServerNew.put("Message", serverNew.getMessage());
			 
			//Insertamos el registro en la base de datos
			serverNewId = db.insert("Server_News", null, newServerNew);
			
	
			//Cerramos la base de datos
			//db.close();
		
		} catch(SQLiteException e) {
	        Log.d("TurnModel", e.getMessage());
	        throw new UxorException("Error de base de datos");
		} catch (Exception e) {
			Log.e("TurnModel", e.getMessage());
			throw new UxorException("Se produjo un error",e);
		}
		
		return BigDecimal.valueOf(serverNewId);
		
	}
	
	public List<ServerNew> getServerNewsByServerTurnId(Long serverTurnId) throws UxorException
	{
		
		List<ServerNew> listServerNews = new ArrayList<ServerNew>();

		//Consulta sql
		String sql = "Select * " + 
				"From Server_News " +
				"Where ServerTurn_id=? " +
				"Order By Recived_Date DESC";
    
		//Obtengo la base de datos
		SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
					    
		String[] args = new String[] {serverTurnId.toString()};
		//Armo la consulta para recuperar los servicios para cargar el combo
		Cursor cursor = db.rawQuery(sql,args);
	    
		//Nos aseguramos de que existe al menos un registro
		if (cursor.moveToFirst()) {
		     //Recorremos el cursor hasta que no haya más registros
		     do {
		    	 //ver
		    	 ServerNew serverNew = new ServerNew (
							new BigDecimal(cursor.getInt(cursor.getColumnIndexOrThrow("_id"))),
							new BigDecimal(cursor.getInt(cursor.getColumnIndexOrThrow("ServerTurn_id"))),
//							Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("Recived_Date"))),
							new Timestamp(cursor.getLong(cursor.getColumnIndexOrThrow("Recived_Date"))),
							cursor.getString(cursor.getColumnIndexOrThrow("Type")), 
							cursor.getString(cursor.getColumnIndexOrThrow("Message")));

		    	 listServerNews.add(serverNew); 
		     } while(cursor.moveToNext());
		}
		 
		//Cierro el cursor
		cursor.close();
		
		//Cerramos la base de datos
        //db.close();
        
		return listServerNews;		
		
	}
	
	public void updateServiceStatus(BigDecimal serviceId, Boolean serviceStatus) throws UxorException {
		
		try {
			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();

			ContentValues newValues = new ContentValues();
			newValues.put("Enabled", serviceStatus);

			String[] args = new String[]{serviceId.toString()};
			db.update("MServices", newValues, "_id=?", args);
					
			//Cerramos la base de datos
			//db.close();
		} catch(SQLiteException e) {
	        Log.d("updateTurnos", e.getMessage());
	        throw new UxorException("Error de base de datos");
		}
		
	}
	
	public BigDecimal getServiceIdByServerTurnId(Long serverTurnId) throws UxorException {
		
		try {

			//Obtengo la base de datos
			SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
			BigDecimal clientServiceId=null;			 
			//Obtenemos el registro en la base de datos
			String selectQuery = "SELECT ClientService_id FROM MTurns WHERE ServerTurn_id=?";
			Cursor c = db.rawQuery(selectQuery, new String[] { serverTurnId.toString() });
			if (c.moveToFirst()) {
				clientServiceId = new BigDecimal (c.getInt(c.getColumnIndexOrThrow("ClientService_id")));
			}

			c.close();
			//Cerramos la base de datos
			//db.close();
			
			return clientServiceId;
		
		} catch(SQLiteException e) {
	        Log.d("TurnModel", e.getMessage());
	        throw new UxorException("Error de base de datos");
		} catch (Exception e) {
			Log.e("TurnModel", e.getMessage());
			throw new UxorException("Se produjo un error",e);
		}
		
	}

	


}
