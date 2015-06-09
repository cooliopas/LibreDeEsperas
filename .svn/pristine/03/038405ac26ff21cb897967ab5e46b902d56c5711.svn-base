package com.uxor.turnos.view.adapter;

import java.util.List;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uxor.turnos.R;
import com.uxor.turnos.view.dto.BranchsQtyDto;


public class AdaptadorProveedores extends ArrayAdapter<BranchsQtyDto> {
    	
		FragmentActivity context;
		List<BranchsQtyDto> vcBranchsQty;
    	
		public AdaptadorProveedores(FragmentActivity context, List<BranchsQtyDto> vcBranchsQty) {
    		super(context, R.layout.list_item_client_type, vcBranchsQty);
    		this.context = context;
    		this.vcBranchsQty = vcBranchsQty;
    	}
    	
    	public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View item = inflater.inflate(R.layout.list_item_client_type, null);
			
			
			//Tipo de letra
			Typeface ttfRobotoLigth = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf");
			Typeface ttfRobotoCondensedLight = Typeface.createFromAsset(context.getAssets(),"fonts/RobotoCondensed-Light.ttf");
			
			TextView txtClientTypeDesc = (TextView)item.findViewById(R.id.txtClientType);
			txtClientTypeDesc.setTypeface(ttfRobotoCondensedLight);
			txtClientTypeDesc.setText(((vcBranchsQty.get(position)).getClientTypeDesc()));
			
			TextView txtBranchQty = (TextView)item.findViewById(R.id.txtBranchQty);
			txtBranchQty.setTypeface(ttfRobotoCondensedLight);
			txtBranchQty.setText((vcBranchsQty.get(position).getBranchQty().toString().concat(" sucursales")));
			txtBranchQty.setTextColor(Color.parseColor("#888888"));

			return(item);
		}
    }
