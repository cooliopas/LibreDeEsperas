package com.uxor.turnos.view;

import java.math.BigDecimal;
import java.util.List;

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
import android.widget.TextView;
import android.widget.Toast;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.InactiveTurnsPresenter;
import com.uxor.turnos.util.FontTypes;
import com.uxor.turnos.view.adapter.AdapterInactiveTurns;
import com.uxor.turnos.view.dto.LstTurnDto;


public class InactiveTurnsFragment extends Fragment implements OnItemClickListener, IInactiveTurnsView{
	
	private static final String LOGTAG = "TurnsListFragment";
	
	private InactiveTurnsPresenter inactiveTurnsPresenter;
	private ListView lstCurrentsTurns;
	private ListView lstOthersTurns;
	
	private List<LstTurnDto> inactiveCurrentMonthTurnsList;
	private List<LstTurnDto> inactiveOtherstMonthTurnsList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
        ((MainActivity)getActivity()).setTituloSeccion(getResources().getString(R.string.inactiveTurnsFragment));

		//Obtengo el presenter para la vista
		inactiveTurnsPresenter = new InactiveTurnsPresenter(this);
		
		//Obtengo la lista de turnos inactivos
		try {
			inactiveTurnsPresenter.getInactiveTurns();
		} catch (UxorException e) {
			Log.e(LOGTAG, e.getMessage());
		}
		
		return inflater.inflate(R.layout.fragment_inactive_turns, container, false);
	}
	
	
	
	
	@Override 
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
           
        lstCurrentsTurns = (ListView) getActivity().findViewById(R.id.lstCurrentsTurnsFragIT); 
        lstOthersTurns = (ListView) getActivity().findViewById(R.id.lstOthersTurnsFragIT);
	     
	     //Seteo el adaptador para los turnos del mes actual
        AdapterInactiveTurns adapterCurrentsTurns = new AdapterInactiveTurns(getActivity(), inactiveCurrentMonthTurnsList);
        lstCurrentsTurns.setAdapter(adapterCurrentsTurns); 
	    
        //Seteo el adaptador para los turnos anteriores al mes actual
	    AdapterInactiveTurns adapterOthersTurns = new AdapterInactiveTurns(getActivity(), inactiveOtherstMonthTurnsList);
	    lstOthersTurns.setAdapter(adapterOthersTurns); 
	    
	    //Asocio evento onclick a las listas
	    lstCurrentsTurns.setOnItemClickListener(this);
	    lstOthersTurns.setOnItemClickListener(this); 
	    
	    //Seteo el tipo de letra al titulo "Este Mes"
	    TextView txtCurrentsTurns = (TextView)getActivity().findViewById(R.id.txtCurrentsTurnsFragIT);
	    txtCurrentsTurns.setTypeface(FontTypes.getFontType(getActivity(), FontTypes.ROBOTO_LIGTH));
	    
	    //Seteo el tipo de letra al titulo "Anteriores"
	    TextView txtOthersTurns = (TextView)getActivity().findViewById(R.id.txtOthersTurnsFragIT);
	    txtOthersTurns.setTypeface(FontTypes.getFontType(getActivity(), FontTypes.ROBOTO_LIGTH));
	    
    }
	
	
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
		CharSequence clientImage = ((LstTurnDto)a.getAdapter().getItem(position)).getClientImageName();
        BigDecimal serverTurnId = ((LstTurnDto)a.getAdapter().getItem(position)).getServerTurnId();
        Boolean serviceStatus = ((LstTurnDto)a.getAdapter().getItem(position)).getServiceStatus();

        //Toast.makeText(getActivity(), branchAddress, Toast.LENGTH_SHORT).show();
        
      //Creamos el Intent
      Intent intent = new Intent(getActivity(), DetalleTurnoActivity.class);

      //Creamos la información a pasar entre actividades
      Bundle b = new Bundle();
      b.putString("CLIENT_NAME", clientName.toString());    //Nombre del Cliente
      b.putString("BRANCH_NAME", branchName.toString());    //Nombre de la sucursal
      b.putString("BRANCH_ADDRESS", branchAddress.toString()); //Direccion Sucursal
      b.putString("BRANCH_PHONE_NUMBER", branchPhoneNumber.toString());    //Telefono Sucursal
      b.putString("SERVICE_TYPE_DESC", serviceDesc.toString());  //Tipo de Servicio
      b.putLong("TURN_BEFORE", turnBefore.longValue());     //Numero de turnos adelante
      b.putLong("WAITING_TIME", waitingTime.longValue());  //Tiempo promedio de espera
      b.putString("TURN_NUMBER", turnNumber);    //Numero de turno
      b.putString("TURN_LETTER", turnLetter.toString());    //Letra del turno
      b.putString("TURN_STATUS", turnStatus.toString());    //Estado del turno
      b.putString("CLIENT_IMAGE_NAME", clientImage.toString()); //Logo del Cliente
      b.putLong("SERVER_TURN_ID", serverTurnId.longValue());    //Server Id del turno
      b.putBoolean("SERVICE_STATUS", serviceStatus.booleanValue());    //Service Status

      
      //Añadimos la información al intent
      intent.putExtras(b);

      //Iniciamos la nueva actividad
      startActivity(intent);
		
	}



	public void setInactiveCurrentMonthTurnsList(
			List<LstTurnDto> inactiveCurrentMonthTurnsList) {
		this.inactiveCurrentMonthTurnsList = inactiveCurrentMonthTurnsList;
	}


	public void setInactiveOtherstMonthTurnsList(
			List<LstTurnDto> inactiveOtherstMonthTurnsList) {
		this.inactiveOtherstMonthTurnsList = inactiveOtherstMonthTurnsList;
	}  
	

//	@Override
//	public void setinactiveCurrentMonthTurnsList(List<LstTurnDto> lstInactiveTurns)
//			throws UxorException {
//		this.inactiveCurrentMonthTurnsList = lstInactiveTurns;
//	}


}
