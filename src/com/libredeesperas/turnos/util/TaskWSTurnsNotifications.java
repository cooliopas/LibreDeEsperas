package com.uxor.turnos.util;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.MainPresenter;
import com.uxor.turnos.view.Fragment1;
import com.uxor.turnos.view.MainActivity;
import com.uxor.turnos.view.OneTurnFragment;

public class TaskWSTurnsNotifications extends AsyncTask<String,Integer,HashMap>{
	
	
	private static final String TAG = "AsyncTaskWSTurnsNotifications"; 
	private MainPresenter mainPresenter;
	private ProgressDialog dialog;
	
	
	public TaskWSTurnsNotifications(MainPresenter mainPresenter){
		this.mainPresenter = mainPresenter;
		dialog = new ProgressDialog(mainPresenter.getMainView().getContext());
		dialog.setCancelable(false);
	}
	
	@Override
    protected HashMap doInBackground(String... params){
        Boolean result = true;
        HashMap hmResultServer = new HashMap();
        String gcmCode = (String)params[0];
        
        try{
           
           //Recupero las actualizaciones de  turnos en el servidor
        	hmResultServer = mainPresenter.getWSTurnsNotifications(gcmCode);

        }catch (UxorException ex){
        	Log.d(TAG, "Error al recuperar las actualizaciones de turnos:" + ex.getMessage());
        }
        
        return hmResultServer;
    }
	
	protected void onPreExecute() {
        this.dialog.setMessage("Recuperando actualizaciones de turnos");
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
    	
    	if ( (Boolean)hmServerResult.get("result") ){
    		
    		hmServerResult.put("msg","Los turnos fueron actualizados correctamente!!!");
    		
    		try
    		{
	    		//Refresco la pantalla principal main
	    		mainPresenter.getMainView().refreshView();
    		}catch (Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    	
    	Toast.makeText(mainPresenter.getMainView().getContext(), ((String)hmServerResult.get("msg")).toString(), Toast.LENGTH_LONG).show();
    }

	
}
