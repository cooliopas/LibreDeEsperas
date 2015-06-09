package com.uxor.turnos.view.adapter;

import java.text.SimpleDateFormat;
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
import com.uxor.turnos.domain.ServerNew;
import com.uxor.turnos.domain.Town;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.TownModel;
import com.uxor.turnos.util.FontTypes;
import com.uxor.turnos.util.Turnos;


public class AdaptadorMessageItem extends ArrayAdapter<ServerNew> {
	
	ActionBarActivity context;
	List<ServerNew> listServerNews;
	
	public AdaptadorMessageItem(ActionBarActivity context, List<ServerNew> listServerNews) {
		super(context, R.layout.list_item_message, listServerNews);
		this.context = context;
		this.listServerNews = listServerNews;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View item;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sfHour = new SimpleDateFormat("HH:mm");
		
		item = inflater.inflate(R.layout.list_item_message, null);
		
		//Hora del mensaje
		TextView txtTurnDate = (TextView)item.findViewById(R.id.LblMessageHourTurn);
		txtTurnDate.setTypeface(FontTypes.getFontType(getContext(), FontTypes.ROBOTO_CONDENSED_LIGHT));
		txtTurnDate.setText(sfHour.format(listServerNews.get(position).getRecivedDate()));
		
		//Cargo la descripcion del menu
		TextView LblMessageTurnView = (TextView)item.findViewById(R.id.LblMessageTurn);
		LblMessageTurnView.setText(this.listServerNews.get(position).getMessage());
		LblMessageTurnView.setTypeface(FontTypes.getFontType(this.context, FontTypes.ROBOTO_CONDENSED_LIGHT));


		return(item);
	}
}
