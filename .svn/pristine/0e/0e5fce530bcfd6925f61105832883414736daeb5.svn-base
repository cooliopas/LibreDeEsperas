package com.uxor.turnos.view;


import java.math.BigDecimal;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.Branch;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.BranchPresenter;
import com.uxor.turnos.presenter.MainPresenter;
import com.uxor.turnos.util.ConnectionCheckReceiver;
import com.uxor.turnos.util.CustomActionBar;
import com.uxor.turnos.util.Turnos;
import com.uxor.turnos.view.adapter.AdapterBranchsList;
 
public class BranchListActivity extends CustomActionBar implements OnItemClickListener, IBranchView {
	
	private static final String LOGTAG = "BranchListActivity";
	
	private BranchPresenter branchPresenter;
	private MainPresenter mainPresenter;
	private ListView lstBranchs;
	private List<Branch> vcBranchs;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        setContentView(R.layout.activity_branch_list);
        
		//Seteo el activity al receiver que checkea el estado de la conexion
		ConnectionCheckReceiver.setActivity(this);
        
        //Creo el presenter para la vista
        branchPresenter = new BranchPresenter(this);
        
      //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();
        
        //Obtengo la SharePreferences
//        SharedPreferences prefs = this.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
  		 
    	//Recupero la ciudad actual del usuario
	    //Long townId = prefs.getLong(Turnos.PROPERTY_USER_TOWN_ID, Long.MIN_VALUE);
        Long townId = Long.valueOf(prefs.getString(Turnos.PROPERTY_USER_TOWN_ID, "-1"));
        
      //Obtengo la lista de turnos
  		try {
  			
  			branchPresenter.retrieveBranchList(new BigDecimal(bundle.getString("CLIENT_TYPE_ID")), townId, bundle.getBoolean("MY_TOWN"));
  		} catch (UxorException e) {
  			Log.e(LOGTAG, e.getMessage());
  		}
      		

        //Seteo el titulo de la contextbar
        setFormatTitleActionBar(getResources().getString(R.string.title_activity_add_turn));
        
        //Configuro los tipos de letras utilizadas en el activity
		Typeface ttfRobotoLigth = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
		Typeface ttfRobotoCondensedLigth = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");
		
		//Seteo el tipo de letra
		TextView txtBranchDesc = (TextView)findViewById(R.id.txtBranchDescLytBranchList);
		txtBranchDesc.setTypeface(ttfRobotoCondensedLigth);
		txtBranchDesc.setText(bundle.getString("CLIENT_TYPE_DESC"));
        
		lstBranchs = (ListView) findViewById(R.id.lstBranchsLytBranchList);
        
        //Seteo el adaptador a la lista de opciones del menu nuevo
        AdapterBranchsList adtBranchList = new AdapterBranchsList(this, vcBranchs);
        lstBranchs.setAdapter(adtBranchList); 
      	
        lstBranchs.setOnItemClickListener(this);
        
    }

	@Override
	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		
		BigDecimal branch_id = ((Branch)a.getAdapter().getItem(position)).getId();
		CharSequence clientName = ((Branch)a.getAdapter().getItem(position)).getClientName();
        CharSequence address = ((Branch)a.getAdapter().getItem(position)).getAddress();
        CharSequence phoneNumber = ((Branch)a.getAdapter().getItem(position)).getPhoneNumber();
        CharSequence imageName = ((Branch)a.getAdapter().getItem(position)).getClient().getImageId();
        
        //Toast.makeText(this, clientName.toString().concat("-").concat(address.toString()), Toast.LENGTH_SHORT).show();
	        
		//Creamos el Intent
		Intent intent = new Intent(this, AddTurnActivity.class);
		
		//Creamos la información a pasar entre actividades
		Bundle b = new Bundle();
		b.putLong("BRANCH_ID", branch_id.longValue());
		b.putString("CLIENTE_NAME", clientName!=null ? clientName.toString() : "");
		b.putString("BRANCH_ADDRESS", address!=null ? address.toString() : "");
		b.putString("BRANCH_PHONE_NUMBER", phoneNumber!=null ? phoneNumber.toString() : "");
		b.putString("CLIENT_IMAGE_NAME", imageName!=null ? imageName.toString() : "");
		
		//TODO:Recuperar mas información de la base para mostrar en el detalle
		  
		//Añadimos la información al intent
		intent.putExtras(b);
		
		//Iniciamos la nueva actividad
		startActivity(intent);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		MenuItem itemLoadData = menu.findItem(R.id.action_loadData);
		itemLoadData.setVisible(false);
		
		MenuItem itemRegGCM = menu.findItem(R.id.action_regGCM);
		itemRegGCM.setVisible(false);
		
		MenuItem itemUpdateConfData = menu.findItem(R.id.action_updateConfigurationData);
		itemUpdateConfData.setVisible(false);
		
		MenuItem btnAdd = menu.findItem(R.id.action_add);
		btnAdd.setVisible(false);
		
		MenuItem btnAccept = menu.findItem(R.id.action_accept);
		btnAccept.setVisible(false);
		
		MenuItem btnHelp = menu.findItem(R.id.action_help);
		btnHelp.setVisible(false);
		
