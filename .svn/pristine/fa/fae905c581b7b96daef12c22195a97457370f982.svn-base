package com.uxor.turnos.view;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.Service;
import com.uxor.turnos.domain.Turno;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.AddTurnPresenter;
import com.uxor.turnos.presenter.MainPresenter;
import com.uxor.turnos.presenter.helper.ApplicationHelper;
import com.uxor.turnos.util.ConnectionCheckReceiver;
import com.uxor.turnos.util.CustomActionBar;
import com.uxor.turnos.util.FontTypes;
import com.uxor.turnos.view.adapter.CustomServiceSpinnerAdapter;
 
public class AddTurnActivity extends CustomActionBar implements OnClickListener, IAddTurnView{
	
	private static final String LOGTAG = "AddTurnActivity";
	private static final int DATE_DIALOG_ID = 0;
	
	private List<Service> vcServiceCbo;
	private AddTurnPresenter addTurnPresenter;
	private MainPresenter mainPresenter;
	private BigDecimal serviceId;
	private int mYear;
	private int mMonth;
	private int mDay;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Seteo el titulo de la contextbar
        setFormatTitleActionBar(getTitle().toString());
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
		//Seteo el activity al receiver que checkea el estado de la conexion
		ConnectionCheckReceiver.setActivity(this);
        
        setContentView(R.layout.activity_agregar_turno);
        
        //Obtengo el presenter
        addTurnPresenter = new AddTurnPresenter(this);
        
        
        try {	
 
        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();
 
        //Obtengo los servicios para el combo	
        addTurnPresenter.retrieveServicesCbo(new BigDecimal (bundle.getLong("BRANCH_ID")));
              
        //Ayuda
        TextView txtHelpDescription = (TextView) findViewById(R.id.txtHelpDescriptionAddTurn);
        
        
      //Seteo el logo del cliente
      	ImageView logo = (ImageView) findViewById(R.id.imgLogoAddTurn);
      	String imageName = bundle.getString("CLIENT_IMAGE_NAME");
        String PACKAGE_NAME = this.getPackageName();
      	int imgId = this.getResources().getIdentifier(PACKAGE_NAME+":drawable/"+imageName, null, null);
      	logo.setImageResource(imgId);
        
        //Nombre del Cliente
        TextView txtClientName = (TextView) findViewById(R.id.txtClientNameAddTurn);
        txtClientName.setText(bundle.getString("CLIENTE_NAME"));
        txtClientName.setTypeface(FontTypes.getFontType(this, FontTypes.ROBOTO_CONDENSED_LIGHT));
        
        //Dirección de la sucursal
        TextView txtBranchAddress = (TextView) findViewById(R.id.txtBranchAddressAddTurn);
        txtBranchAddress.setTypeface(FontTypes.getFontType(this, FontTypes.ROBOTO_CONDENSED_LIGHT));
        txtBranchAddress.setText(bundle.getString("BRANCH_ADDRESS"));
        
        //Telefono de la sucursal
        TextView txtBranchPhoneNumber = (TextView) findViewById(R.id.txtBranchPhoneNumberAddTurn);
        txtBranchPhoneNumber.setTypeface(FontTypes.getFontType(this, FontTypes.ROBOTO_CONDENSED_LIGHT));
        txtBranchPhoneNumber.setText(bundle.getString("BRANCH_PHONE_NUMBER"));
        
        //Fecha del Turno
        TextView txtCalendarAddTurn = (TextView) findViewById(R.id.txtCalendarAddTurn);
        txtCalendarAddTurn.setTypeface(FontTypes.getFontType(this, FontTypes.ROBOTO_CONDENSED_LIGHT));
        
        //Numero del turno
        TextView txtTurnNumberAddTurn = (TextView) findViewById(R.id.txtTurnNumberAddTurn);
        txtTurnNumberAddTurn.setTypeface(FontTypes.getFontType(this, FontTypes.ROBOTO_CONDENSED_BOLD));
        
        
        //Combo Servicios asociados a la sucursal
        Spinner cmbServices = (Spinner)findViewById(R.id.cmbServicesAddTurn);
        
        //Creamos el adaptador
        //ArrayAdapter<Service> spinner_adapter = new ArrayAdapter<Service>(this, android.R.layout.simple_spinner_item, this.vcServiceCbo);
        CustomServiceSpinnerAdapter spinner_adapter = new CustomServiceSpinnerAdapter(this, R.layout.spinner_item, this.vcServiceCbo);
        
        //Añadimos el layout para el menú y se lo damos al spinner
        //spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        
        
        //Seteo el adaptador al combo de servicios
        cmbServices.setAdapter(spinner_adapter);
        
		TextView serviceCongestion = (TextView) findViewById(R.id.txtCongestionServiceTurn);
		serviceCongestion.setTypeface(FontTypes.getFontType(this, FontTypes.ROBOTO_CONDENSED_LIGHT));

        //seteo la congestion por primera vez
        if (!spinner_adapter.isEmpty())
        {  		
			Boolean backLog = spinner_adapter.getItem(0).getBackLog();

			if (backLog!=null && backLog==true) //Si hay un servicio con backlog en true llamo al ws de congestion
			{
		        serviceId = spinner_adapter.getItem(0).getBranchId();
	        	setServiceCongestion(serviceId);			
			}
			else //Oculto el txtView de congestion
			{
				serviceCongestion.setVisibility(View.GONE);
			}
        }
        else
        {
    		serviceCongestion.setText("No hay servicios disponibles");
        }
        
        cmbServices.setOnItemSelectedListener(
    	        new AdapterView.OnItemSelectedListener() {
    	        public void onItemSelected(AdapterView<?> parent, android.view.View v, int pos, long id) {
    	    		if (parent.getId() == R.id.cmbServicesAddTurn) {
    	    			serviceId = ((Service) parent.getItemAtPosition(pos)).getId();
    	    			Boolean backLog = ((Service) parent.getItemAtPosition(pos)).getBackLog();
    	    			
    	    			if (backLog!=null && backLog==true) //Si hay un servicio con backlog en true llamo al ws de congestion
    	    			{
	    	    			//Cambio la congestion
	    	    	        setServiceCongestion(serviceId);	
    	    			}
    	    			else
    	    			{
    	    				TextView serviceCongestion = (TextView) findViewById(R.id.txtCongestionServiceTurn);
    	    				serviceCongestion.setVisibility(View.GONE);
    	    			}
    				}
    	            
//    	    		CharSequence msg = "Seleccinado: " + serviceId.toString();
//    	            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    	        }
    	 
    	        public void onNothingSelected(AdapterView<?> parent) {
//    	            lblMensaje.setText("");
    	        }
    	});

        TextView btnAccept = (TextView) findViewById(R.id.btnAcceptAddTurn);
        btnAccept.setOnClickListener(this);
        
        //Seteo evento onclick a la caja de texto
        TextView txtDate = (TextView) findViewById(R.id.txtCalendarAddTurn);
        txtDate.setOnClickListener(this);
        
        setCurrentDateOnView();
        
        } catch (UxorException e) {
			Log.e(LOGTAG, e.getMessage());
		}
    }
    
   
	
