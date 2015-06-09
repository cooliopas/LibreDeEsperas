package com.uxor.turnos.util;

import java.io.IOException;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.uxor.turnos.presenter.GCMPresenter;
import com.uxor.turnos.presenter.MainPresenter;
import com.uxor.turnos.presenter.helper.IGcmHelper;
import com.uxor.turnos.view.MainActivity;

public class TareaRegistroGCM extends AsyncTask<String,Integer,HashMap>{
	 
	private GoogleCloudMessaging gcm;
	private String regid;
	private static final String TAG = "AsyncTaskRegistroGCM"; 
	private GCMPresenter gcmPresenter;
	private IGcmHelper gcmHelper;
	private ProgressDialog dialog;
	
	public TareaRegistroGCM(GCMPresenter gcmPresenter, IGcmHelper gcmHelper){
		this.gcmPresenter = gcmPresenter;
		this.gcmHelper = gcmHelper;
		dialog = new ProgressDialog(gcmPresenter.getGgcmView().getContext());
		dialog.setCancelable(false);
	}
	
	@Override
    protected HashMap doInBackground(String... params){
		//HashMap hmResult = new HashMap();
		HashMap hmResultServer = new HashMap();
        String msg = "No se pudo registrar el usuario: ".concat(params[0]);
		//String msg = "";
		
		//Recupero el gcmSenderId
//		SharedPreferences prefs = gcmPresenter.getGgcmView().getContext().getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(gcmPresenter.getGgcmView().getContext());
		
		String gcmSenderId = prefs.getString(Turnos.PROPERTY_SENDER_ID, "");
        
        try {
            if (gcm == null) {
                //gcm = GoogleCloudMessaging.getInstance(Turnos.getAppContext());
                gcm = GoogleCloudMessaging.getInstance(gcmPresenter.getGgcmView().getContext());
                
            }
            
            //Nos registramos en los servidores de GCM
            regid = gcm.register(gcmSenderId);
            
            Log.d(TAG, "Registrado en GCM: registration_id=" + regid);

            //Nos registramos en nuestro servidor
            hmResultServer = gcmHelper.registroServidor(gcmPresenter.getGgcmView().getContext(), params[0], regid, Long.valueOf(params[1]), params[2]);

            //Guardamos los datos del registro si fue registrado correctamente.
            if((Boolean)hmResultServer.get("result")){
            	msg = "Registro GCM finalizado!!! \n Usuario:".concat(params[0].concat("\n RegId:".concat(regid)));
            	gcmHelper.saveRegistrationId(gcmPresenter.getGgcmView().getContext(), params[0], regid, Long.valueOf(params[1]), params[2]);
            	//hmResult.put("msg", msg);
            	//hmResult.put("result", Boolean.TRUE);
            //}else{
            //	hmResult.put("msg", (String)hmResultServer.get("msg"));
            //  hmResult.put("result", Boolean.FALSE);
            }

        }catch (IOException ex){
        	Log.d(TAG, "Error registro en GCM:" + ex.getMessage());
        	msg = msg.concat(". Motivo: ").concat(ex.getMessage());
        	
        	//hmResult.put("msg", msg);
        	hmResultServer.put("result", Boolean.FALSE);
        	hmResultServer.put("msg", msg);
        }
        
        return hmResultServer;
    }
	
	protected void onPreExecute() {
        this.dialog.setMessage("Registrando Usuario");
        this.dialog.setTitle("Por favor espere...");
        this.dialog.show();
    }
	
	protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(HashMap hmResult) {
    	
    	if (dialog.isShowing()) {
            dialog.dismiss();
        }
    	
    	if ( (Boolean)hmResult.get("result") ){
    		//Llamo a la pantalla principial main
    		Intent mainActivity = new Intent(gcmPresenter.getGgcmView().getContext(), MainActivity.class);
    		gcmPresenter.getGgcmView().startView(mainActivity);
    	}
    	
    	Toast.makeText(gcmPresenter.getGgcmView().getContext(), ((String)hmResult.get("msg")).toString(), Toast.LENGTH_LONG).show();
    }
}
