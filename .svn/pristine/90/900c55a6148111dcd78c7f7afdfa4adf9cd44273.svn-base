package com.uxor.turnos.presenter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.ITownModel;
import com.uxor.turnos.model.TownModel;
import com.uxor.turnos.presenter.helper.ApplicationHelper;
import com.uxor.turnos.presenter.helper.GcmHelper;
import com.uxor.turnos.presenter.helper.IApplicationHelper;
import com.uxor.turnos.presenter.helper.IGcmHelper;
import com.uxor.turnos.util.TareaRegistroGCM;
import com.uxor.turnos.util.Turnos;
import com.uxor.turnos.util.Util;
import com.uxor.turnos.view.IGCMView;

public class GCMPresenter {
	
                        
//	public static final String PROPERTY_USER_TYPE = "userType";
//	public static final String PROPERTY_SENDER_ID = "gcmSenderId";
    private static final String TAG = "GCMPresenter"; 
	
	private IGCMView gcmView;
	private ITownModel townModel;
	private IGcmHelper gcmHelper;
	private IApplicationHelper applicationHelper;
	//private String regid;
	//private GoogleCloudMessaging gcm;
	
	public GCMPresenter(){
	}
	
	public GCMPresenter(IGCMView view){
		this(view, new TownModel(), GcmHelper.getInstance(), ApplicationHelper.getInstance());
	}
	
	public GCMPresenter(IGCMView view, ITownModel townModel, IGcmHelper gcmHelper, IApplicationHelper applicationHelper){
		this.gcmView = view;
		this.townModel = townModel;
		this.gcmHelper = gcmHelper;
		this.applicationHelper = applicationHelper;
	}
	
	public String retrievePrimaryEmailUser() throws UxorException{
		return gcmHelper.getEmail(gcmView.getContext());
	}
	
	/*
	 * Devuelve el listado de ciudades para cargar en el combo
	 */
	public void retrieveTownsCbo() throws UxorException{
		//Actualizo la grilla de ciudades
		//this.townModel.retrieveTownsCbo();
		this.gcmView.setVcTownsCbo(this.townModel.retrieveTownsCbo());
	}
	
	public void registarUsuario(String nombre, Long townId, String email) throws UxorException{
		
		// Check if Internet Connection present
        if (!gcmHelper.isConnectingToInternet()) {
             
        	applicationHelper.showAlertDialog(gcmView.getContext() ,
                    "Error en la conexión de Internet",
                    "Por favor, conecte internet para registrar el usuario en nuestros servidores", false);
            return;
        }
		
        //Chequemos si está instalado Google Play Services
//        if(gcmHelper.checkPlayServices((RegistrarGCMActivity)gcmView))
        //{
                //this.gcm = GoogleCloudMessaging.getInstance(gcmView.getContext());
 
                //Obtenemos el Registration ID guardado
//                regid = gcmHelper.getRegistrationId(gcmView.getContext(), nombre);
 
                //Si no disponemos de Registration ID comenzamos el registro
//                if (regid.equals("")) {
                    TareaRegistroGCM tarea = new TareaRegistroGCM(this, gcmHelper);
                    tarea.execute(nombre, townId.toString(), email);
//                }else{
//                	Toast.makeText(Turnos.getAppContext(), "El usuario ya se encuentra registrado en GCM!!!", Toast.LENGTH_SHORT).show();
//                }
        //}
        //else
        //{
            //    Log.i(TAG, "No se ha encontrado Google Play Services.");
//                    applicationHelper.showAlertDialog(gcmView.getContext() ,
//                            "Google Play Service",
//                            "Error al verficiar el servicio de Google Play Service: no se encuentra instalado en el equipo", false);
//                    return;
            //}
	}
	
	/*
	 * Agrega en la SharePreference el tipo de usuario y el id de la aplicación para el envio de notificaciones
	 */
	public void setInitParameters() throws UxorException{
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(gcmView.getContext());
	    
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(Turnos.PROPERTY_USER_TYPE, Util.getPropertieValue(gcmView.getContext(), "config.properties", "application.parameter.phonetype"));
	    editor.putString(Turnos.PROPERTY_SENDER_ID, Util.getPropertieValue(gcmView.getContext(), "config.properties", "application.parameter.senderId"));
	    
	    editor.commit();
	}
	
	
	public IGCMView getGgcmView(){
		return this.gcmView;
	}
	
	
	
}
