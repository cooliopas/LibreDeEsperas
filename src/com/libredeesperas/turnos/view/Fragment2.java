package com.uxor.turnos.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.BranchsQtyPresenter;
import com.uxor.turnos.util.Turnos;
import com.uxor.turnos.view.adapter.AdaptadorProveedores;
import com.uxor.turnos.view.dto.BranchsQtyDto;

public class Fragment2 extends Fragment implements OnItemClickListener, IBranchsQtyView{

	private static final String LOGTAG = "Fragment2";
	
	private BranchsQtyPresenter branchsQtyPresenter;
	private List<BranchsQtyDto> vcBranchsQty;
	private List<BranchsQtyDto> vcBranchsQtyAllRest;
	private ListView lstBranchesByTown;
	private ListView lstBranchesByCountry;
	private Boolean isResumed = Boolean.FALSE;
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		setHasOptionsMenu(true);
		
        ((MainActivity)getActivity()).setTituloSeccion(getResources().getString(R.string.fragment2));
				
		//Seteo el titulo
//    	getActionBar().setTitle("Agregar Turno");
    	
		return inflater.inflate(R.layout.fragment_2, container, false);
	}
	
	@Override 
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
        
        //setHasOptionsMenu(true);
        
      //Obtengo el presenter
        branchsQtyPresenter = new BranchsQtyPresenter(this);
        
        try {
        	
        	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
   		 
        	//Recupero la ciudad actual del usuario
    	    Long townId = Long.valueOf(prefs.getString(Turnos.PROPERTY_USER_TOWN_ID, "-1"));
			
        	//Recupera el listado de sucursales por tipo de cliente para la ciudad del usuario
   	    	branchsQtyPresenter.retrieveBranchsQtyList(townId, Boolean.TRUE);
    	    
        	//Recupera el listado de sucursales por tipo de cliente para el resto del país
       		branchsQtyPresenter.retrieveBranchsQtyList(townId, Boolean.FALSE);
		
	        lstBranchesByTown = (ListView) getActivity().findViewById(R.id.lstBranchesByTownFgt2); 
	        lstBranchesByCountry = (ListView) getActivity().findViewById(R.id.lstBranchesByCountryFgt2);  
		     
		     //Seteo el adaptador a la lista de opciones del menu nuevo
//	        AdaptadorProveedores adaptadorMiCiudad = new AdaptadorProveedores(getActivity(), this.vcBranchsQty);
//	        AdaptadorProveedores adaptadorRestoPais = new AdaptadorProveedores(getActivity(), this.vcBranchsQtyAllRest);
//	        
//	        lstBranchesByTown.setAdapter(adaptadorMiCiudad); 
//	        lstBranchesByCountry.setAdapter(adaptadorRestoPais);
	        
	        loadAdapter();
		    
	        lstBranchesByTown.setOnItemClickListener(this);
	        lstBranchesByCountry.setOnItemClickListener(this);
		    
		    //Seteo los tipos de letra 
			Typeface ttfRobotoLigth = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Light.ttf");
			Typeface ttfRobotoCondensedLigth = Typeface.createFromAsset(getActivity().getAssets(),"fonts/RobotoCondensed-Light.ttf");
			
			TextView txtBranchesByTown = (TextView)getActivity().findViewById(R.id.txtBranchesByTownFgt2);
			txtBranchesByTown.setTypeface(ttfRobotoCondensedLigth);
			
			TextView txtBranchesByCountry = (TextView)getActivity().findViewById(R.id.txtBranchesByCountry);
			txtBranchesByCountry.setTypeface(ttfRobotoCondensedLigth);
	    
        } catch (UxorException e) {
			Log.e(LOGTAG, e.getMessage());
		}
         
    }  
	
	private void loadAdapter(){
		
		 //Seteo el adaptador a la lista de opciones del menu nuevo
        AdaptadorProveedores adaptadorMiCiudad = new AdaptadorProveedores(getActivity(), this.vcBranchsQty);
        AdaptadorProveedores adaptadorRestoPais = new AdaptadorProveedores(getActivity(), this.vcBranchsQtyAllRest);
        
        lstBranchesByTown.setAdapter(adaptadorMiCiudad); 
        lstBranchesByCountry.setAdapter(adaptadorRestoPais);
		
	}

	@Override
	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		//CharSequence groupCode = ((Supplier)a.getAdapter().getItem(position)).getGroupCode();
        //CharSequence groupDescription = ((Supplier)a.getAdapter().getItem(position)).getGroupDescription();
        //CharSequence supplierDescription = ((Supplier)a.getAdapter().getItem(position)).getDescription();
        CharSequence clientTypeId = ((BranchsQtyDto)a.getAdapter().getItem(position)).getClientTypeId().toString();
        CharSequence clientTypeDesc = ((BranchsQtyDto)a.getAdapter().getItem(position)).getClientTypeDesc().toString();
        Boolean myTown = ((BranchsQtyDto)a.getAdapter().getItem(position)).getMyTown();
        
        //Toast.makeText(getActivity(), clientTypeId.toString().concat("-").concat(clientTypeDesc.toString()), Toast.LENGTH_SHORT).show();
	        
		//Creamos el Intent
		Intent intent = new Intent(getActivity(), BranchListActivity.class);
		
		//Creamos la información a pasar entre actividades
		Bundle b = new Bundle();
		b.putString("CLIENT_TYPE_ID", clientTypeId.toString());
		b.putString("CLIENT_TYPE_DESC", clientTypeDesc.toString());
		b.putBoolean("MY_TOWN", myTown);

		//TODO:Recuperar mas información de la base para mostrar en el detalle
		  
		//Añadimos la información al intent
		intent.putExtras(b);
		
		//Iniciamos la nueva actividad
		startActivity(intent);
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		// TODO Auto-generated method stub
		//super.onCreateOptionsMenu(menu, inflater);
		
		MenuItem itemLoadData = menu.findItem(R.id.action_loadData);
		itemLoadData.setVisible(false);
		
		MenuItem itemRegGCM = menu.findItem(R.id.action_regGCM);
		itemRegGCM.setVisible(false);
		
		MenuItem itemHelp = menu.findItem(R.id.action_help);
		itemHelp.setVisible(false);
		
		MenuItem itemSettings = menu.findItem(R.id.action_settings);
		itemSettings.setVisible(false);
		
		MenuItem btnAdd = menu.findItem(R.id.action_add);
		btnAdd.setVisible(false);
		
		MenuItem btnAccept = menu.findItem(R.id.action_accept);
		btnAccept.setVisible(false);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch(item.getItemId()){
	    	
	        case R.id.action_help:
	            //Toast.makeText(this.getActivity(), "Ayuda", Toast.LENGTH_SHORT).show();
	            break;
	        
//	        default:
//	            return super.onOptionsItemSelected(item);
	    }
	 
	    return true;
	}
	
	

	@Override
	public void setVcBranchsQty(List<BranchsQtyDto> vcBranchsQty)
			throws UxorException {
		this.vcBranchsQty = vcBranchsQty;
	}


	public void setVcBranchsQtyAllRest(List<BranchsQtyDto> vcBranchsQtyAllRest) {
		this.vcBranchsQtyAllRest = vcBranchsQtyAllRest;
	}

	@Override
	public Context getContext() {
		return this.getActivity();
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		
		if (isVisibleToUser) {
			Log.i(LOGTAG, "Esta visible al usuario");
	    }
	}
	
	
	@Override
	public void onResume() {
		
		super.onResume();
		
//		Fragment2 myFragment = (Fragment2)getFragmentManager().findFragmentByTag("AddTurnFrag2");
//        if (!myFragment.isVisible()) {
//        	Log.i(LOGTAG, "Esta visible al usuario");
//        }
        
        if (this.isResumed){
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
			
	    	//Recupero la ciudad actual del usuario
		    Long townId = Long.valueOf(prefs.getString(Turnos.PROPERTY_USER_TOWN_ID, "-1"));
			
	    	//Recupera el listado de sucursales por tipo de cliente para la ciudad del usuario
	    	try {
				branchsQtyPresenter.retrieveBranchsQtyList(townId, Boolean.TRUE);
	    	} catch (UxorException e) {
				Log.e(LOGTAG, e.getMessage());
			}
	    	
	    	//Recupera el listado de sucursales por tipo de cliente para el resto del país
	    	try {
				branchsQtyPresenter.retrieveBranchsQtyList(townId, Boolean.FALSE);
			} catch (UxorException e) {
				Log.e(LOGTAG, e.getMessage());
			}
	    	
	    	loadAdapter();
	    	
        }else{
        	this.isResumed = Boolean.TRUE;
        }
		
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
 	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	
//	private ActionBar getActionBar() {
//	    return ((ActionBarActivity) getActivity()).getSupportActionBar();
//	}
	
	
}
