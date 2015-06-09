package com.uxor.turnos.view;


import java.util.List;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.ItemMedia;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.AboutUsPresenter;
import com.uxor.turnos.util.ConnectionCheckReceiver;
import com.uxor.turnos.util.CustomActionBar;
import com.uxor.turnos.view.adapter.AdaptadorSocialMediaItem;
 
	
public class AboutUsActivity extends CustomActionBar implements IAboutUsView, OnItemClickListener  {
	private static final String LOGTAG = "Turnos-AboutUsActivity";

    private List<com.uxor.turnos.domain.ItemMedia> socialMediaList;
    private AboutUsPresenter aboutUsPresenter;
    
	private ListView lstSocialMediaView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setFormatTitleActionBar(getResources().getString(R.string.activityAboutUs).toString()); //Seteo el Titulo de la Action Bar
		
        //Seteo el boton back en el action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
		//Seteo el activity al receiver que checkea el estado de la conexion
		ConnectionCheckReceiver.setActivity(this);
        
        setContentView(R.layout.activity_about_us);
        
        aboutUsPresenter = new AboutUsPresenter(this);

        
	     try {
	    	 aboutUsPresenter.loadMediaList();
		} catch (UxorException e) {
			Log.e(LOGTAG, e.getMessage());
		}
	     

    	 lstSocialMediaView = (ListView) findViewById(R.id.lstSocialMediaListView);
	        
        //Seteo el adaptador a la lista de opciones del menu nuevo
	    AdaptadorSocialMediaItem adtSocialMediaList = new AdaptadorSocialMediaItem(this, this.socialMediaList);
        lstSocialMediaView.setAdapter(adtSocialMediaList);

      	
        lstSocialMediaView.setOnItemClickListener(this);
        
        
        //Cargo el nombre de la app con la version
      	Typeface robotoLight = Typeface.createFromAsset(this.getAssets(),"fonts/Roboto-Light.ttf");
      	
      	
      	PackageInfo pInfo = null;
		String version = "";

		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			version = pInfo.versionName.toString();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
      	String appName = getResources().getString(R.string.app_name);
      	
	   	 TextView labelLdeVersionView = (TextView) findViewById(R.id.labelLdeVerion);
	   	 labelLdeVersionView.setText(appName+" "+version);
	   	 labelLdeVersionView.setTypeface(robotoLight);
 
    }
    
    
    @Override
    protected void onResume() {
      super.onResume();

    }

    @Override
    protected void onPause() {
      super.onPause();
      
     }
    
	public void setSocialMediaList(List<com.uxor.turnos.domain.ItemMedia> socialMediaList)
			throws UxorException {
		this.socialMediaList = socialMediaList;
		
	}


	@Override
	public Context getContext() {
		return this;
	}
	
	@Override
	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		try{
	      try {
	    	  
//	    	  if (position==0){
//	    		  Uri uri = Uri.parse("market://details?id=" + getPackageName());
//	    		  Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
//	    		  try {
//	    		      startActivity(goToMarket);
//	    		  } catch (ActivityNotFoundException e) {
//	    			  Log.e(LOGTAG, e.getMessage());
//	    		  }
//	    	  }else{
	    		  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(((ItemMedia)a.getAdapter().getItem(position)).getUri1().toString())));  
//	    	  }
	    	  
	    	  
		  } catch (Exception e) {
	    	  //Si la primer URL esta mal probamos con la segunda
			  if (((ItemMedia)a.getAdapter().getItem(position)).getUri2()!=null)
			  {
		    	  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(((ItemMedia)a.getAdapter().getItem(position)).getUri2().toString())));
			  }
		  }
		} catch (Exception e) {
			Log.e(LOGTAG, e.getMessage());
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            onBackPressed();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
}
    
	
