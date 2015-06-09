package com.uxor.turnos.view.adapter;

import java.math.BigDecimal;
import java.util.List;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.TurnosModel;
import com.uxor.turnos.view.dto.LstTurnDto;


public class AdaptadorListadoTurnos extends ArrayAdapter<LstTurnDto> {
	
	FragmentActivity context;
	List<LstTurnDto> listaTurnos;
	
	public AdaptadorListadoTurnos(FragmentActivity context, List<LstTurnDto> listaTurnos) {
		super(context, R.layout.list_item_turno, listaTurnos);
		this.context = context;
		this.listaTurnos = listaTurnos;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View item = inflater.inflate(R.layout.list_item_turno, null);
		
		
//		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)item.getLayoutParams();
//		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//		ImageView imgMessage = (ImageView) item.findViewById(R.drawable.ic_ico_message);
//		imgMessage.setLayoutParams(params);
		
		
		
		//Tipo de letra
		Typeface myFont = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf");
		Typeface tfRobotoCondensedLight = Typeface.createFromAsset(context.getAssets(),"fonts/RobotoCondensed-Light.ttf");
		
//		TextView txtDireccionSucursal = (TextView)item.findViewById(R.id.txt_lt_dir_sucursal);
//		txtDireccionSucursal.setTypeface(myFont);
//		txtDireccionSucursal.setText(" / ".concat(listaTurnos.get(position).getBranchAddress()));
		
		//Formateo el texto de la cadena
		SpannableStringBuilder sb = new SpannableStringBuilder(listaTurnos.get(position).getmServiceDesc().concat(" / ".concat(listaTurnos.get(position).getBranchAddress())));
		ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(135, 135, 135)); 

		int ini = listaTurnos.get(position).getmServiceDesc().length();
		int fin = listaTurnos.get(position).getmServiceDesc().length()+listaTurnos.get(position).getBranchAddress().length()+3;
		
		// Set the text color for first 4 characters
		sb.setSpan(fcs, ini, fin, Spannable.SPAN_INCLUSIVE_INCLUSIVE); 

		TextView txtTipoServicio = (TextView)item.findViewById(R.id.txt_lt_tipo_servicio);
		txtTipoServicio.setTypeface(myFont);
		txtTipoServicio.setText(sb);
		
		TextView txtTiempoEstimadoEspera = (TextView)item.findViewById(R.id.txt_lt_tiempo_estimado_espera);
		txtTiempoEstimadoEspera.setTypeface(tfRobotoCondensedLight);
		if (listaTurnos.get(position).getWaitingTime().toString().equals("-10") || listaTurnos.get(position).getWaitingTime().toString().equals("-1")){
			txtTiempoEstimadoEspera.setText("? ".concat("' aprox."));
		}else{
			txtTiempoEstimadoEspera.setText(listaTurnos.get(position).getWaitingTime().toString().concat("' aprox."));
		}
		
		
		TextView txtCantidadNumeros = (TextView)item.findViewById(R.id.txt_lt_cantidad_turnos);
		txtCantidadNumeros.setTypeface(myFont);
		Boolean serviceStatus = listaTurnos.get(position).getServiceStatus();
		if (!serviceStatus)
		{
			txtCantidadNumeros.setBackgroundResource(R.drawable.circle_grey);
		}
		else
		{
			if (listaTurnos.get(position).getTurnBefore().compareTo(new BigDecimal("10"))>=0){
				txtCantidadNumeros.setBackgroundResource(R.drawable.circle_blue);
			}else{
				if (listaTurnos.get(position).getTurnBefore().compareTo(new BigDecimal("5"))>=0){
					txtCantidadNumeros.setBackgroundResource(R.drawable.circle_orange);
				}else{
					txtCantidadNumeros.setBackgroundResource(R.drawable.circle_magenta);
				}
			}
		}
		
		//Turnos pendientes
		if (listaTurnos.get(position).getTurnBefore().toString().equals("-1")){
			txtCantidadNumeros.setText("?");
		}else{
			txtCantidadNumeros.setText(listaTurnos.get(position).getTurnBefore().toString());
		}
		
		//Nro de Turno
		TextView txtNroTurno = (TextView)item.findViewById(R.id.txt_lt_nro_turno);
		txtNroTurno.setTypeface(myFont);
		String turnLetter = listaTurnos.get(position).getTurnLetter()!=null?listaTurnos.get(position).getTurnLetter():"";
		txtNroTurno.setText(turnLetter.concat(listaTurnos.get(position).getTurnNumber().toString()));
		
		//Carga el Logo del Cliente----------------------------
		RelativeLayout rl = (RelativeLayout)item.findViewById(R.id.rlInfoTurn);
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		//params.addRule(RelativeLayout.ABOVE, txtTipoServicio.getId()); 

	     //ImageView Setup
        ImageView imgClientLogo = new ImageView(context);
        //setting image resource
        String imageName = listaTurnos.get(position).getClientImageName().toString();
        String PACKAGE_NAME = context.getPackageName();
//        int imgId = context.getResources().getIdentifier(PACKAGE_NAME+":drawable/"+imageName+"_list" , null, null);
        int imgId = context.getResources().getIdentifier(PACKAGE_NAME+":drawable/"+imageName, null, null);
   
        imgClientLogo.setImageResource(imgId);
        
		//setting image position
        imgClientLogo.setLayoutParams(params);
		
		//adding view to layout
        rl.addView(imgClientLogo);
        
		//-----------------------------
		
        //TODO: Habilitar el código cuando se modifique el webservice para que devuelva los mensajes de alerta.

//		if (!serviceStatus) //HAY NOTICIAS
//		{
//		//TODO: Agrego el icono de mensaje/alerta si existe un evento nuevo. Esta forma agrega dinamicamente el icono si el turno tiene un mensaje nuevo de alerta al llegar una notificación.
//			RelativeLayout.LayoutParams paramsImgMessage = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//			paramsImgMessage.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//			paramsImgMessage.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//			//paramsImgMessage.addRule(RelativeLayout.BELOW, txtTiempoEstimadoEspera.getId()); 
//
//		     //ImageView Setup
//		    ImageView imgMessage = new ImageView(context);
//		    //setting image resource
//		    imgMessage.setImageResource(R.drawable.ic_ico_message);
//		    
//			//setting image position
//		    imgMessage.setLayoutParams(paramsImgMessage);
//			
//			//adding view to layout
//		    rl.addView(imgMessage);
//		}
		
		//-----------------------------
		
        //TODO:Si tiene mensajes nuevos mostrar el icono rojo de mensajes. Esta forma es ocultando y mostrando la imagen cuando el turno tiene mensajes de notificación
		//Muestro el icono que existe un nuevo evento para el turno
//        ImageView imgMessage = (ImageView) item.findViewById(R.id.imgAlert);
//      	imgMessage.setVisibility(View.VISIBLE);
      	

        //Muestro el icono que existe un nuevo evento para el turno
//    	ImageView imgView = (ImageView) item.findViewById(R.id.imgAlert);
//    	imgView.setVisibility(View.INVISIBLE);
    	
		return(item);
	}
}