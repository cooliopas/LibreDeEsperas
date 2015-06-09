package com.uxor.turnos.view;
 

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.TurnosModel;
import com.uxor.turnos.presenter.DelTurnPresenter;
import com.uxor.turnos.presenter.helper.ApplicationHelper;
import com.uxor.turnos.util.ConnectionCheckReceiver;
import com.uxor.turnos.util.CustomActionBar;
import com.uxor.turnos.view.adapter.AdaptadorMessageItem;
import com.uxor.turnos.view.adapter.AdapterBranchsList;
 
public class DetalleTurnoActivity extends CustomActionBar implements OnClickListener, IDelTurnView {
	
	   private String clientName;
	   private String branchName;
	   private String branchAddress;
	   private String branchPhoneNumber;
	   private String serviceTypeDesc;
	   private Long turnBefore;
	   private Long waitingTime;
	   private String turnNumber;
	   private String turnLetter;
	   private String turnStatus;
	   private String clientImageName;
	   private Long turnId;
	   private static final String LOGTAG = "Turnos-DetalleTurnoActivity";
	   private Long serverTurnId;
	   private Boolean serviceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_detalle_turno);
        
        //Seteo el titulo de la contextbar
        setFormatTitleActionBar(getTitle().toString());
        
        //Seteo el boton back en el action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
		//Seteo el activity al receiver que checkea el estado de la conexion
		ConnectionCheckReceiver.setActivity(this);
        
        //Tipo de letra
      	Typeface robotoLight = Typeface.createFromAsset(this.getAssets(),"fonts/Roboto-Light.ttf");
      	Typeface robotoThin = Typeface.createFromAsset(this.getAssets(),"fonts/Roboto-Thin.ttf");
      	Typeface robotoBlack = Typeface.createFromAsset(this.getAssets(),"fonts/Roboto-Black.ttf");
      	Typeface robotoCondensedLight = Typeface.createFromAsset(this.getAssets(),"fonts/RobotoCondensed-Light.ttf");
      	Typeface robotoCondensedBold = Typeface.createFromAsset(this.getAssets(),"fonts/RobotoCondensed-Bold.ttf");
      	
        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();
        
        this.clientName = bundle.getString("CLIENT_NAME");
        this.branchName = bundle.getString("BRANCH_NAME");
        this.branchAddress = bundle.getString("BRANCH_ADDRESS");
        this.branchPhoneNumber = bundle.getString("BRANCH_PHONE_NUMBER");
        this.serviceTypeDesc = bundle.getString("SERVICE_TYPE_DESC");
        this.turnBefore =  bundle.getLong("TURN_BEFORE");
        this.waitingTime =  bundle.getLong("WAITING_TIME");
        this.turnNumber =  bundle.getString("TURN_NUMBER");
        this.turnLetter = bundle.getString("TURN_LETTER");
        this.turnStatus = bundle.getString("TURN_STATUS");
        this.clientImageName = bundle.getString("CLIENT_IMAGE_NAME");
        this.turnId =  bundle.getLong("TURN_ID");
        this.serverTurnId =  bundle.getLong("SERVER_TURN_ID");
        this.serviceStatus =  bundle.getBoolean("SERVICE_STATUS");

        //Nombre del Cliente
      	TextView txtClientName = (TextView)findViewById(R.id.txtClientNameTurnDetail);
      	txtClientName.setTypeface(robotoLight);
      	txtClientName.setText(clientName.toString());
      	
        //Nombre de la sucursal
      	TextView txtBranchName = (TextView)findViewById(R.id.txtBranchNameTurnDetail);
      	txtBranchName.setTypeface(robotoLight);
      	txtBranchName.setText(branchName.toString());
      	
        //Dirección Sucursal
      	TextView txtBranchAddress = (TextView)findViewById(R.id.txtBranchAddressTurnDetail);
      	txtBranchAddress.setTypeface(robotoCondensedLight);
      	txtBranchAddress.setText(branchAddress.toString());
      	
        //Telefono Sucursal
      	TextView txtBranchPhoneNumber = (TextView)findViewById(R.id.txtBranchPhoneNumberTurnDetail);
      	txtBranchPhoneNumber.setTypeface(robotoCondensedLight);
      	txtBranchPhoneNumber.setText(branchPhoneNumber.toString());
      	
      	 //Tipo de Servicio
      	TextView txtServiceTypeDesc = (TextView)findViewById(R.id.txtServiceTypeDescTurnDetail);
      	txtServiceTypeDesc.setTypeface(robotoLight);
      	txtServiceTypeDesc.setText(serviceTypeDesc.toString());
      	
      	 
      	//Cantidad de personas adelante
      	TextView txtTurnBeforeTurnDetail = (TextView)findViewById(R.id.txtTurnBeforeTurnDetail);
      	txtTurnBeforeTurnDetail.setTypeface(robotoLight);
		if (!serviceStatus) //Si esta el servicio deshabilitado pongo en gris el turno.
		{
			txtTurnBeforeTurnDetail.setBackgroundResource(R.drawable.circle_grey);	
		}else
		{		
			if (turnBefore>=10){
				txtTurnBeforeTurnDetail.setBackgroundResource(R.drawable.circle_blue);
			}else{
				if (turnBefore>=5){
					txtTurnBeforeTurnDetail.setBackgroundResource(R.drawable.circle_orange);
				}else{
					txtTurnBeforeTurnDetail.setBackgroundResource(R.drawable.circle_magenta);
				}
			}
		}
		txtTurnBeforeTurnDetail.setText(turnBefore.compareTo(Long.valueOf(-1))==0?"?":turnBefore.toString());
      	
		
		//Numero de turno
      	TextView txtTurnNumberTurnDetail = (TextView)findViewById(R.id.txtTurnNumberTurnDetail);
      	txtTurnNumberTurnDetail.setTypeface(robotoLight);
  		if (!turnStatus.equals("HAB")) 
  		{
  			txtTurnNumberTurnDetail.setText("Mi turno era ".concat(turnLetter.concat(turnNumber.toString()).toString()));
  		}
  		else
  		{
  			txtTurnNumberTurnDetail.setText("Mi turno es ".concat(turnLetter.concat(turnNumber.toString()).toString()));
  		}
      	     	
      	//Tiempo estimado de espera
      	TextView txtWaitingTimeTurnDetail = (TextView)findViewById(R.id.txtWaitingTimeTurnDetail);
      	txtWaitingTimeTurnDetail.setTypeface(robotoCondensedLight);
      	txtWaitingTimeTurnDetail.setText("Espera aprox. "+ (waitingTime.compareTo(Long.valueOf(-1))==0?"?":waitingTime.toString().concat(" min.")));
		
      	//Alertas y Mensajes
      	TextView txtAlertMessagesTurnDetail = (TextView)findViewById(R.id.txtAlertMessagesTurnDetail);
      	txtAlertMessagesTurnDetail.setTypeface(robotoThin);
      	
      	
      	//Seteo el logo del cliente
      	String imageName = clientImageName;
      	if (clientImageName!=null)
      	{
			ImageView imgLogoClient = (ImageView)findViewById(R.id.imgLogoClientTurnDetail);
			imgLogoClient.setImageResource(this.getResources().getIdentifier(imageName,
	                    "drawable", this.getPackageName()));
      	}
      	
      	//Agrego evento onclick a la imagen para llamar por teléfono
      	ImageView imgPhoneCall = (ImageView) findViewById(R.id.imgPhoneCallTurnDetail);
      	imgPhoneCall.setOnClickListener(this);
      	
      	
      	//Cargo la lista de mensages
      	TurnosModel turnosModel = new TurnosModel();
      	
		ListView listMessagesView = (ListView) findViewById(R.id.lstAlertMessagesTurnDetail);
        
        //Seteo el adaptador a la lista de opciones del menu nuevo
		AdaptadorMessageItem adtMessageList;
		try {
			adtMessageList = new AdaptadorMessageItem(this, turnosModel.getServerNewsByServerTurnId(this.serverTurnId));
	        listMessagesView.setAdapter(adtMessageList); 
		} catch (UxorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      	
    }
    
    
    @Override
	public void onClick(View v) {
		 
		switch (v.getId()) {
			case R.id.imgPhoneCallTurnDetail: 
				try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+this.branchPhoneNumber));
                    startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {
                     Log.e("helloandroid dialing example", "Call failed");
                }
				
				
		break;
		}
			
		
	}
    
    
    
    //Click en Cancelar/Eliminar Turno
    public AlertDialog cancelTurn()
    {
	    
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
                    // Yes button clicked
					
					DelTurnPresenter delTurnPresenter = new DelTurnPresenter(DetalleTurnoActivity.this);
					
					//elimino el turno
					try {
						delTurnPresenter.delTurn(turnId);
					} catch (UxorException e) {
						//Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
						//Toast.makeText(DetalleTurnoActivity.this, "Error al cancelar el turno!!!", Toast.LENGTH_SHORT).show();
						Toast.makeText(DetalleTurnoActivity.this, "Problemas de conexión. Por favor, intente nuevamente...", Toast.LENGTH_SHORT).show();
					}
					
                    break;

				case DialogInterface.BUTTON_NEGATIVE:
					// No button clicked
					// do nothing
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Esta seguro que desea cancelar el turno?")
				.setPositiveButton("Si", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();

		return builder.create();
	
		
    }
    
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalle_turno, menu);
  		if (!turnStatus.equals("HAB")) //Si veo el detalle de un turno no Activo no muestro el button de Eliminar de la Action Bar 
  		{
			menu.findItem(R.id.action_del).setVisible(false);  	      
  		}
  		
		if (isOnline())
		{
			MenuItem btnAlertConnection = menu.findItem(R.id.alert_no_connection);
			btnAlertConnection.setVisible(false);
		}
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch(item.getItemId()){
	        case android.R.id.home: 
	            onBackPressed();
	            break;
	        case R.id.action_del:
	        	cancelTurn();
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

	@Override
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