package com.uxor.turnos.model.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uxor.turnos.model.MainModel;

public class TurnosSqlLiteHelper extends SQLiteOpenHelper {
	
	private static final String LOGTAG = "TurnosSqlLiteHelper";
	
	//singleton/ single instance reference of database instance
    private static TurnosSqlLiteHelper _dbHelper;
 
    //The Android's default system path of your application database.
    //private static String DB_PATH = "/data/data/YOUR_PACKAGE/databases/";
    private static String DB_PATH = "/data/data/com.uxor.turnos/databases/";
    
    //database name
    private static String DB_NAME = "turnos_sqlite_2.db";
    
    //database version
    private static int DB_VERSION = 1;
    
    //reference of database
    private SQLiteDatabase turnosSqliteDb;
 
    //Contexto
    private final Context _context;
    
    //MainModel
    private MainModel mainModel;
    
	 
    //Pruebas
    private String sqlCreate = "CREATE TABLE TurnosTemp (_id INTEGER primary key autoincrement, " +
    		"direccion_sucursal TEXT, " +
    		"tipo_servicio TEXT, " +
    		"tiempo_estimado_espera REAL, " +
    		"cantidad_turnos INTEGER, " +
    		"nro_turno TEXT)";
    
    
    //Sentencia SQL para crear la tabla de Clients
    private String sqlClients = "CREATE TABLE [Clients] (" +
		  "[_id] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, " +
		  "[Name] VARCHAR(100)  NULL, " +
		  "[Address] VARCHAR(100)  NULL, "+
		  "[ClientImage] VARCHAR(30) NULL, " +
		  "ClientType_id INTEGER references Client_Types(_id))";
    
    private String sqlClientTypes = "CREATE TABLE [Client_Types] (" +
    		"[_id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
    		"[Description] VARCHAR(50)  NULL )";
 
    
  	//Sentencia SQL para crear la tabla de Branchs
  	private String sqlBranchs = "CREATE TABLE [Branchs] (" +
  		"[_id] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, " +
  		"[Alias] VARCHAR(6)  NULL, " +
  		"[Address] VARCHAR(100)  NULL, " +
  		"[City_id] INTEGER  NULL, " +
  		"[PhoneNumber] VARCHAR(15) NULL, " +
  		"Client_id INTEGER references Clients(_id))";
  
  	//Sentencia SQL para crear la tabla de MServices
  	private String sqlMServices = "CREATE TABLE [MServices] (" +
  		"[_id] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, " +
  		"[Description] VARCHAR(30)  NULL, " +
  		"[Aditional_info] VARCHAR(100)  NULL, " +
  		"[Enabled] BOOLEAN  NULL, " +
  		"[Last_Update] TIMESTAMP  NULL, " +
  		"[Confirm_Cancel_Turn] INTEGER  NULL, " +
  		"[Number_Qty] BOOLEAN  NULL, " +
  		"[Waiting_Time] INTEGER  NULL, " +
  		"[Backlog] BOOLEAN  NULL, " +
  		"[WSNews] VARCHAR(100)  NULL , " +
  		"[Letter] VARCHAR(2)  NULL, " +
  		"Branch_id INTEGER references Branchs(_id))";
  
  	//Sentencia SQL para crear la tabla de Turns
  	private String sqlMTurns = "CREATE TABLE [MTurns] (" +
  		"[_id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
  		"[ServerTurn_id] INTEGER  UNIQUE NULL, " +
  		"[ClientService_id] INTEGER NOT NULL, " +
  		"[Turn_Date] DATE  NOT NULL, " +
  		"[Turn_Hour] TIME  NULL, " +
  		"[Turn_Number] INTEGER NOT NULL, " +
  		"[Status] VARCHAR(3)  NULL, " +
  		"[Waiting_Time] INTEGER  NULL, " +
  		"[Turns_Before] INTEGER  NULL, " +
  		"[GcmId] VARCHAR(200) NULL)";
  	
  	//Sentencia SQL para crear la tabla de Service News
  	private String sqlMServiceNews = "CREATE TABLE [MService_News] (" +
  		"[_id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
  		"[ServerTurn_id] INTEGER  NULL, " +
  		"[Recived_Date] DATE  NOT NULL, " +
  		"[Recived_Hour] TIME  NULL, " +
  		"[Message] VARCHAR(200)  NULL, " +
  		"[Type] VARCHAR(200) NULL)";
 
  	
  	//Sentencia SQL para crear la tabla Towns
  	private String sqlMTowns ="CREATE TABLE MTowns (" + 
  		    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
  		    "description VARCHAR( 250 ), " +
  		    "provinceId INTEGER REFERENCES Provinces ( _id ))";
  	
