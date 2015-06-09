package com.uxor.turnos.util;

import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.uxor.turnos.domain.Turno;
import com.uxor.turnos.presenter.MainPresenter;

public class TareaWSInfoTurnos extends AsyncTask<List,Integer,Boolean>{
	
	
	private static final String TAG = "AsyncTaskWSInfoTurnos"; 
	private MainPresenter mainPresenter;
	private Turno[] listaTurnos;
	private ProgressDialog dialog;
	
	
	public TareaWSInfoTurnos(MainPresenter mainPresenter){
		this.mainPresenter = mainPresenter;
		dialog = new ProgressDialog(mainPresenter.getMainView().getContext());
		dialog.setCancelable(false);
	}
	
	@Override
    protected Boolean doInBackground(List... params){
        Boolean result = true;
        
        try 
        {
        	
        	
//            String srtServerTurnsIds = params[0];
            //Split string (1,2,3) into array
            
//            ServerIds serverIds = new ServerIds();
            
            //for string array y agregar los ids al objeto de tipo ServerIds
//            serverIds.add(params[0]);
//            serverIds.add("2");
            
//            Log.d(TAG, "Ids Servidor: " + srtServerTurnsIds);

          //Recupero la info de los turnos actualizada
//           HashMap hmTurnos = turnosHelper.retrieveInfoTurns(serverIds);
            
           List<String> vcServerTurnsIds = params[0];
           
           //Consulta al webservice para recuperar los turnos a actualizar en la base local sqlite
           mainPresenter.retrieveWSInfoTurns(vcServerTurnsIds);


        }catch (Exception ex){
        	Log.d(TAG, "Error registro en GCM:" + ex.getMessage());
        	return Boolean.FALSE;	
        }
        
        return result;
    }
	
	protected void onPreExecute() {
        this.dialog.setMessage("Consultando turnos");
        this.dialog.setTitle("Por favor espere...");
        this.dialog.show();
    }
	
	protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Boolean result) {
    	//TODO: Actualizo los turnos con el array de listadoTurnos
    	if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

	
}
