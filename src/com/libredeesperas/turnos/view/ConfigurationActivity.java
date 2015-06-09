package com.uxor.turnos.view;


import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.Town;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.ConfigurationPresenter;
import com.uxor.turnos.util.ConnectionCheckReceiver;
 
@SuppressLint("NewApi")	//Para poder obtener la ActionBar
public class ConfigurationActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener, IConfigurationView {
	 
	private static final String LOGTAG = "ConfigurationActivity";
	private List<Town> vcTownsCbo;
	private ConfigurationPresenter configurationPresenter;
	
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
		super.onCreate(savedInstanceState);
		
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
        	
            setFormatTitleActionBar(getResources().getString(R.string.activityConfiguration).toString()); //Seteo el Titulo de la Action Bar
		
            //Seteo el boton back en el action bar
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
		//Seteo el activity al receiver que checkea el estado de la conexion
		ConnectionCheckReceiver.setActivity(this);
               
        addPreferencesFromResource(R.xml.settings);
                
        configurationPresenter = new ConfigurationPresenter(this);   

        //Cargo el combo de ciudades
        retrieveTownsCbo();
        
        final EditTextPreference editTextPreference = (EditTextPreference) findPreference("user");
        
        //Muestro los valores actuales de la sharepreference en la pantalla de configuración
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
          initSummary(getPreferenceScreen().getPreference(i));
        }
        
        Preference button = (Preference)findPreference("button");
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) { 
            	try{
            		//Actualizo los datos de configuración
            		configurationPresenter.updateConfigurationData();
            		
            		//Refresco el combo ciudades
                    retrieveTownsCbo();
                    
            		return true;
            	}catch (UxorException e){
					Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
					Toast.makeText(ConfigurationActivity.this, "Error al actualizar los datos de configuración!!!", Toast.LENGTH_SHORT).show();
					return false;
				}
            }
        });
 
    }
    
    
    @Override
    protected void onResume() {
      super.onResume();
      getPreferenceScreen().getSharedPreferences()
          .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
      super.onPause();
      getPreferenceScreen().getSharedPreferences()
          .unregisterOnSharedPreferenceChangeListener(this);
     }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
        String key) {
      updatePreferences(findPreference(key));
    }

    private void initSummary(Preference p) {
      if (p instanceof PreferenceCategory) {
        PreferenceCategory cat = (PreferenceCategory) p;
        for (int i = 0; i < cat.getPreferenceCount(); i++) {
          initSummary(cat.getPreference(i));
        }
      } else {
        updatePreferences(p);
      }
    }

    private void updatePreferences(Preference p) {
      if (p instanceof EditTextPreference) {
        EditTextPreference editTextPref = (EditTextPreference) p;
        p.setSummary(editTextPref.getText());
      }
      
      if (p instanceof ListPreference) {
    	  ListPreference listTextPref = (ListPreference) p;
          p.setSummary(listTextPref.getEntry());
        }
    }


	public List<Town> getVcTownsCbo() {
		return vcTownsCbo;
	}


	public void setVcTownsCbo(List<Town> vcTownsCbo) {
		this.vcTownsCbo = vcTownsCbo;
	}
	
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		
        
	}
	
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
//	        Intent a = new Intent(this,MainActivity.class);
//	        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//	        startActivity(a);
//	        return true;
//	    }
//	    return super.onKeyDown(keyCode, event);
//	}
	
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
	
	
	public void setFormatTitleActionBar(String title){
	    
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		
	    LayoutInflater inflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View v = inflator.inflate(R.layout.title_view, null);
	    
	    //Se utiliza un textview personalizado para cambiar el tipo de letra.
	    ((TextView)v.findViewById(R.id.title_action_bar)).setText(title);
	    
	    //assign the view to the actionbar
	    this.getActionBar().setCustomView(v);
	    
	}
	
	public Context getContext() {
		return this;
	}
	
	private void retrieveTownsCbo(){
		 //Recupero las ciudades
        try {
        	configurationPresenter.retrieveTownsCbo();
		} catch (UxorException e) {
			e.printStackTrace();
		}
        
               
        CharSequence[] entries = new CharSequence[this.vcTownsCbo.size()];
        CharSequence[] entryValues = new CharSequence[this.vcTownsCbo.size()];
        
       
        for (int i = 0; i < this.vcTownsCbo.size(); i++) {
        	entries[i] = ((Town)this.vcTownsCbo.get(i)).getDescription().toString();
        	entryValues[i] = ((Town)this.vcTownsCbo.get(i)).getId().toString();
		}
        
        final ListPreference listPreference = (ListPreference) findPreference("userTownId");
        
        listPreference.setEntries(entries);
        listPreference.setDefaultValue("1");
        listPreference.setEntryValues(entryValues);
        
	}
}
    
	