  	//Sentencia SQL para crear la tabla Provinces
  	private String sqlMProvinces = "CREATE TABLE MProvinces (" + 
  		    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
  		    "description VARCHAR( 250 ), " +
  		    "countryId INTEGER REFERENCES Countries ( _id ))";
  
  //Sentencia SQL para crear la tabla Countries
  	private String sqlMCountries = "CREATE TABLE MCountries (" + 
  		    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
  		    "description VARCHAR( 250 ))";
  	
    private TurnosSqlLiteHelper(Context context) 
    {
        super(context, DB_NAME, null, DB_VERSION);
        this._context = context;
        try {
			createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    public static TurnosSqlLiteHelper getInstance(Context context)
    {
        if(_dbHelper == null)
        {
            _dbHelper = new TurnosSqlLiteHelper(context);
            
        }
        return _dbHelper;
    }
    
    
    public SQLiteDatabase getDataBase(){
    	turnosSqliteDb = this.getWritableDatabase();
    	
    	return turnosSqliteDb;
    }
    
    
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    private void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    		Log.e(LOGTAG, "La base de datos ya existe!!!");
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
    		Log.e(LOGTAG, "La base de datos no existe!!!");
    		Log.e(LOGTAG, e.getMessage());
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = this._context.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
//    public void openDataBase() throws SQLException{
// 
//    	//Open the database
//        String myPath = DB_PATH + DB_NAME;
//    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
// 
//    }
 
    @Override
	public synchronized void close() {
 
    	    if(turnosSqliteDb != null)
    	    	turnosSqliteDb.close();
 
    	    super.close();
 
	}
    
    
//    @Override
//    public void onCreate(SQLiteDatabase db) {
    	
//        //Se ejecuta la sentencia SQL de creación de la tabla
//        db.execSQL(sqlCreate);
//        
//      //Creo la tabla Clients Types
//    	db.execSQL(sqlClientTypes);
//    	
//    	//Creo la tabla Clients
//    	db.execSQL(sqlClients);
//    	
//    	//Creo la tabla Branchs
//    	db.execSQL(sqlBranchs);
//    	
//    	//Creo la tabla Services
//    	db.execSQL(sqlMServices);
//    	
//    	//Creo la tabla Turnos
//    	db.execSQL(sqlMTurns);
//    	
//    	//Creo la tabla Towns
//    	db.execSQL(sqlMTowns);
//    	
//    	//Creo la tabla Provinces
//    	db.execSQL(sqlMProvinces);
//    	
//    	//Creo la tabla Countries
//    	db.execSQL(sqlMCountries);
    	
//    }
 
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
//        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
//        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
//        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
//        //      a la nueva, por lo que este método debería ser más elaborado.
// 
//        //Se elimina la versión anterior de la tabla
//        db.execSQL("DROP TABLE IF EXISTS Client_Types");
//        db.execSQL("DROP TABLE IF EXISTS Clients");
//        db.execSQL("DROP TABLE IF EXISTS Branchs");
//        db.execSQL("DROP TABLE IF EXISTS MServices");
//        db.execSQL("DROP TABLE IF EXISTS MTurns");
//        db.execSQL("DROP TABLE IF EXISTS MTowns");
//        db.execSQL("DROP TABLE IF EXISTS MProvinces");
//        db.execSQL("DROP TABLE IF EXISTS MCountries");
// 
//        //Se crea la nueva versión de la tabla
//        //db.execSQL(sqlCreate);
//        
//      //Creo la tabla Clients
//    	db.execSQL(sqlClientTypes);
//        
//    	//Creo la tabla Clients
//    	db.execSQL(sqlClients);
//    	
//    	//Creo la tabla Branchs
//    	db.execSQL(sqlBranchs);
//    	
//    	//Creo la tabla Clients
//    	db.execSQL(sqlMServices);
//    	
//    	//Creo la tabla Clients
//    	db.execSQL(sqlMTurns);
//    	
//    	//Creo la tabla Towns
//    	db.execSQL(sqlMTowns);
//    	
//    	//Creo la tabla Provinces
//    	db.execSQL(sqlMProvinces);
//    	
//    	//Creo la tabla Countries
//    	db.execSQL(sqlMCountries);
//    	
//		//Creo la tabla Service News
//		db.execSQL(sqlMServiceNews);
	    
//    	//insertTempData(db);
//    }
    
    
    
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	
}
