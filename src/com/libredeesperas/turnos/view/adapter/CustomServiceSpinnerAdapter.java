package com.uxor.turnos.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uxor.turnos.domain.Service;
import com.uxor.turnos.util.FontTypes;

public class CustomServiceSpinnerAdapter extends ArrayAdapter<Service> {

	private Context context;
	
	public CustomServiceSpinnerAdapter(Context context, int textViewResourceId, List<Service> vcServiceCbo) {
        super(context, textViewResourceId, vcServiceCbo);
        this.context = context;
    }

    public TextView getView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);
        v.setTypeface(FontTypes.getFontType(this.context, FontTypes.ROBOTO_CONDENSED_LIGHT));
        v.setGravity(Gravity.CENTER);
        return v;
    }

    public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);
        v.setHeight(80);
        v.setTypeface(FontTypes.getFontType(this.context, FontTypes.ROBOTO_CONDENSED_LIGHT));
        return v;
    }
    
}
