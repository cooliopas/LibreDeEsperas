package com.uxor.turnos.view;


import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.Town;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.GCMPresenter;
import com.uxor.turnos.util.ConnectionCheckReceiver;
import com.uxor.turnos.util.CustomActionBar;
import com.uxor.turnos.util.FontTypes;
import com.uxor.turnos.view.adapter.CustomTownsSpinnerAdapter;
 
public class RegistrarGCMActivity extends CustomActionBar implements OnClickListener, IGCMView{
    
	private static final String LOGTAG = "RegistrarGCMActivity";
	private List<Town> vcTownsCbo;
	private Long townId;
	private String email;
	GCMPresenter gcmPresenter;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        
		//Seteo el activity al receiver que checkea el estado de la conexion
		ConnectionCheckReceiver.setActivity(this);
        
        //Obtengo el presenter para la vista
        gcmPresenter = new GCMPresenter(this);
        
        try {
			gcmPresenter.setInitParameters();
		} catch (UxorException e) {
			// TODO Auto-generated catch block
			Log.e(LOGTAG, e.getMessage());
		}
        
        //Recupero el mail principal del teléfono
        try {
			this.email = gcmPresenter.retrievePrimaryEmailUser();
		} catch (UxorException e1) {
			e1.printStackTrace();
		}
		
        //Recupero las ciudades
        try {
			gcmPresenter.retrieveTownsCbo();
		} catch (UxorException e) {
			e.printStackTrace();
		}
        
        //Recuperamos la información pasada en el intent
//        Bundle bundle = this.getIntent().getExtras();
 
        //Seteo el titulo        
        setFormatTitleActionBar(getTitle().toString());
        
        
        //Titulo cliente nuevo
        TextView txtNewClientUserReg = (TextView) findViewById(R.id.txtNewClientUserReg);
        txtNewClientUserReg.setTypeface(FontTypes.getFontType(this, FontTypes.ROBOTO_CONDENSED_BOLD));
        
        //Texo de Ayuda
        TextView txtHelpUserReg = (TextView) findViewById(R.id.txtHelpUserReg);
        txtHelpUserReg.setTypeface(FontTypes.getFontType(this, FontTypes.ROBOTO_CONDENSED_LIGHT));
        
        //Nombre de usuario
        TextView txtUserNameUserReg = (TextView) findViewById(R.id.txtUserNameUserReg);
        txtUserNameUserReg.setTypeface(FontTypes.getFontType(this, FontTypes.ROBOTO_CONDENSED_LIGHT));
        
        //Seteo el listener al boton registrar usuario
        TextView btnRegisterUserReg = (TextView)findViewById(R.id.btnRegisterUserReg);
        btnRegisterUserReg.setOnClickListener(this);
//        Button btnRegistrarGCM = (Button)findViewById(R.id.btnRegistrarGCM);
//        btnRegistrarGCM.setOnClickListener(this);
        
        //Recupero el combo de ciudades
        Spinner sprCboTowns = (Spinner)findViewById(R.id.cmbTownsUserReg);
        
        //Creamos el adaptador para el combo ciudades
//        ArrayAdapter<Town> spinner_adapter = new ArrayAdapter<Town>(this, android.R.layout.simple_spinner_item, this.vcTownsCbo);
        CustomTownsSpinnerAdapter spinner_adapter = new CustomTownsSpinnerAdapter(this, R.layout.spinner_item, this.vcTownsCbo);
        
        //Añadimos el layout para el menú y se lo damos al spinner
//        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        
        //Seteo el adaptador al combo de ciudades
        sprCboTowns.setAdapter(spinner_adapter);
        
        
        sprCboTowns.setOnItemSelectedListener(
    	        new AdapterView.OnItemSelectedListener() {
    	        public void onItemSelected(AdapterView<?> parent, android.view.View v, int pos, long id) {
    	    		if (parent.getId() == R.id.cmbTownsUserReg) {
    	    			setTownId(((Town) parent.getItemAtPosition(pos)).getId());
    				}
    	            
    	    		//CharSequence msg = "Seleccinado: " + getTownId().toString();
    	            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    	        }
    	 
    	        public void onNothingSelected(AdapterView<?> parent) {
    	            //lblMensaje.setText("");
    	        }
    	});
        
    }

    

	@Override
	public void onClick(View v) {
		
		//Nombre de usuario
		EditText txtUserNameUserReg = (EditText)findViewById(R.id.txtUserNameUserReg);
		 
		switch (v.getId()) {
		
//			case R.id.btnRegistrarGCM: 
//				try {
//					gcmPresenter.registarUsuario(txtUsuarioGCM.getText().toString(), this.townId, this.email);
//				} catch (UxorException e) {
//					e.printStackTrace();
//				}
//				Toast.makeText(this, "Nombre de usuario GCM: ".concat(txtUsuarioGCM.getText().toString()), Toast.LENGTH_SHORT).show();
//			break;
			
			case R.id.btnRegisterUserReg: 
				try {
					gcmPresenter.registarUsuario(txtUserNameUserReg.getText().toString(), this.townId, this.email);
				} catch (UxorException e) {
					e.printStackTrace();
				}
			break;
		}
			
		
	}
	
	
	@Override
	protected void onResume()
	{
	    super.onResume();
	 
	    //checkPlayServices();
	}

	@Override
	public Context getContext() {
		return this;
	}


	@Override
	public void setVcTownsCbo(List<Town> vcTownsCbo) throws UxorException {
		this.vcTownsCbo=vcTownsCbo;
	}

	public Long getTownId() {
		return townId;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
	}


	@Override
	public void startView(Intent pIntent) {
		startActivity(pIntent);
	}
	
}