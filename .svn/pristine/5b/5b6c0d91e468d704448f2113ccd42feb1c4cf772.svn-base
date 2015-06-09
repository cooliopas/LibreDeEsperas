package com.uxor.turnos.view;
 

import java.math.BigDecimal;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.OneTurnPresenter;
import com.uxor.turnos.util.Util;
import com.uxor.turnos.view.dto.LstTurnDto;
 
public class OneTurnFragment extends Fragment implements IOneTurnView{
	
	private static final String LOGTAG = "OneTurnFragment";
	private LstTurnDto lstTurnDto;
	private OneTurnPresenter oneTurnPresenter;
	private BigDecimal turnBefore;
	private BigDecimal waitingTime;
	private String turnNumber;
	private String serviceTypeDesc;
	private String clientName;
	private String branchAddress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		setHasOptionsMenu(true);
				
		return inflater.inflate(R.layout.activity_one_turn, container, false);
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ((MainActivity)getActivity()).setTituloSeccion(getResources().getString(R.string.oneTurnFragment));
        
        RelativeLayout rlOneTurn_1 = (RelativeLayout)getActivity().findViewById(R.id.rlOneTurn_1);
               	
        rlOneTurn_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            	
            	//Creamos el Intent
                Intent intent = new Intent(getActivity(), DetalleTurnoActivity.class);
                
                //Añadimos la información al intent
                //intent.putExtra("Turn",lstTurnDto);
                
                //Creamos la información a pasar entre actividades
                Bundle b = new Bundle();
                b.putString("CLIENT_NAME", lstTurnDto.getClientName()!=null?lstTurnDto.getClientName().toString():"");    //Nombre del Cliente
                b.putString("BRANCH_NAME", lstTurnDto.getBranchAlias()!=null?lstTurnDto.getBranchAlias().toString():"");    //Nombre de la sucursal
                b.putString("BRANCH_ADDRESS", lstTurnDto.getBranchAddress()!=null ? lstTurnDto.getBranchAddress().toString() : ""); //Direccion Sucursal
                b.putString("BRANCH_PHONE_NUMBER", lstTurnDto.getBranchPhoneNumber()!=null?lstTurnDto.getBranchPhoneNumber().toString():"");    //Telefono Sucursal
                b.putString("SERVICE_TYPE_DESC", lstTurnDto.getmServiceDesc()!=null?lstTurnDto.getmServiceDesc().toString():"");  //Tipo de Servicio
                b.putLong("TURN_BEFORE", lstTurnDto.getTurnBefore()!=null?lstTurnDto.getTurnBefore().longValue():-1);     //Numero de turnos adelante
                b.putLong("WAITING_TIME", lstTurnDto.getWaitingTime()!=null?lstTurnDto.getWaitingTime().longValue():-1);  //Tiempo promedio de espera
                b.putString("TURN_NUMBER", lstTurnDto.getTurnNumber()!=null?lstTurnDto.getTurnNumber().toString():"");    //Numero de turno
                b.putString("TURN_LETTER", lstTurnDto.getTurnLetter()!=null?lstTurnDto.getTurnLetter().toString():"");    //Letra del turno
                b.putString("TURN_STATUS", lstTurnDto.getTurnStatus()!=null?lstTurnDto.getTurnStatus().toString():"");    //Estado del turno
                b.putString("CLIENT_IMAGE_NAME", lstTurnDto.getClientImageName()!=null?lstTurnDto.getClientImageName().toString():""); //Logo del Cliente

                b.putLong("TURN_ID", lstTurnDto.getTurnId().longValue());    //Id del turno
                b.putLong("SERVER_TURN_ID", lstTurnDto.getServerTurnId().longValue());    //Server Id del turno
                b.putBoolean("SERVICE_STATUS", lstTurnDto.getServiceStatus().booleanValue());    //Service Status

                

                //Añadimos la información al intent
                intent.putExtras(b);
                
