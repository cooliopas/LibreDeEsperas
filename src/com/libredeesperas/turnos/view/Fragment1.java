package com.uxor.turnos.view;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.TurnosPresenter;
import com.uxor.turnos.view.adapter.AdaptadorListadoTurnos;
import com.uxor.turnos.view.dto.BranchsQtyDto;
import com.uxor.turnos.view.dto.LstTurnDto;


public class Fragment1 extends Fragment implements ITurnosView{
	
	private static final String LOGTAG = "TurnsListFragment";
	
	private TurnosPresenter turnosPresenter;
	private ListView drawerListaTurnos;
	
	private List<LstTurnDto> listadoTurnos;
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
        ((MainActivity)getActivity()).setTituloSeccion(getResources().getString(R.string.fragment1));

		//Obtengo el presenter para la vista
		turnosPresenter = new TurnosPresenter(this);
		
		//Obtengo la lista de turnos
		try {
			turnosPresenter.obtenerListadoTurnos();
		} catch (UxorException e) {
			Log.e(LOGTAG, e.getMessage());
		}
		
		return inflater.inflate(R.layout.fragment_1, container, false);
	}
	
	
	
	@Override 
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
           
        drawerListaTurnos = (ListView) getActivity().findViewById(R.id.LstTurnos); 
        
	     
	     //Seteo el adaptador 
        AdaptadorListadoTurnos adaptador = new AdaptadorListadoTurnos(getActivity(), listadoTurnos);
	    drawerListaTurnos.setAdapter(adaptador); 
	    adaptador.notifyDataSetChanged();
	    
	    drawerListaTurnos.setOnItemClickListener(new OnItemClickListener() {
	    	
	    	
	        @Override
	        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	            
	           CharSequence clientName = ((LstTurnDto)a.getAdapter().getItem(position)).getClientName();
	           CharSequence branchName = ((LstTurnDto)a.getAdapter().getItem(position)).getBranchAlias();
	           CharSequence branchAddress = ((LstTurnDto)a.getAdapter().getItem(position)).getBranchAddress();
	           CharSequence branchPhoneNumber = ((LstTurnDto)a.getAdapter().getItem(position)).getBranchPhoneNumber();
	           CharSequence serviceDesc = ((LstTurnDto)a.getAdapter().getItem(position)).getmServiceDesc();
	           BigDecimal turnBefore = ((LstTurnDto)a.getAdapter().getItem(position)).getTurnBefore();
	           BigDecimal waitingTime = ((LstTurnDto)a.getAdapter().getItem(position)).getWaitingTime();
	           String turnNumber = ((LstTurnDto)a.getAdapter().getItem(position)).getTurnNumber();
	           CharSequence turnLetter = ((LstTurnDto)a.getAdapter().getItem(position)).getTurnLetter();
	           CharSequence turnStatus = ((LstTurnDto)a.getAdapter().getItem(position)).getTurnStatus();
	           CharSequence clientImageName = ((LstTurnDto)a.getAdapter().getItem(position)).getClientImageName();
	           
	           BigDecimal turnId = ((LstTurnDto)a.getAdapter().getItem(position)).getTurnId();
	           BigDecimal serverTurnId = ((LstTurnDto)a.getAdapter().getItem(position)).getServerTurnId();
	           Boolean serviceStatus = ((LstTurnDto)a.getAdapter().getItem(position)).getServiceStatus();


	         //Creamos el Intent
             Intent intent = new Intent(getActivity(), DetalleTurnoActivity.class);

             //Creamos la información a pasar entre actividades
             Bundle b = new Bundle();
             b.putString("CLIENT_NAME", clientName!=null?clientName.toString():"");    //Nombre del Cliente
             b.putString("BRANCH_NAME", branchName!=null?branchName.toString():"");    //Nombre de la sucursal
             b.putString("BRANCH_ADDRESS", branchAddress!=null?branchAddress.toString():""); //Direccion Sucursal
             b.putString("BRANCH_PHONE_NUMBER", branchPhoneNumber!=null?branchPhoneNumber.toString():"");    //Telefono Sucursal
             b.putString("SERVICE_TYPE_DESC", serviceDesc!=null?serviceDesc.toString():"");  //Tipo de Servicio
             b.putLong("TURN_BEFORE", turnBefore!=null?turnBefore.longValue():0);     //Numero de turnos adelante
             b.putLong("WAITING_TIME", waitingTime!=null?waitingTime.longValue():0);  //Tiempo promedio de espera
             b.putString("TURN_NUMBER", turnNumber!=null?turnNumber.toString():"");    //Numero de turno
             b.putString("TURN_LETTER", turnLetter!=null?turnLetter.toString():"");    //Letra del turno
             b.putString("TURN_STATUS", turnStatus!=null?turnStatus.toString():"");    //Estado del turno
             b.putString("CLIENT_IMAGE_NAME", clientImageName!=null?clientImageName.toString():""); //Nombre del logo del cliente
             
             b.putLong("TURN_ID", turnId.longValue());    //Id del turno
             b.putLong("SERVER_TURN_ID", serverTurnId.longValue());    //Server Id del turno
             b.putBoolean("SERVICE_STATUS", serviceStatus.booleanValue());    //Service Status

             //Añadimos la información al intent
             intent.putExtras(b);

             //Iniciamos la nueva actividad
             startActivity(intent);
	        }
	    });
         
    }  
	

	@Override
	public void setLstTurnos(List<LstTurnDto> listadoTurnos) throws UxorException {
		this.listadoTurnos = listadoTurnos;
	}
	
	public void updateListView(){
		//Obtengo el presenter para la vista
		turnosPresenter = new TurnosPresenter(this);
		
		//Obtengo la lista de turnos
		try {
			turnosPresenter.obtenerListadoTurnos();
		} catch (UxorException e) {
			Log.e(LOGTAG, e.getMessage());
		}
		
		drawerListaTurnos = (ListView) getActivity().findViewById(R.id.LstTurnos);
		((AdaptadorListadoTurnos)drawerListaTurnos.getAdapter()).clear();
		((AdaptadorListadoTurnos)drawerListaTurnos.getAdapter()).addAll(listadoTurnos);
		((AdaptadorListadoTurnos)drawerListaTurnos.getAdapter()).notifyDataSetChanged();
	}


}