	@Override
	public void onClick(View v) {

		TextView txtNroTurno = (TextView)findViewById(R.id.txtTurnNumberAddTurn);
		TextView txtDate = (TextView) findViewById(R.id.txtCalendarAddTurn);
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		 
		switch (v.getId()) {
			case R.id.btnAcceptAddTurn: 
				Turno turn = new Turno();
				turn.setClientServiceId(this.serviceId);
				
				if (txtNroTurno.getText().toString().length() == 0){
					txtNroTurno.setError( "Debe ingresar un numero de turno!" );
					break;
				}
				
				
				turn.setTurnNumber(txtNroTurno.getText().toString());
								
				
				try {
					turn.setTurnDate( new Timestamp(df.parse(txtDate.getText().toString()).getTime()));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				//Agrego el turno
				try {
					addTurnPresenter.insertTurn(turn);
				} catch (UxorException e) {
					Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
					Toast.makeText(this, "Error al insertar el turno!!!", Toast.LENGTH_SHORT).show();
				}
				
				break;
				
			case R.id.txtCalendarAddTurn:
				showDialog(DATE_DIALOG_ID);
				break;
		
		}
		
	}
	
	public void setServiceCongestion(BigDecimal serviceId) {
		try
		{
			addTurnPresenter.serviceCongestion(serviceId);

		}
		catch (UxorException e) {
			Log.e(LOGTAG, e.getMessage());
		}
	}
	
	public void setServiceCongestionOnView(String congestionString)
	{
		TextView serviceCongestion = (TextView) findViewById(R.id.txtCongestionServiceTurn);
		serviceCongestion.setText(congestionString);
	}
	
	// display current date
	public void setCurrentDateOnView() {
	 
			TextView txtDate = (TextView) findViewById(R.id.txtCalendarAddTurn);
//			ImageView imgBtnDate = (ImageView) findViewById(R.id.imgBtnCalendarAddTurn);
	 
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);
	 
			//Actualiza el valor de la caja de texto
			updateTxtDate();
			
//			imgBtnDate.setOnClickListener(new View.OnClickListener() {
//	            
//				@SuppressWarnings("deprecation")
//				public void onClick(View v) {
//	                showDialog(DATE_DIALOG_ID);
//	            }
//	        });
			
			
	 
		}