                //Iniciamos la nueva actividad
                startActivity(intent);
                
            }
        });
        
       
        //Obtengo el presenter
      	oneTurnPresenter = new OneTurnPresenter(this);
        
      	//Recupero el único turno activo
        try {
			oneTurnPresenter.retrieveOneTurn();
		} catch (UxorException e) {
			Log.e(LOGTAG, e.getMessage());
		}
        
        
        //Seteo el background segun la cantidad de turnos faltantes 
        setBackground(rlOneTurn_1);
        
        //Formateo el texto de la vista
        formatTextView();
        
      	
    }
	
	public void setBackground(RelativeLayout rlOneTurn_1){
		if (!lstTurnDto.getServiceStatus()) //Si esta el servicio deshabilitado pongo en gris el turno. FALSE=Deshabilitado
		{
			rlOneTurn_1.setBackgroundResource(R.drawable.circle_grey_selector);
		}else
		{
			//Seteo el background segun la cantidad de turnos faltantes 
	        if (lstTurnDto.getTurnBefore().compareTo(new BigDecimal(Util.getPropertieValue(this.getContext(), "config.properties", "turnBefore.circle.blue")))>=0){
	    		//rlOneTurn_1.setBackgroundResource(R.drawable.circle_blue_one_turn);
	    		rlOneTurn_1.setBackgroundResource(R.drawable.circle_blue_selector);
			}else{
				if (lstTurnDto.getTurnBefore().compareTo(new BigDecimal(Util.getPropertieValue(this.getContext(), "config.properties", "turnBefore.circle.orange")))>=0){
					rlOneTurn_1.setBackgroundResource(R.drawable.circle_orange_selector);
				}else{
					rlOneTurn_1.setBackgroundResource(R.drawable.circle_magenta_selector);
				}
			}
		}
	}
    
    
    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		MenuItem itemLoadData = menu.findItem(R.id.action_loadData);
		itemLoadData.setVisible(false);
		
		MenuItem itemRegGCM = menu.findItem(R.id.action_regGCM);
		itemRegGCM.setVisible(false);
		
		MenuItem btnAdd = menu.findItem(R.id.action_add);
		btnAdd.setVisible(false);
		
		MenuItem btnAccept = menu.findItem(R.id.action_accept);
		btnAccept.setVisible(false);
		
		MenuItem btnHelp = menu.findItem(R.id.action_help);
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
	public Context getContext() {
		return this.getActivity();
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
      	TextView txtTurnBefore = (TextView)getActivity().findViewById(R.id.txtTurnBeforeOneTurn);
      	
  		TextView txtPeopleOneTurn = (TextView)getActivity().findViewById(R.id.txtPeopleOneTurn);
  		TextView txtCloserOneTurn = (TextView)getActivity().findViewById(R.id.txtCloserOneTurn);
  		ImageView imgPeopleOneTurnView =  (ImageView)getActivity().findViewById(R.id.imgPeopleOneTurn);
  		
      	if (turnBefore.toString().equals("-1")){
      		txtTurnBefore.setText("?");
      	}
      	else
  		{
      		if (turnBefore.toString().equals("0"))
      		{
      			DisplayMetrics scale = getResources().getDisplayMetrics();
      			float scaleDensity = scale.density; //Hay que usar dp
      			
      			txtTurnBefore.setText("TU TURNO");
      			
          		txtCloserOneTurn.setText("ES");

      			int paddingDpAsPixels = (int) (60*scaleDensity + 0.5f);     	
      			txtTurnBefore.setPadding(0,paddingDpAsPixels,0,0);
          		paddingDpAsPixels = (int) (30*scaleDensity + 0.5f);
      			txtCloserOneTurn.setPadding(0,paddingDpAsPixels,0,0);
      			
      			scaleDensity = scale.scaledDensity; //Hay que usar sp
      			int sizeAsPixels = (int) (27*scaleDensity + 0.5f);
          		txtTurnBefore.setTextSize(sizeAsPixels); 
      			sizeAsPixels = (int) (22*scaleDensity + 0.5f);
          		txtCloserOneTurn.setTextSize(sizeAsPixels);  
          		
          		txtPeopleOneTurn.setVisibility(View.GONE);
          		imgPeopleOneTurnView.setVisibility(View.GONE);
      		}
      		else{
      			txtTurnBefore.setText(turnBefore.toString());
      		}
      	}
      	
	}

	
	public BigDecimal getWaitingTime() {
		return waitingTime;
	}
	
	@Override
	public void setWaitingTime(BigDecimal waitingTime) {
		TextView txtWaintingTime = (TextView)getActivity().findViewById(R.id.txtWaitingTimeOneTurn);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

		if (!lstTurnDto.getServiceStatus() || waitingTime.toString().equals("-10") || waitingTime.toString().equals("-1")){
			lp.setMargins(convertDpToPx(262),convertDpToPx(238),0,0);
			txtWaintingTime.setText("?");
		}else{
			
			switch (waitingTime.toString().length())
			{
				case 1: //1 Digito
				{
					//lp.setMargins(convertDpToPx(257),convertDpToPx(238),0,0);
					if (Util.retrieveScreenDensity(getActivity().getWindowManager())==DisplayMetrics.DENSITY_LOW){
						lp.setMargins(convertDpToPx(257),convertDpToPx(240),0,0);
					}else{
						lp.setMargins(convertDpToPx(257),convertDpToPx(238),0,0);
					}
				}
				break;
				case 2: //2 Digitos
				{
					lp.setMargins(convertDpToPx(248),convertDpToPx(238),0,0);
				}break;
				case 3: //3 Digitos
				{
					lp.setMargins(convertDpToPx(239),convertDpToPx(238),0,0);
				}break;
			}
			
			txtWaintingTime.setText(waitingTime.toString().concat("'"));
		}
		
		txtWaintingTime.setLayoutParams(lp);

		
	}
	
	@Override
	public void setImgLogoClient(String logoPath) {
		ImageView imgLogoClient = (ImageView)getActivity().findViewById(R.id.imgLogoClientOneTurn);
		imgLogoClient.setImageResource(getDrawable(getContext(),logoPath));
	}
	
    public static int getDrawable(Context context, String name)
    {
//        Assert.assertNotNull(context);
//        Assert.assertNotNull(name);
//    	String PACKAGE_NAME = context.getPackageName();
//      	return context.getResources().getIdentifier(PACKAGE_NAME+":drawable/"+name, null, null);
        return context.getResources().getIdentifier(name,
                "drawable", context.getPackageName());
    }
	
	public static int convertDpToPx(int dp)
	{
	    return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}

	public String getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(String turnNumber) {
		TextView txtTurnNumber = (TextView)getActivity().findViewById(R.id.txtTurnNumberOneTurn);
		txtTurnNumber.setText(turnNumber);
	}

	public String getServiceTypeDesc() {
		return serviceTypeDesc;
	}

	public void setServiceTypeDesc(String serviceTypeDesc) {
		TextView txtServiceTypeDesc = (TextView)getActivity().findViewById(R.id.txtServiceTypeDescOneTurn);
      	txtServiceTypeDesc.setText(serviceTypeDesc);
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		TextView txtClientName = (TextView)getActivity().findViewById(R.id.txtClientNameOneTurn);
      	txtClientName.setText(clientName);
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		TextView txtBranchAddress = (TextView)getActivity().findViewById(R.id.txtBranchDescOneTurn);
      	txtBranchAddress.setText(branchAddress);
	}
	
	
	
	/**
	 * Formatea el texto de la vista 
	 */
	private void formatTextView(){
		
		 //Tipo de letra
      	Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Light.ttf");
      	Typeface robotoThin = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Thin.ttf");
      	Typeface robotoBlack = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Black.ttf");
		
		//Cantidad de turnos adelante
      	TextView txtTurnBefore = (TextView)getActivity().findViewById(R.id.txtTurnBeforeOneTurn);
      	txtTurnBefore.setTypeface(robotoLight);
        
        //Tiempo promedio de espera
      	TextView txtWaintingTime = (TextView)getActivity().findViewById(R.id.txtWaitingTimeOneTurn);
      	txtWaintingTime.setTypeface(robotoThin);
      	
        //Texto "Aprox"
      	TextView txtAproxOneTurn = (TextView)getActivity().findViewById(R.id.txtAproxOneTurn);
      	txtAproxOneTurn.setTypeface(robotoThin);
      	
      	//Número de turno
      	TextView txtTurnNumber = (TextView)getActivity().findViewById(R.id.txtTurnNumberOneTurn);
      	txtTurnNumber.setTypeface(robotoLight);
      	
        //Tipo de Servicio
      	TextView txtServiceTypeDesc = (TextView)getActivity().findViewById(R.id.txtServiceTypeDescOneTurn);
      	txtServiceTypeDesc.setTypeface(robotoThin);
      	
      	
        //Nombre del Cliente
      	TextView txtClientName = (TextView)getActivity().findViewById(R.id.txtClientNameOneTurn);
      	txtClientName.setTypeface(robotoLight);
      	
        //Dirección Sucursal
      	TextView txtBranchAddress = (TextView)getActivity().findViewById(R.id.txtBranchDescOneTurn);
      	txtBranchAddress.setTypeface(robotoThin);
		
	}
	
	public void updateView(){
		
		RelativeLayout rlOneTurn_1 = (RelativeLayout)getActivity().findViewById(R.id.rlOneTurn_1);
		
		//Obtengo el presenter
      	oneTurnPresenter = new OneTurnPresenter(this);
        
      	//Recupero el único turno activo
        try {
			oneTurnPresenter.retrieveOneTurn();
		} catch (UxorException e) {
			Log.e(LOGTAG, e.getMessage());
		}
        
        
        //Seteo el background segun la cantidad de turnos faltantes 
        setBackground(rlOneTurn_1);
        
        //Formateo el texto de la vista
        formatTextView();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		String tituloActionBar = getResources().getString(R.string.oneTurnFragment);
        setFormatTitleActionBar(tituloActionBar);
	}
	
	private  void setFormatTitleActionBar(String title){
	    
		((ActionBarActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
		((ActionBarActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

	    LayoutInflater inflator = (LayoutInflater)((ActionBarActivity)getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View v = inflator.inflate(R.layout.title_view, null);
	    
	    //Se utiliza un textview personalizado para cambiar el tipo de letra.
	    ((TextView)v.findViewById(R.id.title_action_bar)).setText(title);
	    
	    //assign the view to the actionbar
	    ((ActionBarActivity)getActivity()).getSupportActionBar().setCustomView(v);
	    
	}
	
		
}