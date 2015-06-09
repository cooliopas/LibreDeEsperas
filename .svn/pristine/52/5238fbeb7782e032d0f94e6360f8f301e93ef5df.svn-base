package com.uxor.turnos.view.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uxor.turnos.R;
import com.uxor.turnos.util.FontTypes;
import com.uxor.turnos.view.dto.LstTurnDto;


public class AdapterInactiveTurns extends ArrayAdapter<LstTurnDto> {
	
	FragmentActivity context;
	List<LstTurnDto> listInactiveTurns;
	
	
	public AdapterInactiveTurns(FragmentActivity context, List<LstTurnDto> listInactiveTurns) {
		super(context, R.layout.list_item_inactive_turn, listInactiveTurns);
		this.context = context;
		this.listInactiveTurns = listInactiveTurns;
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View item = inflater.inflate(R.layout.list_item_inactive_turn, null);
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sfHour = new SimpleDateFormat("HH:mm");
		
		
		TextView txtClientName = (TextView)item.findViewById(R.id.txtClientNameLytItemInactiveTurn);
		txtClientName.setTypeface(FontTypes.getFontType(getContext(), FontTypes.ROBOTO_CONDENSED_LIGHT));
		txtClientName.setText(listInactiveTurns.get(position).getClientName());
		
		TextView txtBranchAddress = (TextView)item.findViewById(R.id.txtBranchAddressLytItemInactiveTurn);
		txtBranchAddress.setTypeface(FontTypes.getFontType(getContext(), FontTypes.ROBOTO_CONDENSED_LIGHT));
		txtBranchAddress.setText(listInactiveTurns.get(position).getBranchAddress());
		
		TextView txtBranchPhoneNumber = (TextView)item.findViewById(R.id.txtBranchPhoneNumberLytItemInactiveTurn);
		txtBranchPhoneNumber.setTypeface(FontTypes.getFontType(getContext(), FontTypes.ROBOTO_CONDENSED_LIGHT));
		txtBranchPhoneNumber.setText(listInactiveTurns.get(position).getBranchPhoneNumber());
		
		TextView txtTurnDate = (TextView)item.findViewById(R.id.txtTurnDateLytItemInactiveTurn);
		txtTurnDate.setTypeface(FontTypes.getFontType(getContext(), FontTypes.ROBOTO_CONDENSED_LIGHT));
		txtTurnDate.setText(df.format(listInactiveTurns.get(position).getTurnDate()));
		
		TextView txtTurnHour = (TextView)item.findViewById(R.id.txtTurnHourLytItemInactiveTurn);
		txtTurnHour.setTypeface(FontTypes.getFontType(getContext(), FontTypes.ROBOTO_CONDENSED_LIGHT));
		
		int ini = sfHour.format(listInactiveTurns.get(position).getTurnHour()).length();
		int fin = sfHour.format(listInactiveTurns.get(position).getTurnHour()).length()+2;
		
		SpannableStringBuilder sb = new SpannableStringBuilder(sfHour.format(listInactiveTurns.get(position).getTurnHour()).concat("hs"));
		sb.setSpan(new RelativeSizeSpan(0.5f), ini, fin, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		txtTurnHour.setText(sb);
		
		TextView txtServiceTypeDesc = (TextView)item.findViewById(R.id.txtServiceTypeDescLytItemInactiveTurn);
		txtServiceTypeDesc.setTypeface(FontTypes.getFontType(getContext(), FontTypes.ROBOTO_CONDENSED_LIGHT));
		txtServiceTypeDesc.setText(listInactiveTurns.get(position).getmServiceDesc());
		
		 //Seteo el logo del cliente
      	ImageView logo = (ImageView) item.findViewById(R.id.imageCompanyLogoLytItemInactiveTurn);
      	String imageName = listInactiveTurns.get(position).getClientImageName();
        String PACKAGE_NAME = this.context.getPackageName();
      	int imgId = this.context.getResources().getIdentifier(PACKAGE_NAME+":drawable/"+imageName, null, null);
      	logo.setImageResource(imgId);
		
		return(item);
	}
}