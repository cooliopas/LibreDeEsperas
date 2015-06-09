package com.uxor.turnos.util;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.uxor.turnos.domain.Turno;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.DelTurnPresenter;
import com.uxor.turnos.view.MainActivity;

public class TaskWSDelServerTurn extends AsyncTask<Long,Integer,HashMap>{
	
	
	private static final String TAG = "AsyncTaskWSUpdateServerTurn"; 
	private DelTurnPresenter delTurnPresenter;
	private ProgressDialog dialog;
	
	
	public TaskWSDelServerTurn(DelTurnPresenter delTurnPresenter){
		this.delTurnPresenter = delTurnPresenter;
		dialog = new ProgressDialog(delTurnPresenter.getDelTurnView().getContext());
		dialog.setCancelable(false);
	}
	
	@Override
    protected HashMap doInBackground(Long... params){
        Boolean result = true;
        HashMap hmResultServer = new HashMap();
        Long IdTurn = (Long)params[0];
        
        try{
           
           //Actualiza el turno en el servidor
           hmResultServer = delTurnPresenter.delServerTurn(IdTurn);

        }catch (UxorException ex){
        	Log.d(TAG, "Error al actualizar el turno en el servidor:" + ex.getMessage());
        	hmResultServer.put("result", Boolean.FALSE);
        	hmResultServer.put("msg", "No se pudo cancelar el turno. ");
        }
        
        return hmResultServer;
    }
	
	protected void onPreExecute() {
        this.dialog.setMessage("Cancelando el turno");
        this.dialog.setTitle("Por favor espere...");
        this.dialog.show();
    }
	
	protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(HashMap hmResultServer) {
    	//TODO: Actualizo los turnos con el array de listadoTurnos
    	if (dialog.isShowing()) {
            dialog.dismiss();
        }
    	
    	if ( (Boolean)hmResultServer.get("result") ){
    		//Llamo a la pantalla principial main
    		Intent mainActivity = new Intent(delTurnPresenter.getDelTurnView().getContext(), MainActivity.class);
    		delTurnPresenter.getDelTurnView().startView(mainActivity);
    	}
    	
    	Toast.makeText(delTurnPresenter.getDelTurnView().getContext(), ((String)hmResultServer.get("msg")).toString(), Toast.LENGTH_LONG).show();
    }

	
}
