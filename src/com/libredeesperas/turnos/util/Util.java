package com.uxor.turnos.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;

public class Util {
	
	public static String getPropertieValue(Context context, String fileName, String key){
		Resources resources = context.getResources();
		AssetManager assetManager = resources.getAssets();
		InputStream inputStream;
		Properties properties = new Properties();
		
		// Read from the /assets directory
		try {
		    inputStream = assetManager.open(fileName);
		    properties.load(inputStream);
		    
		} catch (IOException e) {
		    System.err.println("Failed to open".concat(" ").concat(fileName).concat(" property file"));
		    e.printStackTrace();
		}
		
		return ((String)properties.get(key));
	}
	
	
	public static void copiarBaseDatos(Context context) {
        String ruta = "/data/data/com.uxor.turnos/databases/";
		//File ruta = context.getDatabasePath("turnos_sqlite_2.db");
        String archivo = "turnos_sqlite_2.db";
        File archivoDB = new File(ruta + archivo);
        
        if (!archivoDB.exists()) {
	        try {
	        	archivoDB.createNewFile();
	            InputStream IS = context.getAssets().open(archivo);
	            OutputStream OS = new FileOutputStream(archivoDB);
	            byte[] buffer = new byte[1024];
	            int length = 0;
	            while ((length = IS.read(buffer))>0){
	                OS.write(buffer, 0, length);
	            }
	            OS.flush();
	            OS.close();
	            IS.close();
	        } catch (FileNotFoundException e) {
	            Log.e("ERROR", "Archivo no encontrado, " + e.toString());
	        } catch (IOException e) {
	            Log.e("ERROR", "Error al copiar la Base de Datos, " + e.toString());
	        }
        }

	}
	
	
	/*
	 * Valida si una caja de texto es numérica
	 * @return verdadero/falso
	 * @see com.uxor.turnos.util#isdigit()
	 */
	public static boolean isdigit(EditText input){

	    String data=input.getText().toString().trim();
	    for(int i=0;i<data.length();i++){
	         if (!Character.isDigit(data.charAt(i)))
	                return false;
	    }
    	return true;
    }

	/*
	 * Valida si una caja solo acepte letras
	 * @return verdadero/falso
	 * @see com.uxor.turnos.util#isdigit()
	 */
    public static boolean ischar(EditText input){

	    String data=input.getText().toString().trim();
	    for(int i=0;i<data.length();i++){
	         if (!Character.isDigit(data.charAt(i)))
	                return true;
	    }
	    return false;
    }
    
    
    /*
	 * Obtiene la densidad de la pantalla
	 * @return int
	 * @see com.uxor.turnos.util#retrieveScreenDensity()
	 */
    public static int retrieveScreenDensity(WindowManager windowManager){
    	DisplayMetrics metrics = new DisplayMetrics();
    	windowManager.getDefaultDisplay().getMetrics(metrics);
    	
    	return metrics.densityDpi;
		
    }
	
}