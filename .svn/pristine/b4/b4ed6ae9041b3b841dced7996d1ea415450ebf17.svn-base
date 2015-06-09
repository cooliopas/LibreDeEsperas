package com.uxor.turnos.model;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.helper.TurnosSqlLiteHelper;
import com.uxor.turnos.util.Turnos;

public class MainModel implements IMainModel {
	private TurnosSqlLiteHelper turnosSqlLiteHelper;
	
	public MainModel(){
		this.turnosSqlLiteHelper = TurnosSqlLiteHelper.getInstance(Turnos.getAppContext());
	}


	@Override
	public void insertTempData() throws UxorException {
		
		//Obtengo la base de datos
		SQLiteDatabase db = turnosSqlLiteHelper.getDataBase();
		
		try {
		
			//Inserto los tipos de clientes
			db.execSQL("INSERT INTO [Client_Types] ([_id], [Description]) VALUES (1, 'Entidades de Estado')");
			db.execSQL("INSERT INTO [Client_Types] ([_id], [Description]) VALUES (2, 'Bancos o entidades financieras')");
					
			//Inserto los Clientes
			db.execSQL("INSERT INTO [Clients] ([_id], [Name], [Address], [ClientImage], [ClientType_id]) VALUES (1, 'Municipalidad', 'Garay 3236', 'logo_claro', 1)");
			db.execSQL("INSERT INTO [Clients] ([_id], [Name], [Address], [ClientImage], [ClientType_id]) VALUES (2, 'Tribunal de Faltas 1', 'La Rioja 4587', 'logo_afip', 2)");
			db.execSQL("INSERT INTO [Clients] ([_id], [Name], [Address], [ClientImage], [ClientType_id]) VALUES (3, 'Banco Rio', 'Av. Independencia 2548', 'logo_claro', 2)");
			db.execSQL("INSERT INTO [Clients] ([_id], [Name], [Address], [ClientImage], [ClientType_id]) VALUES (4, 'Banco Provincia', 'San Martin 5214', 'logo_afip', 1)");
			db.execSQL("INSERT INTO [Clients] ([_id], [Name], [Address], [ClientImage], [ClientType_id]) VALUES (5, 'Client34', 'La Rioja 456', 'logo_claro', 1)");
			
			//Inserto las Sucursales
			db.execSQL("INSERT INTO [Branchs] ([_id], [Alias], [Address], [City_id], [PhoneNumber], [Client_id]) VALUES (1, 'AA', 'Barrio Norte 4521', 1, '4732211', 3)");
			db.execSQL("INSERT INTO [Branchs] ([_id], [Alias], [Address], [City_id], [PhoneNumber], [Client_id]) VALUES (2, 'BB', 'Av. Independencia 541', 1, '4732211', 4)");
			db.execSQL("INSERT INTO [Branchs] ([_id], [Alias], [Address], [City_id], [PhoneNumber], [Client_id]) VALUES (3, 'CC', 'Av. Juan B. Justo 2548', 1, '4732211', 2)");
			db.execSQL("INSERT INTO [Branchs] ([_id], [Alias], [Address], [City_id], [PhoneNumber], [Client_id]) VALUES (4, 'DD', 'Fleming 658', 1, '4732211', 1)");
			db.execSQL("INSERT INTO [Branchs] ([_id], [Alias], [Address], [City_id], [PhoneNumber], [Client_id]) VALUES (5, 'RR', 'Roca 145', 1, '4732211', 5)");
			db.execSQL("INSERT INTO [Branchs] ([_id], [Alias], [Address], [City_id], [PhoneNumber], [Client_id]) VALUES (6, 'WW', 'Roca 345', 2, '4732211', 1)");
			db.execSQL("INSERT INTO [Branchs] ([_id], [Alias], [Address], [City_id], [PhoneNumber], [Client_id]) VALUES (7, 'qq', 'Roca 567', 2, '4732211', 3)");
			
			
			//Inserto los Servicios
			db.execSQL("INSERT INTO [MServices] ([_id], [Description], [Aditional_info], [Enabled], [Last_Update], [Confirm_Cancel_Turn], [Number_Qty], [Waiting_Time], [Backlog], [WSNews], [Letter], [Branch_id]) VALUES (1, 'Servicio Nro 1', 'Informacion adicional', 'Y', '2013-11-07 00:00:00', null, 'Y', null, null, null, 'AF', 1)");
			db.execSQL("INSERT INTO [MServices] ([_id], [Description], [Aditional_info], [Enabled], [Last_Update], [Confirm_Cancel_Turn], [Number_Qty], [Waiting_Time], [Backlog], [WSNews], [Letter], [Branch_id]) VALUES (2, 'Servicio Nro 2', 'Informacion adicional 2', 'Y', '2013-11-07 00:00:00', null, 'Y', null, null, null, 'B', 2)");
			db.execSQL("INSERT INTO [MServices] ([_id], [Description], [Aditional_info], [Enabled], [Last_Update], [Confirm_Cancel_Turn], [Number_Qty], [Waiting_Time], [Backlog], [WSNews], [Letter], [Branch_id]) VALUES (3, 'Servicio Nro 3', 'Informacion adicional 3', 'Y', '2013-11-07 00:00:00', null, 'Y', null, null, null, 'KJ', 3)");
			db.execSQL("INSERT INTO [MServices] ([_id], [Description], [Aditional_info], [Enabled], [Last_Update], [Confirm_Cancel_Turn], [Number_Qty], [Waiting_Time], [Backlog], [WSNews], [Letter], [Branch_id]) VALUES (4, 'Servicio Nro 4', 'Info adicional prueba servicio 4', 'Y', '2013-11-07 00:00:00', null, 'Y', null, null, null, 'F', 4)");
			
	
			//Inserto los Turnos
			db.execSQL("INSERT INTO [MTurns] ([_id], [ServerTurn_id], [ClientService_id], [Turn_Date], [Turn_Hour], [Turn_Number], [Status], [Waiting_Time], [Turns_Before]) VALUES (1, 1, 1, '2014-02-14', '12:45:00', '452', 'HAB', 25, 3)");
			db.execSQL("INSERT INTO [MTurns] ([_id], [ServerTurn_id], [ClientService_id], [Turn_Date], [Turn_Hour], [Turn_Number], [Status], [Waiting_Time], [Turns_Before]) VALUES (2, 2, 2, '2014-02-14', '14:30:00', '456', 'HAB', 450, 12)");
			db.execSQL("INSERT INTO [MTurns] ([_id], [ServerTurn_id], [ClientService_id], [Turn_Date], [Turn_Hour], [Turn_Number], [Status], [Waiting_Time], [Turns_Before]) VALUES (3, 3, 3, '2014-02-14', '15:45:00', '55', 'HAB', 10, 1)");
			db.execSQL("INSERT INTO [MTurns] ([_id], [ServerTurn_id], [ClientService_id], [Turn_Date], [Turn_Hour], [Turn_Number], [Status], [Waiting_Time], [Turns_Before]) VALUES (4, 4, 4, '2014-02-14', '17:25:00', '5', 'HAB', 200, 41)");
			
			//Inserto las ciudades
			db.execSQL("INSERT INTO [MTowns] ([_id], [description], [provinceId]) VALUES (1, 'Mar del Plata', 1)");
			db.execSQL("INSERT INTO [MTowns] ([_id], [description], [provinceId]) VALUES (2, 'Pinamar', 1)");
			db.execSQL("INSERT INTO [MTowns] ([_id], [description], [provinceId]) VALUES (3, 'Mar Chiquita', 1)");
			
			//Inserto las provincias
			db.execSQL("INSERT INTO [MProvinces] ([_id], [description], [countryId]) VALUES (1, 'Buenos Aires', 1)");
			
			//Inserto los paises
			db.execSQL("INSERT INTO [MCountries] ([_id], [description]) VALUES (1, 'Argentina')");
			
			
		
		} catch (SQLException e) {
			Log.e("MainModel", e.getMessage());
			throw new UxorException("Error de base de datos",e);
		}
		
		db.close();
	}

}
