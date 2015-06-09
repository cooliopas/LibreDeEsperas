package com.uxor.turnos.view.adapter;

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
import com.uxor.turnos.domain.ItemMedia;
import com.uxor.turnos.domain.Menu;
import com.uxor.turnos.domain.Town;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.TownModel;
import com.uxor.turnos.util.FontTypes;
import com.uxor.turnos.util.Turnos;


public class AdaptadorSocialMediaItem extends ArrayAdapter<ItemMedia> {
	
	ActionBarActivity context;
	List<ItemMedia> listItemMedia;
	
	public AdaptadorSocialMediaItem(ActionBarActivity context, List<ItemMedia> listItemMedia) {
		super(context, R.layout.list_item_social_media, listItemMedia);
		this.context = context;
		this.listItemMedia = listItemMedia;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		
		View item;
		
		item = inflater.inflate(R.layout.list_item_social_media, null);
			
		//Cargo la descripcion del menu
		TextView lblOptMenu = (TextView)item.findViewById(R.id.LblMenuTurnosNuevos);
		lblOptMenu.setText(this.listItemMedia.get(position).getDescripcion());
		lblOptMenu.setTypeface(FontTypes.getFontType(this.context, FontTypes.ROBOTO_CONDENSED_LIGHT));

		//Cargo el logo
		ImageView imgMenuLogo= (ImageView)item.findViewById(R.id.logoMenuItem);

		String uri = this.listItemMedia.get(position).getLogo();
		
        String PACKAGE_NAME = context.getPackageName();
        int imageResource = context.getResources().getIdentifier(PACKAGE_NAME+":drawable/"+uri, null, null);
        
        imgMenuLogo.setImageResource(imageResource);
        
		
		return(item);
	}
}