//		MenuItem itemUpdateConfData = menu.findItem(R.id.action_updateConfigurationData);
//		itemUpdateConfData.setVisible(false);
		
		MenuItem btnAlertConnection = menu.findItem(R.id.alert_no_connection);
		btnAlertConnection.setVisible(false);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch(item.getItemId())
	    {
	    	case R.id.action_loadData:
		    	//Inserto datos temporales;	
//		    	try {
//					mainPresenter.insertTempData();
					Toast.makeText(this, "Datos temporales insertados...", Toast.LENGTH_SHORT).show();
//				} catch (UxorException e) {
//					Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
//					Toast.makeText(this, "Error al insertar los datos temporales!!!", Toast.LENGTH_SHORT).show();
//				}	
	            
	            break;
	        case R.id.action_settings:
	            //Toast.makeText(this, "Configuración", Toast.LENGTH_SHORT).show();
	            break;
	            
	        case R.id.action_regGCM:
	        	//Creamos el Intent
	        	//Toast.makeText(this, "Registrar GCM", Toast.LENGTH_SHORT).show();
//	             Intent intent = new Intent(MainActivity.this, RegistrarGCMActivity.class);
//	             startActivity(intent);
	            
	            break;
	        case R.id.action_add:
	            //Toast.makeText(this, "Agregar", Toast.LENGTH_SHORT).show();
	            break;
	            
	        case android.R.id.home: 
	            onBackPressed();
	            break;
	            
//	        case R.id.action_updateConfigurationData:
//
//	        	 //Toast.makeText(this, "Actualizar Datos", Toast.LENGTH_SHORT).show();
//	        	 try{
//						mainPresenter.updateConfigurationData();
//						//Toast.makeText(this, "La Configuración de Datos fue actualizada correctamente!!!", Toast.LENGTH_SHORT).show();
//					}catch (UxorException e){
//						Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
//						Toast.makeText(this, "Error al actualizar los datos de configuración!!!", Toast.LENGTH_SHORT).show();
//					}	
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 
	    return true;
	}
	
	
//	class AdaptadorListaEntidades extends ArrayAdapter<Branch> {
//    	
//		Activity context;
//    	
//		AdaptadorListaEntidades(Activity context) {
//    		super(context, R.layout.list_item_entidad, datosEntidades);
//    		this.context = context;
//    	}
//    	
//    	public View getView(int position, View convertView, ViewGroup parent) {
//			LayoutInflater inflater = context.getLayoutInflater();
//			View item = inflater.inflate(R.layout.list_item_entidad, null);
//			
//			
//			TextView txtNombreEntidad = (TextView)item.findViewById(R.id.txtNombreEntidad);
//			txtNombreEntidad.setText(datosEntidades[position].getName());
//			
//			TextView txtDireccionEntidad = (TextView)item.findViewById(R.id.txtDireccionEntidad);
//			txtDireccionEntidad.setText(datosEntidades[position].getAddress());
//				
//			return(item);
//		}
//    }


	@Override
	public void onResume() {
 	    super.onResume(); 	
 	    
        //Actualizo la Action Bar
 	    supportInvalidateOptionsMenu();
		//ActivityCompat.invalidateOptionsMenu(this);   
		
		//Seteo el activity al receiver que checkea el estado de la conexion
		ConnectionCheckReceiver.setActivity(this);
	}

	public void setVcBranchs(List<Branch> vcBranchs) {
		this.vcBranchs = vcBranchs;
	}
		
}