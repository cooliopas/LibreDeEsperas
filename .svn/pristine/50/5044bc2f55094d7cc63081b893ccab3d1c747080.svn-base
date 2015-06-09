package com.uxor.turnos.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.uxor.turnos.R;

public class CustomActionBar extends ActionBarActivity{

	
	public void setFormatTitleActionBar(String title){
	    
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

	    LayoutInflater inflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View v = inflator.inflate(R.layout.title_view, null);
	    
	    //Se utiliza un textview personalizado para cambiar el tipo de letra.
	    ((TextView)v.findViewById(R.id.title_action_bar)).setText(title);
	    
	    //assign the view to the actionbar
	    this.getSupportActionBar().setCustomView(v);
	    
	}
}
