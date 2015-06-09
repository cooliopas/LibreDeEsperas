package com.uxor.turnos.presenter;
 
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.uxor.turnos.domain.Branch;
import com.uxor.turnos.domain.Client;
import com.uxor.turnos.domain.ClientType;
import com.uxor.turnos.domain.Country;
import com.uxor.turnos.domain.Menu;
import com.uxor.turnos.domain.ServerNew;
import com.uxor.turnos.domain.Service;
import com.uxor.turnos.domain.State;
import com.uxor.turnos.domain.Town;
import com.uxor.turnos.domain.Turno;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.ConfigurationModel;
import com.uxor.turnos.model.IConfigurationModel;
import com.uxor.turnos.model.IMainModel;
import com.uxor.turnos.model.ITurnosModel;
import com.uxor.turnos.model.MainModel;
import com.uxor.turnos.model.TurnosModel;
import com.uxor.turnos.presenter.helper.ApplicationHelper;
import com.uxor.turnos.presenter.helper.ConfigurationHelper;
import com.uxor.turnos.presenter.helper.IApplicationHelper;
import com.uxor.turnos.presenter.helper.IConfigurationHelper;
import com.uxor.turnos.presenter.helper.ITurnosHelper;
import com.uxor.turnos.presenter.helper.TurnosHelper;
import com.uxor.turnos.util.MenuLeftXmlHandler;
import com.uxor.turnos.util.TareaWSInfoTurnos;
import com.uxor.turnos.util.TaskWSServerNotifications;
import com.uxor.turnos.util.TaskWSTurnsNotifications;
import com.uxor.turnos.util.TaskWSUpdateConfigurationData;
import com.uxor.turnos.util.Turnos;
import com.uxor.turnos.view.IMainView;
import com.uxor.turnos.view.dto.LstTurnDto;

public class MainPresenter {
	
	private static final String LOGTAG = "MainPresenter";
	
//	public static final String PROPERTY_USER_TYPE = "userType";
//	public static final String PROPERTY_SENDER_ID = "gcmSenderId";

	 
	private IMainView mainView;
	private IMainModel mainModel;
	private ITurnosModel turnosModel;
	private IConfigurationModel configurationModel;
	private ITurnosHelper turnosHelper;
	private IApplicationHelper applicationHelper;
	private IConfigurationHelper configurationHelper;

	public MainPresenter(IMainView view){
		this(view, new MainModel(), new TurnosModel(), new ConfigurationModel(), TurnosHelper.getInstance(), ApplicationHelper.getInstance(), ConfigurationHelper.getInstance());
	}
	
