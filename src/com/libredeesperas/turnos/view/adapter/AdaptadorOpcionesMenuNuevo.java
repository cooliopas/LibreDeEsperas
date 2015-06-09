package com.uxor.turnos.view.adapter;

import java.math.BigDecimal;
import java.util.List;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.Menu;
import com.uxor.turnos.domain.Town;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.TownModel;
import com.uxor.turnos.util.FontTypes;
import com.uxor.turnos.util.Turnos;


public class AdaptadorOpcionesMenuNuevo extends ArrayAdapter<Menu> {
	
	ActionBarActivity context;
	List<Menu> listMenu;
	
	public AdaptadorOpcionesMenuNuevo(ActionBarActivity context, List<Menu> list) {
		super(context, R.layout.list_item_navegation,list);
		this.context = context;
		this.listMenu = list;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		
		View item;
		if (this.listMenu.get(position).getStyle().equals("USER_INFO")) //Titulo con info del usuario
		{
	        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
	     	String userName = String.valueOf(prefs.getString(Turnos.PROPERTY_USER, "-1"));
	     	Long userLocationId = Long.valueOf(prefs.getString(Turnos.PROPERTY_USER_TOWN_ID, "-1"));

	     	TownModel townModel = new TownModel();
	     	
	     	Town userTown = null;
			try {
				userTown = townModel.getTownById(userLocationId);
			} catch (UxorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     	
			item = inflater.inflate(R.layout.list_item_navegation_title, null);
			
			//Cargo el nombre de usuario
			TextView labelUserName = (TextView)item.findViewById(R.id.LblMenuTurnosNuevos);
			labelUserName.setText(userName.toUpperCase());
			labelUserName.setTypeface(FontTypes.getFontType(this.context, FontTypes.ROBOTO_CONDENSED_LIGHT));
			
			//Cargo la localizacion del usuario
			TextView labelLocationUser = (TextView)item.findViewById(R.id.LblLocationUser);
			labelLocationUser.setText(userTown.getDescription());
			labelLocationUser.setTypeface(FontTypes.getFontType(this.context, FontTypes.ROBOTO_LIGTH));
		}
		else //Item de navegacion
		{
			item = inflater.inflate(R.layout.list_item_navegation, null);
			
			//Cargo la descripcion del menu
			TextView lblOptMenu = (TextView)item.findViewById(R.id.LblMenuTurnosNuevos);
			lblOptMenu.setText(this.listMenu.get(position).getDescripicion());
			lblOptMenu.setTypeface(FontTypes.getFontType(this.context, FontTypes.ROBOTO_CONDENSED_LIGHT));
		}
		
		
		//Cargo el logo
		ImageView imgMenuLogo= (ImageView)item.findViewById(R.id.logoMenuItem);

		String uri = this.listMenu.get(position).getLogo(); 
		
        String PACKAGE_NAME = context.getPackageName();
        int imageResource = context.getResources().getIdentifier(PACKAGE_NAME+":drawable/"+uri, null, null);
        
        imgMenuLogo.setImageResource(imageResource);
        
//        if (this.listMenu.get(position).getStyle().equals("DES_ITEM"))
//        {
//        	item.setClickable(false);
//        }
		
		return(item);
	}
}