	private void updateTxtDate() {
		TextView txtDate = (TextView) findViewById(R.id.txtCalendarAddTurn);
		txtDate.setText(
		        new StringBuilder()
	        		.append(mDay<10?"0".concat(String.valueOf(mDay)): mDay).append("-")
	                .append(mMonth + 1<10?"0".concat(String.valueOf(mMonth+1)):mMonth+1).append("-") // Month is 0 based so add 1
	                .append(mYear).append(" "));
		}
	
	
	private DatePickerDialog.OnDateSetListener mDateSetListener =
		    new DatePickerDialog.OnDateSetListener() {

		        public void onDateSet(DatePicker view, int year, 
		                              int monthOfYear, int dayOfMonth) {
		            mYear = year;
		            mMonth = monthOfYear;
		            mDay = dayOfMonth;
		            updateTxtDate();
		        }
		    };
		    @Override
		    protected Dialog onCreateDialog(int id) {
		        switch (id) {
		        case DATE_DIALOG_ID:
		            return new DatePickerDialog(this,
		                        mDateSetListener,
		                        mYear, mMonth, mDay);
		        }
		        return null;
		    }
		 
		
	@Override
	public void setVcServicesCbo(List<Service> vcServicesCbo) throws UxorException {
		// TODO Auto-generated method stub
		this.vcServiceCbo = vcServicesCbo;
	}
	
//  Otra forma de agregarle el listener onclick a la imagen que muestra el calendario	
//	public void addListenerOnButton() {
//		 
//		btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
// 
//		btnChangeDate.setOnClickListener(new OnClickListener() {
// 
//			@Override
//			public void onClick(View v) {
// 
//				showDialog(DATE_DIALOG_ID);
// 
//			}
// 
//		});
// 
//	}
	
	
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
		
		if (isOnline())
		{
			MenuItem btnAlertConnection = menu.findItem(R.id.alert_no_connection);
			btnAlertConnection.setVisible(false);
		}
		
		return true;
	}
	
	@Override
	public void onResume() {
 	    super.onResume(); 	
 	    
        //Actualizo la Action Bar
 	    supportInvalidateOptionsMenu();
		//ActivityCompat.invalidateOptionsMenu(this);   
		
		//Seteo el activity al receiver que checkea el estado de la conexion
		ConnectionCheckReceiver.setActivity(this);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch(item.getItemId())
	    {
	    	
	        case R.id.action_settings:
	            //Toast.makeText(this, "Configuración", Toast.LENGTH_SHORT).show();
	            break;
	            
	        case R.id.action_help:
	            //Toast.makeText(this, "Ayuda", Toast.LENGTH_SHORT).show();
	            break;
	            
	        case R.id.action_accept:
	            //Toast.makeText(this, "Aceptar", Toast.LENGTH_SHORT).show();
	            break;
	            
	        case android.R.id.home: 
	            onBackPressed();
	            break;	          	
	        	 
	        case R.id.alert_no_connection:
	            Toast.makeText(this,  getResources().getString(R.string.alertNoConnectionTitle), Toast.LENGTH_SHORT).show();
	            ApplicationHelper helper = new ApplicationHelper();
	            helper.showAlertDialog(getContext(),getResources().getString(R.string.alertNoConnectionTitle),getResources().getString(R.string.alertNoConnectionMessage),false);
           	 	break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 
	    return true;
	}
	
	@Override
	public Context getContext() {
		return this;
	}
	
	public void startView(Intent pIntent){
		startActivity(pIntent);
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
}