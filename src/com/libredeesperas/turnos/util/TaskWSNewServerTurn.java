package com.uxor.turnos.util;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.uxor.turnos.domain.Turno;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.AddTurnPresenter;
import com.uxor.turnos.view.MainActivity;

public class TaskWSNewServerTurn extends AsyncTask<Turno,Integer,HashMap>{
	
	
	private static final String TAG = "AsyncTaskWSUpdateServerTurn"; 
	private AddTurnPresenter addTurnPresenter;
	private ProgressDialog dialog;
	
	
	public TaskWSNewServerTurn(AddTurnPresenter addTurnPresenter){
		this.addTurnPresenter = addTurnPresenter;
		dialog = new ProgressDialog(addTurnPresenter.getAddTurnView().getContext());
		dialog.setCancelable(false);
	}
	
	@Override
    protected HashMap doInBackground(Turno... params){
        Boolean result = true;
        HashMap hmResultServer = new HashMap();
        Turno turn = (Turno)params[0];
        
        try{
           
           //Actualiza el turno en el servidor
           hmResultServer = addTurnPresenter.newServerTurn(turn);

        }catch (UxorException ex){
        	Log.d(TAG, "Error al actualizar el turno en el servidor:" + ex.getMessage());
        	hmResultServer.put("result", Boolean.FALSE);
        	//hmResultServer.put("msg", "No se pudo registrar el turno: " + turn.getTurnNumber().toString().concat(". Motivo: ").concat(ex.getMessage()));
        	hmResultServer.put("msg", "Problemas de conexión. Por favor, intente nuevamente...");
        }
        
        return hmResultServer;
    }
	
	protected void onPreExecute() {
        this.dialog.setMessage("Agregando el turno");
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
    		Intent mainActivity = new Intent(addTurnPresenter.getAddTurnView().getContext(), MainActivity.class);
    		addTurnPresenter.getAddTurnView().startView(mainActivity);
    	}
    	
    	Toast.makeText(addTurnPresenter.getAddTurnView().getContext(), ((String)hmResultServer.get("msg")).toString(), Toast.LENGTH_LONG).show();
    }

	
}
