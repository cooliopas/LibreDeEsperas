package com.uxor.turnos.model;

/**
 * Implementación de los métodos de la interface ITownModel
 * 
 * @author      Alejandro Espina <alejandroespina@uxorit.com>
 * @version     1.0                 (current version number of program)
 * @since       2014-02-06          (the version of the package this class was first added to)
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.uxor.turnos.domain.Town;
import com.uxor.turnos.domain.Turno;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.helper.TurnosSqlLiteHelper;
import com.uxor.turnos.util.Turnos;

public class TownModel implements ITownModel {
	private TurnosSqlLiteHelper turnosSqlLiteHelper;
	
	public TownModel(){
		this.turnosSqlLiteHelper = TurnosSqlLiteHelper.getInstance(Turnos.getAppContext());
	}

	@Override
	public List<Town> retrieveTownsCbo() throws UxorException {
			List<Town> townCboList = new ArrayList<Town>();
			
			try{
				//Obtengo la base de datos
				SQLiteDatabase db = this.turnosSqlLiteHelper.getDataBase();
				
				//Armo la consulta para recuperar los servicios para cargar el combo
				Cursor c = db.rawQuery("Select _id, " +
						"description, " +
						"provinceId " +
						"From MTowns " + 
						"Order by description",null);
				
				//Nos aseguramos de que existe al menos un registro
				if (c.moveToFirst()) {
				     //Recorremos el cursor hasta que no haya más registros
				     do {
				    	 
				    	 //Creo la ciudad
				    	 Town aTown = new Town(c.getLong(c.getColumnIndexOrThrow("_id")), 
				    			 			   c.getString(c.getColumnIndexOrThrow("description")), 
				    			 			   c.getLong(c.getColumnIndexOrThrow("provinceId")));
				    	 
				    	 //Agrego la sucursal a la lista
				    	 townCboList.add(aTown);
				    	 
				     } while(c.moveToNext());
				}
				 
				//Cierro el cursor
				c.close();
				
				 //Cerramos la base de datos
		        db.close();
		        
			} catch (SQLException e) {
				Log.e("TownModel", e.getMessage());
				throw new UxorException("Error de base de datos",e);
			} catch (Exception e) {
				Log.e("TurnModel", e.getMessage());
				throw new UxorException("Se produjo un error",e);
			}
			return townCboList;
		}
	
			
		public Town getTownById(Long townId) throws UxorException {
			try {

				//Obtengo la base de datos
				SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
				Town town=null;			 
				//Obtenemos el registro en la base de datos
				String selectQuery = "SELECT * FROM MTowns WHERE _id=?";
				Cursor c = db.rawQuery(selectQuery, new String[] { townId.toString() });
				if (c.moveToFirst()) {
					town = new Town (c.getLong(c.getColumnIndexOrThrow("_id")), c.getString(c.getColumnIndexOrThrow("description")),c.getLong(c.getColumnIndexOrThrow("provinceId")));
				}

				c.close();
				//Cerramos la base de datos
				db.close();
				
				return town;
			
			} catch(SQLiteException e) {
		        Log.d("TownModel", e.getMessage());
		        throw new UxorException("Error de base de datos");
			} catch (Exception e) {
				Log.e("TownModel", e.getMessage());
				throw new UxorException("Se produjo un error",e);
			}
		}
		

}
	


