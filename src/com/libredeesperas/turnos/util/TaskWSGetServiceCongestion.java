package com.uxor.turnos.util;

import java.math.BigDecimal;
import java.util.HashMap;

import android.os.AsyncTask;
import android.util.Log;

import com.uxor.turnos.domain.Turno;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.AddTurnPresenter;
//import com.uxor.turnos.view.MainActivity;

public class TaskWSGetServiceCongestion extends AsyncTask<BigDecimal,Integer,HashMap>{
	
	
	private static final String TAG = "AsyncTaskWSUpdateServerTurn"; 
	private AddTurnPresenter addTurnPresenter;
	
	
	public TaskWSGetServiceCongestion(AddTurnPresenter addTurnPresenter){
		this.addTurnPresenter = addTurnPresenter;
	}
	
	@Override
    protected HashMap doInBackground(BigDecimal... params){
        Boolean result = true;
        HashMap hmResultServer = new HashMap();
        BigDecimal serviceId = params[0];
        
        try{
           
           //Actualiza el turno en el servidor
           hmResultServer = addTurnPresenter.getServiceCongestion(serviceId);

        }catch (UxorException ex){
        	Log.d(TAG, "Error al obtener la congestión en el servidor:" + ex.getMessage());
        	hmResultServer.put("result", Boolean.FALSE);
        	hmResultServer.put("msg", "No se pudo obtener la congestión del servicio.");
        }
        
        return hmResultServer;
    }
	
	protected void onPreExecute() {
		addTurnPresenter.getAddTurnView().setServiceCongestionOnView("Obteniendo Congestión...");	
    }
	
	protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(HashMap hmResultServer) {
    	
		Boolean result = (Boolean)hmResultServer.get("result");
	    
		if (result)
		{
			Turno serverTurn = (Turno)hmResultServer.get("serverTurn");
			if (serverTurn!=null && serverTurn.getTurnsBefore()!=null)
			{
				addTurnPresenter.getAddTurnView().setServiceCongestionOnView("Congestión: " + serverTurn.getTurnsBefore().toString()+" personas.");
			}

    	}
    }

	
}