	public MainPresenter(IMainView view, IMainModel model, ITurnosModel turnosModel, IConfigurationModel configurationModel, ITurnosHelper turnosHelper, IApplicationHelper applicationHelper, IConfigurationHelper configurationHelper){
		this.mainView = view;
		this.mainModel = model;
		this.turnosModel = turnosModel;
		this.configurationModel = configurationModel;
		this.turnosHelper = turnosHelper;
		this.applicationHelper = applicationHelper;
		this.configurationHelper = configurationHelper;
		
	}
	
		
	public void loadListMenuLeft() throws UxorException{
		List<LstTurnDto> listadoTurnos = new ArrayList<LstTurnDto>();
		
		//Actualizo la grilla de turnos
		this.mainView.setLstMenuLeft(parseMenuLeftXML());
	}
	
	
	private List<Menu> parseMenuLeftXML() {
        AssetManager assetManager = Turnos.getAppContext().getAssets();
        List<Menu> listMenuLeft = null;
        String text = "";
        String textXml = "";
        try {
        	String[] files = assetManager.list("");
            InputStream is = assetManager.open("menuLeft.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
 
            MenuLeftXmlHandler myXMLHandler = new MenuLeftXmlHandler();
            xr.setContentHandler(myXMLHandler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);
    
            //Obtengo el menu izquierdo
            listMenuLeft = myXMLHandler.getListMenu();
		  
            //Cierro el inputstrem
            is.close();
            
            
            //---------------------------------------------
            InputStream input = assetManager.open("prueba.txt");
            
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            
         // byte buffer into a string
            text = new String(buffer);
            
            InputStream inputA = assetManager.open("menuLeft.xml");
            int sizeA = inputA.available();
            byte[] bufferA = new byte[sizeA];
            inputA.read(bufferA);
            inputA.close();
 
            // byte buffer into a string
            textXml = new String(bufferA);
            
            //-----------------------------------------------
           
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        
        return listMenuLeft;
    }
	
	
	public void insertTempData() throws UxorException{
		mainModel.insertTempData();
	}
	
	
	public void retrieveInfoTurns() throws UxorException{
		List<LstTurnDto> listadoTurnos = new ArrayList<LstTurnDto>();
		
		int count = this.turnosModel.getCountTurnos();
		
		//Si existe turnos para el dia actual actualizo los estados
		if (count>0){
			
			//Obtengo los turnos del día
			listadoTurnos = this.turnosModel.getListadoTurnos();
			
			String [] turnosIds = new String[listadoTurnos.size()];
			
			//Armo un array de string con los id's de los turnos a actualizar
			int i=0;
			String strServerTurnsIds = "";
			for (LstTurnDto listadoTurnoDto : listadoTurnos) {
				turnosIds[i++]=listadoTurnoDto.getTurnId().toString();
				if (listadoTurnos.size()<=i){
					strServerTurnsIds.concat(listadoTurnoDto.getServerTurnId().toString());
				}else{
					strServerTurnsIds.concat(listadoTurnoDto.getServerTurnId().toString()).concat(",");
				}
			}
			
			//Lanzo tarea async para actualizar los turnos del servidor
			TareaWSInfoTurnos tarea = new TareaWSInfoTurnos(this);
			List vcTurnsIds = new ArrayList<String>();
			vcTurnsIds.add("1");
			vcTurnsIds.add("2");
            tarea.execute(vcTurnsIds);
			
			
		}
		
		//Actualizo la grilla de turnos
		//this.turnosView.setLstTurnos(this.turnosModel.getListadoTurnos());
	}
	
	
		
	public HashMap retrieveWSInfoTurns(List<String> vcServerIds) throws UxorException{
		
		HashMap hmInfoTurns = turnosHelper.retrieveInfoTurns(vcServerIds);
		
		//TODO: Actualizar turnos con la info recibida del servidor
		
		
		return hmInfoTurns;
		
	}
	
		
	/*
	 * Actualiza un turno al recibir una notificación del servidor
	 */
	public void updateTurnNotification(BigDecimal serverTurnId, BigDecimal waitingTime, BigDecimal turnsBefore, String turnStatus) throws UxorException{
		
		//Actualizo el turno
		this.turnosModel.updateTurnos(serverTurnId, waitingTime, turnsBefore, turnStatus);
	}

	public IMainView getMainView() {
		return mainView;
	}
	
		
	
	/*
	 * Agrega en la SharePreference el tipo de usuario y el id de la aplicación para el envio de notificaciones
	 */
//	public void setInitParameters() throws UxorException{
//		SharedPreferences prefs = mainView.getContext().getSharedPreferences(
//	    		MainActivity.class.getSimpleName(), 
//	            Context.MODE_PRIVATE);
//	    
//	    SharedPreferences.Editor editor = prefs.edit();
//	    editor.putString(PROPERTY_USER_TYPE, "ADD");
//	    editor.putString(PROPERTY_SENDER_ID, "281952056801");
//	    
//	    editor.commit();
//	}
	
	public int getPendingTurnsQty() throws UxorException{
		return this.turnosModel.getPendingTurnsQty();
	}
	
	public int getDisabledTurnsQty() throws UxorException{
		return this.turnosModel.getDisabledTurnsQty();
	}
	
	public int getActiveTurnsQty() throws UxorException{
		return this.turnosModel.getActiveTurnsQty();
	}
	
	
	
	
	public void getTurnsNotifications() throws UxorException{
		
		//Obtengo la SharePreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mainView.getContext());
        
        //Recupero el gcmCode registrado en el celular
        String gcmCode = prefs.getString(Turnos.PROPERTY_REG_ID, "");
		
		// Check if Internet Connection present
        if (!applicationHelper.isConnectingToInternet()) {
             
        	applicationHelper.showAlertDialog(mainView.getContext() ,
                    "Error en la conexión de internet",
                    "Por favor, contecte internet para poder sincronizar el estado de los turnos activos...", false);
        	
        }else{
        	//Llamar tarea asincronica para recuperar las actualizaciones de los turnos activos
        	TaskWSTurnsNotifications taskWSTurnsNotifications = new TaskWSTurnsNotifications(this);
        	taskWSTurnsNotifications.execute(gcmCode);
        }
		
	}
	
	public void getServerNotifications() throws UxorException{
		
		//Obtengo la SharePreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mainView.getContext());
        
        //Recupero el gcmCode registrado en el celular
        String gcmCode = prefs.getString(Turnos.PROPERTY_REG_ID, "");
		
		// Check if Internet Connection present
        if (!applicationHelper.isConnectingToInternet()) {
             
        	applicationHelper.showAlertDialog(mainView.getContext() ,
                    "Error en la conexión de internet",
                    "Por favor, contecte internet para poder recibir notificaciones del servidor...", false);
        	
        }else{
        	//Llamar tarea asincronica para recuperar las actualizaciones de los turnos activos
        	TaskWSServerNotifications taskWSServerNotifications = new TaskWSServerNotifications(this);
        	taskWSServerNotifications.execute(gcmCode);
        }
		
	}
	
	
	public HashMap getWSTurnsNotifications(String gcmCode) throws UxorException{
		
	    //Llamo al webservice para recuperar las notificaciones de turnos
		HashMap hmServerResult = turnosHelper.retrieveTurnsNotifications(mainView.getContext(), gcmCode);
		List<Turno> lstTurnsNotifications = (List<Turno>)hmServerResult.get("lstTurnsNotifications");
		Boolean result = (Boolean)hmServerResult.get("result");
		
		if (result){
			
			//Recorro lstTurnsNotifications y actualizo la información de los turnos
	    	for (Turno turn : lstTurnsNotifications) {
    			turnosModel.updateTurnos(turn.getServerTurnId(), turn.getWaitingTime(), turn.getTurnsBefore(), turn.getStatus());
			}
		}
		
		return hmServerResult;
		
	}
	
	public HashMap getWSServerNotifications(String gcmCode) throws UxorException{
	    //Llamo al webservice para recuperar las notificaciones de servicios
		HashMap hmServerResult = turnosHelper.retrieveServerNotifications(mainView.getContext(), gcmCode);
		List<ServerNew> lstServerNotifications = (List<ServerNew>)hmServerResult.get("lstServerNotifications");
		Boolean result = (Boolean)hmServerResult.get("result");
		
		if (result){
			
			//Recorro lstTurnsNotifications y actualizo la información de los turnos
	    	for (ServerNew serverNew : lstServerNotifications) {
	    			turnosModel.insertServerNew(serverNew);
	    			
	    			//Descomentar cuando sepamos los tipos que se pasan desde el WS
//	    			if (serverNew.getType().equals(turnos.NOTIFICACION_STN_ERROR)) //Si es un mensaje de Error deshabilito el servicio
//	    			{
	    				updateServiceStatus(serverNew.getServerTurnId().longValue(),Boolean.FALSE);
//	    			}

			}
		}
		
		return hmServerResult;
		
	}
	
	public void updateServiceStatus(Long serverTurnId, Boolean newStatus)
	{		
		try {
			BigDecimal serviceId = turnosModel.getServiceIdByServerTurnId(serverTurnId);

			/*
			 * Si el serviceId es nulo significa que el turno existe en el servidor pero en la base de datos del celular no existe mas
			 * Esto puede ser porque el usuario desinstalo el programa o borro los datos desde el administrador de programas de android. 
			 */
			if (serviceId!=null){
				turnosModel.updateServiceStatus(serviceId, newStatus);
			}
			
		} catch (UxorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
