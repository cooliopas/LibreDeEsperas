package com.uxor.turnos.util;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.ConfigurationPresenter;
import com.uxor.turnos.presenter.helper.ConfigurationHelper;
import com.uxor.turnos.presenter.helper.IConfigurationHelper;

public class TaskWSUpdateConfigurationData extends AsyncTask<String,Integer,HashMap>{
	
	
	private static final String TAG = "AsyncTaskWSUpdateConfigurationData"; 
	private ConfigurationPresenter confPresenter;
	private IConfigurationHelper confHelper;
	private ProgressDialog dialog;
	
	
	public TaskWSUpdateConfigurationData(ConfigurationPresenter confPresenter){
		this.confPresenter = confPresenter;
		this.confHelper = ConfigurationHelper.getInstance();
		dialog = new ProgressDialog(confPresenter.getConfView().getContext());
		dialog.setCancelable(false);
	}
	
	@Override
    protected HashMap doInBackground(String... params){
        Boolean result = true;
        HashMap hmConfigurationData = new HashMap();
        String gcmCode = (String)params[0];
        
        //Muestra el popup al usuario unos segundos...
        try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        try{
           
           //Recupero las actualizaciones de  turnos en el servidor
        	hmConfigurationData = confPresenter.getWSUpdateConfigurationData(gcmCode);

        }catch (UxorException ex){
        	Log.d(TAG, "Error al actualizar los datos de configuración:" + ex.getMessage());
        }
        
        return hmConfigurationData;
    }
	
	protected void onPreExecute() {
        this.dialog.setMessage("Actualizando datos de configuración.");
        this.dialog.setTitle("Por favor espere...");
        this.dialog.show();
    }
	
	protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(HashMap hmServerResult) {
    	
    	//Cierro el cuadro de dialogo
    	if (dialog.isShowing()) {
            dialog.dismiss();
        }
    	
    	if ( !(Boolean)hmServerResult.get("resultWithErrors") ){
    		hmServerResult.put("msg","Los datos de configuración fueron actualizados correctamente!!!");
    	}
    	  	
    	Toast.makeText(confPresenter.getConfView().getContext(), ((String)hmServerResult.get("msg")).toString(), Toast.LENGTH_LONG).show();
    }
    
	
}
