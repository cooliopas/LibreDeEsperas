package com.uxor.turnos.view.adapter;

import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.Branch;


public class AdapterBranchsList extends ArrayAdapter<Branch> {
	
	Activity context;
	List<Branch> vcBranchs;
	
	public AdapterBranchsList(Activity context, List<Branch> vcBranchs) {
		super(context, R.layout.list_item_branch, vcBranchs);
		this.context = context;
		this.vcBranchs = vcBranchs;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View item = inflater.inflate(R.layout.list_item_branch, null);
		
		
		 //Configuro los tipos de letras utilizadas en el activity
		Typeface ttfRobotoLigth = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf");
		Typeface ttfRobotoCondensedLigth = Typeface.createFromAsset(context.getAssets(),"fonts/RobotoCondensed-Light.ttf");
		
		TextView txtBranchDesc = (TextView)item.findViewById(R.id.txtBranchDescLytItemBrach);
		txtBranchDesc.setText(vcBranchs.get(position).getClientName());
		txtBranchDesc.setTypeface(ttfRobotoCondensedLigth);
		
		TextView txtBranchAddress = (TextView)item.findViewById(R.id.txtBranchAddressLytItemBrach);
		txtBranchAddress.setText(vcBranchs.get(position).getAddress());
		txtBranchAddress.setTypeface(ttfRobotoCondensedLigth);
		
//		ImageView imgPhone = (ImageView) item.findViewById(R.id.imageBtnCallLytItemBrach);
//		imgPhone.setOnClickListener(new ImgPhoneViewClickListener(position));
		
		//Carga el Logo del Cliente----------------------------
		RelativeLayout rlItemBranch = (RelativeLayout)item.findViewById(R.id.rlItemBranch);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//		params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
//		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		
		//ImageView Setup
        //ImageView imgClientLogo = new ImageView(context);
		ImageView imgClientLogo = (ImageView) item.findViewById(R.id.imageClientLogo);

		
        //setting image resource
        String imageName = vcBranchs.get(position).getClient().getImageId();
        String PACKAGE_NAME = context.getPackageName();
        int imgId = context.getResources().getIdentifier(PACKAGE_NAME+":drawable/"+imageName, null, null);
   
        //Seteo la imagen del cliente
        imgClientLogo.setImageResource(imgId);
        
		//Seteo la posicion de la imagen dentro del layout
        //imgClientLogo.setLayoutParams(params);
		
		//Agrego la imagen al layout
        //rlItemBranch.addView(imgClientLogo);
		
		return(item);
	}
	
	
	private class ImgPhoneViewClickListener implements OnClickListener {
		    int position;
		    public ImgPhoneViewClickListener( int pos){
		            this.position = pos;
		    }

		    public void onClick(View v) {
		    	String phoneNumber = vcBranchs.get(this.position).getPhoneNumber();
		    	try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+"2234234848"));
                    context.startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {
                     Log.e("helloandroid dialing example", "Call failed");
                }
		    }
	}
}