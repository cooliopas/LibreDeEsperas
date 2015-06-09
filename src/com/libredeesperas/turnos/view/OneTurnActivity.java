package com.uxor.turnos.view;
 

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.OneTurnPresenter;
import com.uxor.turnos.util.ConnectionCheckReceiver;
import com.uxor.turnos.util.CustomActionBar;
import com.uxor.turnos.view.dto.LstTurnDto;
 
public class OneTurnActivity extends CustomActionBar implements IOneTurnView{
	
	private static final String LOGTAG = "AddTurnActivity";

	private List<LstTurnDto> vcTurns;
	private LstTurnDto lstTurnDto;
	private OneTurnPresenter oneTurnPresenter;
	private BigDecimal turnBefore;
	private BigDecimal waitingTime;
	private String turnNumber;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_one_turn);
        
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
      	
      	
      //Obtengo el presenter
      	oneTurnPresenter = new OneTurnPresenter(this);
        
       
      	//Recupero el único turno activo
        try {
			oneTurnPresenter.retrieveOneTurn();
		} catch (UxorException e) {
			Log.e(LOGTAG, e.getMessage());
		}
        
        
        //Cantidad de turnos adelante
      	TextView txtTurnBefore = (TextView)findViewById(R.id.txtTurnBeforeOneTurn);
      	txtTurnBefore.setTypeface(robotoLight);
      	//txtTurnBefore.setText(lstTurnDto.getTurnBefore().toString());
        
        //Tiempo promedio de espera
      	TextView txtWaintingTime = (TextView)findViewById(R.id.txtWaitingTimeOneTurn);
      	txtWaintingTime.setTypeface(robotoThin);
      	//txtWaintingTime.setText(lstTurnDto.getWaitingTime().toString().concat("'"));
      	
        //Aprox
      	TextView txtAproxOneTurn = (TextView)findViewById(R.id.txtAproxOneTurn);
      	txtAproxOneTurn.setTypeface(robotoThin);
      	
      	//Número de turno
      	TextView txtTurnNumber = (TextView)findViewById(R.id.txtTurnNumberOneTurn);
      	txtTurnNumber.setTypeface(robotoLight);
      	//txtTurnNumber.setText(lstTurnDto.getTurnLetter().concat(lstTurnDto.getTurnNumber().toString()));
      	
      	
        //Tipo de Servicio
      	TextView txtServiceTypeDesc = (TextView)findViewById(R.id.txtServiceTypeDescOneTurn);
      	txtServiceTypeDesc.setTypeface(robotoThin);
      	txtServiceTypeDesc.setText(lstTurnDto.getmServiceDesc());
      	
      	
        //Nombre del Cliente
      	TextView txtClientName = (TextView)findViewById(R.id.txtClientNameOneTurn);
      	txtClientName.setTypeface(robotoLight);
      	txtClientName.setText(lstTurnDto.getClientName());
      	
        //Dirección Sucursal
      	TextView txtBranchAddress = (TextView)findViewById(R.id.txtBranchDescOneTurn);
      	txtBranchAddress.setTypeface(robotoThin);
      	txtBranchAddress.setText(lstTurnDto.getBranchAddress());
      	
      	
    }
    
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		MenuItem itemLoadData = menu.findItem(R.id.action_loadData);
		itemLoadData.setVisible(false);
		
		MenuItem itemRegGCM = menu.findItem(R.id.action_regGCM);
		itemRegGCM.setVisible(false);
		
		MenuItem btnAdd = menu.findItem(R.id.action_add);
		btnAdd.setVisible(false);
		
		MenuItem btnAccept = menu.findItem(R.id.action_accept);
		btnAccept.setVisible(false);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch(item.getItemId()){
	        case android.R.id.home: 
	            onBackPressed();
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


		
	public LstTurnDto getLstTurnDto() {
		return lstTurnDto;
	}


	@Override
	public void setLstTurnDto(LstTurnDto lstTurnDto) throws UxorException {
		this.lstTurnDto = lstTurnDto;
	}


	public BigDecimal getTurnBefore() {
		return turnBefore;
	}


	public void setTurnBefore(BigDecimal turnBefore) {
      	TextView txtTurnBefore = (TextView)findViewById(R.id.txtTurnBeforeOneTurn);
      	txtTurnBefore.setText(turnBefore.toString());
	}


	@Override
	public void setWaitingTime(BigDecimal waitingTime) {
		TextView txtWaintingTime = (TextView)findViewById(R.id.txtWaitingTimeOneTurn);
		txtWaintingTime.setText(waitingTime.toString());
		
		//AGREGAR LO DE ONEFRAGMENT
	}
	
	@Override
	public void setImgLogoClient(String logoPath) {
		ImageView imgLogoClient = (ImageView)findViewById(R.id.imgLogoClientOneTurn);
		
		imgLogoClient.setImageResource(getDrawable(getContext(),logoPath));		
	}
	
    public static int getDrawable(Context context, String name)
    {
//        Assert.assertNotNull(context);
//        Assert.assertNotNull(name);
        return context.getResources().getIdentifier(name,
                "drawable", context.getPackageName());
    }

	public String getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(String turnNumber) {
		TextView txtTurnNumber = (TextView)findViewById(R.id.txtTurnNumberOneTurn);
		txtTurnNumber.setText(turnNumber);
	}


	@Override
	public BigDecimal getWaitingTime() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getServiceTypeDesc() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setServiceTypeDesc(String serviceTypeDesc) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getClientName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setClientName(String clientName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getBranchAddress() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setBranchAddress(String branchAddress) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateView() {
		// TODO Auto-generated method stub
		
	}
	
		
}