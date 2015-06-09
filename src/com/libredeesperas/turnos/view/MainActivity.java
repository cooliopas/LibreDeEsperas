package com.uxor.turnos.view;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.intent.GCMIntentService;
import com.uxor.turnos.presenter.MainPresenter;
import com.uxor.turnos.presenter.helper.ApplicationHelper;
import com.uxor.turnos.util.ConnectionCheckReceiver;
import com.uxor.turnos.util.CustomActionBar;
import com.uxor.turnos.util.Turnos;
import com.uxor.turnos.view.adapter.AdaptadorOpcionesMenuNuevo;

public class MainActivity extends CustomActionBar implements IMainView {
	
	private static final String LOGTAG = "Turnos-MainActivity";
	private static final int RESULT_SETTINGS = 1;
	
	private List<com.uxor.turnos.domain.Menu>  opcionesMenu;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence tituloSeccion;  
    private CharSequence tituloApp;
    private List<com.uxor.turnos.domain.Menu> listMenuLeft;
    private MainPresenter mainPresenter;
    private int pendingTurnQty;
    private int disabledTurnQty;
    private boolean isInFront;
    private boolean doubleBackToExitPressedOnce; 
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Seteo el activity al receiver que checkea el estado de la conexion
		ConnectionCheckReceiver.setActivity(this);
       
		//Seteo el activity al service de notificaciones
		GCMIntentService.setActivity(this);
				
		setContentView(R.layout.activity_main);
		
		Bundle bundle = this.getIntent().getExtras();
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		switch(metrics.densityDpi){
		     case DisplayMetrics.DENSITY_LOW:
		    	 		Log.i(LOGTAG, "Dpi:".concat(String.valueOf(metrics.densityDpi)));
		                break;
		     case DisplayMetrics.DENSITY_MEDIUM:
		    	 		Log.i(LOGTAG, "Dpi:".concat(String.valueOf(metrics.densityDpi)));
		                 break;
		     case DisplayMetrics.DENSITY_HIGH:
		    	 		Log.i(LOGTAG, "Dpi:".concat(String.valueOf(metrics.densityDpi)));
		                 break;
		     case DisplayMetrics.DENSITY_XHIGH:
		    	 Log.i(LOGTAG, "Dpi:".concat(String.valueOf(metrics.densityDpi)));
                 break;
		     case DisplayMetrics.DENSITY_XXHIGH:
		    	 Log.i(LOGTAG, "Dpi:".concat(String.valueOf(metrics.densityDpi)));
                 break;
		}
		
		//Seteo el titulo de la contextbar
        setFormatTitleActionBar(getTitle().toString());
        
		//Obtengo el presenter para la vista
		mainPresenter = new MainPresenter(this);
		
		//Obtengo la cantidad de turnos pendientes (HAB) del usuario.
	     try {
			this.pendingTurnQty = mainPresenter.getPendingTurnsQty();
	     } catch (UxorException e) {
			e.printStackTrace();
	     }
	     
	     //Obtengo la cantidad de turnos deshabilitados (DES) del usuario.
	     try {
			this.disabledTurnQty = mainPresenter.getDisabledTurnsQty();
	     } catch (UxorException e) {
			e.printStackTrace();
	     }
		
		//Seteo el tipo de usuario en la sharePreferences
//		try {
//			mainPresenter.setInitParameters();
//		} catch (UxorException e1) {
//			Log.e(LOGTAG, e1.getMessage());
//		}
		
		//Lanzo tarea async para actualizar los turnos del servidor
//		TareaWSInfoTurnos tarea = new TareaWSInfoTurnos(mainPresenter);
//		List vcTurnsIds = new ArrayList<String>();
//		vcTurnsIds.add("1");
//		vcTurnsIds.add("2");
//        tarea.execute(vcTurnsIds);
		
		 		 
	     drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	     drawerList = (ListView) findViewById(R.id.left_drawer);
	 
	     try {
			mainPresenter.loadListMenuLeft();
		} catch (UxorException e) {
			Log.e(LOGTAG, e.getMessage());
		}
	     
	     //Seteo el adaptador a la lista de opciones del menu nuevo
	     AdaptadorOpcionesMenuNuevo adaptador = new AdaptadorOpcionesMenuNuevo(this, listMenuLeft);
	     
	     drawerList.setAdapter(adaptador);
	     //drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START); //Sombra lateral de la navigation bar
	     
	     //Inicializo la grilla principal de turnos ------------------------------------------------     
	     
	     FragmentManager fragmentManager = getSupportFragmentManager();
	     fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	     if (savedInstanceState == null) {
	    	 if (this.pendingTurnQty == 0){
				 	Log.d(LOGTAG, "Usuario sin turnos (activos): Mostrar pantalla nuevo turno");
				 	this.tituloSeccion = getResources().getString(R.string.fragment_new_turn);
		            Fragment fragment = new NewTurnFragment();
	           	 	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); 
	           	 	//fragmentTransaction.addToBackStack(null);
	           	 	fragmentTransaction.add(R.id.content_frame, fragment, "newTurnFragment");
	           	 	fragmentTransaction.commit();			 
			 }else{
		    	 //Si tiene mas de un turno pendiente mostrar pantalla de "Listado de turnos"
		    	 if (this.pendingTurnQty>1){
		    		 
	                 this.tituloSeccion = getResources().getString(R.string.fragment1); //Para el Titulo de la Action Bar
				     Fragment grillaTurnosFragment = new Fragment1();
				     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); 
				     //fragmentTransaction.addToBackStack(null);
				     fragmentTransaction.add(R.id.content_frame, grillaTurnosFragment,"frag1");
				     fragmentTransaction.commit();
		    	 }else{
		    		 //Si tiene un turno pendiente mostrar pantalla "Turno único"
		    		 if (this.pendingTurnQty==1){
		    			 Log.d(LOGTAG, "Usuario registrado y con un solo turno pendiente: Mostrar la pantalla con el diseño para un turno...");
	
		                 this.tituloSeccion = getResources().getString(R.string.oneTurnFragment);
		                 Fragment oneTurnFragment = new OneTurnFragment();
					     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					     //fragmentTransaction.addToBackStack(null);
					     fragmentTransaction.add(R.id.content_frame, oneTurnFragment,"oneTurnFrag");
					     fragmentTransaction.commit();
		    			 
		    		 }else{
		    			 if (this.pendingTurnQty==0 && this.disabledTurnQty>0){
			    			//Si tiene todos los turnos vencidos se muestra las pantalla "Historial de Turnos"
			    			 Log.d(LOGTAG, "Usuario registrado sin turnos: Mostrar pantalla turnos vencidos");
			                 this.tituloSeccion = getResources().getString(R.string.inactiveTurnsFragment);
			    			 Fragment inactiveTurnsFragment = new InactiveTurnsFragment();
						     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
						     //fragmentTransaction.addToBackStack(null);
						     fragmentTransaction.add(R.id.content_frame, inactiveTurnsFragment, "InactiveTurnsFrag");
						     fragmentTransaction.commit();

		    			 }else{
		    				 	Log.d(LOGTAG, "Usuario sin turnos (activos/inactivos): Mostrar pantalla alta de turno");
		    				 	this.tituloSeccion = getResources().getString(R.string.fragment2);
		    		            Fragment fragment = new Fragment2();
		    	           	 	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		    	           	 	//fragmentTransaction.addToBackStack(null);
		    	           	 	fragmentTransaction.add(R.id.content_frame, fragment, "AddTurnFrag2");
		    	           	 	fragmentTransaction.commit();		    				 
		    			 }
		    		 }
		    	 }
			 }
	     }
	     //------------------------------------------------------------------------------------------
	    
	     //Si tiene algun turno pendiente ejecuto el modo de actualización de turnos
	     if (this.pendingTurnQty != 0){
	     
		    //Obtengo la SharePreferences
	        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
	        
	
	    	//Modo actualización de turnos
	    	//Reseteo el flag para la actualización de turnos
	    	SharedPreferences.Editor editor = prefs.edit();
	    	        	
			if (prefs.getBoolean(Turnos.PROPERTY_MODE_UPDATE_TURNS_NOTIFICATIONS, Boolean.FALSE))
			{
				Log.e(LOGTAG, "Actulizacion de turnos (Main)");
	
	        	//Recupero las actualizaciones de todos los turnos activos
				try {
					mainPresenter.getTurnsNotifications();
		        	editor.putBoolean(Turnos.PROPERTY_MODE_UPDATE_TURNS_NOTIFICATIONS, Boolean.FALSE);     
				} catch (UxorException e) {
					Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
					Toast.makeText(this, "Error al recuperar las actualizaciones de turnos!!!", Toast.LENGTH_SHORT).show();
				}
			}
			if (prefs.getBoolean(Turnos.PROPERTY_MODE_UPDATE_SERVER_NOTIFICATIONS, Boolean.FALSE))
			{
				Log.e(LOGTAG, "Actulizacion de notificaciones por servicio (Main)");
				
				//Actualizo las noticias de los turnos activos
				try {	        				
					mainPresenter.getServerNotifications();
		        	editor.putBoolean(Turnos.PROPERTY_MODE_UPDATE_SERVER_NOTIFICATIONS, Boolean.FALSE);     
				} catch (UxorException e) {
					Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
					Toast.makeText(this, "Error al recuperar las noticias del servidor!!!", Toast.LENGTH_SHORT).show();
				}
			}
	    	editor.commit();
        	
	     }
        		     
	     drawerList.setOnItemClickListener(new OnItemClickListener() { //OnClick Item del Navigation Bar 
	         @Override
	         public void onItemClick(AdapterView parent, View view,
	                 int position, long id) {
	  
	             Fragment fragment = null;
	             String fragmentName = "";
	             
	             Intent i = null; 
	             
	             FragmentManager manager = getSupportFragmentManager();
	             FragmentTransaction fragmentManager = manager.beginTransaction();
            	 //getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	             
	             
	             //manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	  
	             
	            //Obtengo la cantidad de turnos pendientes (HAB) del usuario.
	    	     try {
	    	    	 setPendingTurnQty(mainPresenter.getPendingTurnsQty());
	    	     } catch (UxorException e) {
	    			e.printStackTrace();
	    	     }
	             
	             switch (position) {
             		 case 0: //User Info
	                	 i = new Intent(getContext(), ConfigurationActivity.class);
	                	 startActivity(i);
             			 break;
	                 case 1: //Agregar Turno
	                	 fragmentName = "fragment2";
	                	 fragment = new Fragment2();
	                	 //fragmentManager.addToBackStack(fragmentName);
	                	 fragmentManager.addToBackStack(null);
	                     break;
	                 case 2: //Mis Turnos
	                 {
                	 	 if (getPendingTurnQty() == 0){
         		            fragment = new NewTurnFragment();
         		            fragmentName = "newTurnFragment";
	
                	 	 }else{
	            	    	 //Si tiene mas de un turno pendiente mostrar pantalla de "Listado de turnos"
	            	    	 if (getPendingTurnQty()>1){
	    	                	 fragment = new Fragment1();
	    	                	 fragmentName = "fragment1";
	            	    	 }else{
	            	    		 //Si tiene un turno pendiente mostrar pantalla "Turno único"
	            	    		 if (getPendingTurnQty()==1){
	            	    			 fragment = new OneTurnFragment();
	        	                	 fragmentName = "oneTurnFragment";
	
	            	    		 }else{
	            	    			 if (getPendingTurnQty()==0 && getDisabledTurnQty()>0){
	            		    			//Si tiene todos los turnos vencidos se muestra las pantalla "Historial de Turnos"
	            	                	 fragment = new InactiveTurnsFragment();
	            	                	 fragmentName = "inactiveTurnsFragment";
	            	    			 }else{
	            	    		            fragment = new Fragment2();
	            	    		            fragmentName = "fragment2";
            	    			 		}
            	    		 		}
        	    	 			}
                	 	 	}
	                	 //fragmentManager.addToBackStack(fragmentName);
	                	 //fragmentManager.addToBackStack(null);
	                 } break;
	                 case 3: //Consulta Historicos
	                	 fragmentName = "inactiveTurnsFragment";
	                	 fragment = new InactiveTurnsFragment();
	                	 //fragmentManager.addToBackStack(fragmentName);
	                	 //fragmentManager.addToBackStack(null);
	                     break;
	                 case 4: //Configuraciones
	                	 i = new Intent(getContext(), ConfigurationActivity.class);
	                	 startActivity(i);
	                     break;
	                 case 5: //Acerca de...
	                	 i = new Intent(getContext(), AboutUsActivity.class);
	                	 startActivity(i);
	                	 break;

	             }
	             
	             if (fragment!=null)
	             {
		             fragmentManager.replace(R.id.content_frame, fragment, fragmentName);
		             fragmentManager.commit();
	            	 
	             }  
	             
	             drawerLayout.closeDrawer(drawerList);
	         }
	     });
	     
	     
		tituloApp = getTitle();
			
		drawerToggle = new ActionBarDrawerToggle(this, 
				drawerLayout,
				R.drawable.ic_navigation_drawer, 
				R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) { //Cuando se cierra la navigation bar
				//getSupportActionBar().setTitle(tituloSeccion);
				setFormatTitleActionBar(tituloSeccion.toString());
				supportInvalidateOptionsMenu();
				//ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}

			public void onDrawerOpened(View drawerView) { //Cuando se abre la navigation bar
				//getSupportActionBar().setTitle(tituloApp);
				setFormatTitleActionBar(tituloApp.toString());
				supportInvalidateOptionsMenu();
				//ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}
		};

		drawerLayout.setDrawerListener(drawerToggle);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		
		if (this.tituloSeccion!=null)
		{
			setFormatTitleActionBar(tituloSeccion.toString());
		}
		else
		{
			setFormatTitleActionBar(tituloApp.toString());
		}
	}
	
	public int getPendingTurnQty()
	{
		return this.pendingTurnQty;
	}
	
	public void setPendingTurnQty(int turnQty)
	{
		this.pendingTurnQty = turnQty;
	}
	
	public int getDisabledTurnQty()
	{
		return this.disabledTurnQty;
	}
	

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		MenuItem itemLoadData = menu.findItem(R.id.action_loadData);
		itemLoadData.setVisible(false);
		
		MenuItem itemRegGCM = menu.findItem(R.id.action_regGCM);
		itemRegGCM.setVisible(false);
		
		MenuItem btnAdd = menu.findItem(R.id.action_add);
		btnAdd.setVisible(false);
		
		MenuItem btnAccept = menu.findItem(R.id.action_accept);
		btnAccept.setVisible(false);
		
		MenuItem btnHelp = menu.findItem(R.id.action_help);
		btnHelp.setVisible(false);
		
		MenuItem itemUpdateConfData = menu.findItem(R.id.action_updateConfigurationData);
		itemUpdateConfData.setVisible(false);
		
		//Si no tiene turnos activos se oculta el botón "Actualizar Turnos"
		if (this.pendingTurnQty==0){
			MenuItem btnRefresh = menu.findItem(R.id.action_refresh);
			btnRefresh.setVisible(false);
		}
		
		if (isOnline())
		{
			MenuItem btnAlertConnection = menu.findItem(R.id.alert_no_connection);
			btnAlertConnection.setVisible(false);
		}
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentManager = manager.beginTransaction();
		
	    switch(item.getItemId())
	    {
	    	case R.id.action_loadData:
		    	//Inserto datos temporales;	
		    	try {
					mainPresenter.insertTempData();
					Toast.makeText(this, "Datos temporales insertados...", Toast.LENGTH_SHORT).show();
				} catch (UxorException e) {
					Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
					Toast.makeText(this, "Error al insertar los datos temporales!!!", Toast.LENGTH_SHORT).show();
				}	
	            break;
	            
	        case R.id.action_settings:
	            //Toast.makeText(this, "Configuración", Toast.LENGTH_SHORT).show();
	            Intent i = new Intent(this, ConfigurationActivity.class);
	            startActivity(i);
	            break;
	            
	        case R.id.action_regGCM:
	        	//Creamos el Intent
	        	//Toast.makeText(this, "Registrar GCM", Toast.LENGTH_SHORT).show();
	             Intent intent = new Intent(MainActivity.this, RegistrarGCMActivity.class);
	             startActivity(intent);
	            break;
	        case R.id.action_add:
	            //Toast.makeText(this, "Agregar", Toast.LENGTH_SHORT).show();
	            String fragmentName = "AddTurnFrag2";
	            
	            Fragment2 myFragment = (Fragment2)getSupportFragmentManager().findFragmentByTag("AddTurnFrag2");
	            if (myFragment==null || !myFragment.isVisible()) {
	            	//getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		            Fragment fragment = new Fragment2();
	           	 	//fragmentManager.addToBackStack(fragmentName);
	           	 	//fragmentManager.addToBackStack(null);
	           	 	fragmentManager.replace(R.id.content_frame, fragment, fragmentName);
	           	 	fragmentManager.commit();
	           	 	setFormatTitleActionBar("Agregar Turno");
	            }
	            
           	 	break;
           	 	
	        case R.id.action_help:
	        	//Toast.makeText(this, "Ayuda", Toast.LENGTH_SHORT).show();
	            break;
	            
	        case R.id.alert_no_connection:
	            Toast.makeText(this,  getResources().getString(R.string.alertNoConnectionTitle), Toast.LENGTH_SHORT).show();
	            ApplicationHelper helper = new ApplicationHelper();
	            helper.showAlertDialog(getContext(),getResources().getString(R.string.alertNoConnectionTitle),getResources().getString(R.string.alertNoConnectionMessage),false);
           	 	break;
           	 	
	        case R.id.action_refresh:
		    	//Actualiza los turnos;	
				try {
					mainPresenter.getTurnsNotifications();
				} catch (UxorException e) {
					Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
					Toast.makeText(this, "Error al recuperar las actualizaciones de turnos!!!", Toast.LENGTH_SHORT).show();
				}	
	            break;
	            
//	        case R.id.action_updateConfigurationData:
//
//	        	 //Toast.makeText(this, "Actualizar Datos", Toast.LENGTH_SHORT).show();
//	        	 try{
//						mainPresenter.updateConfigurationData();
//						//Toast.makeText(this, "La Configuración de Datos fue actualizada correctamente!!!", Toast.LENGTH_SHORT).show();
//					}catch (UxorException e){
//						Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
//						Toast.makeText(this, "Error al actualizar los datos de configuración!!!", Toast.LENGTH_SHORT).show();
//					}	
//	            break;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 
	    return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		boolean menuAbierto = drawerLayout.isDrawerOpen(drawerList);
		
//		if(menuAbierto)
//			menu.findItem(R.id.action_add).setVisible(false);
//		else
//			menu.findItem(R.id.action_add).setVisible(true);
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
		
		// Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    	Log.i(LOGTAG, "ORIENTATION LANDSCAPE");
	        //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	    	Log.i(LOGTAG, "ORIENTATION PORTRAIT");
	        //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
	    }
	}

	

	@Override
	public void setLstMenuLeft(List<com.uxor.turnos.domain.Menu> listMenuLeft)
			throws UxorException {
		this.listMenuLeft = listMenuLeft;
		
	}

	@Override
	public Context getContext() {
		return this;
	}
	
	public void refreshView(){
		
	     try {
			this.pendingTurnQty = mainPresenter.getPendingTurnsQty();
		} catch (UxorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        Fragment fragment = null;
        String fragmentName = "";
                 
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentManager = manager.beginTransaction();
        
        if (getPendingTurnQty() == 0){
           fragment = new NewTurnFragment();
           fragmentName = "newTurnFragment";

	 	 }else{
	    	 //Si tiene mas de un turno pendiente mostrar pantalla de "Listado de turnos"
	    	 if (getPendingTurnQty()>1){
	           	 fragment = new Fragment1();
	           	 fragmentName = "fragment1";
	    	 }else{
	    		 //Si tiene un turno pendiente mostrar pantalla "Turno único"
	    		 if (getPendingTurnQty()==1){
	    			 fragment = new OneTurnFragment();
	    			 fragmentName = "oneTurnFragment";
	    		 }else{
		    			 if (getPendingTurnQty()==0 && getDisabledTurnQty()>0){
			    			//Si tiene todos los turnos vencidos se muestra las pantalla "Historial de Turnos"
		                	 fragment = new InactiveTurnsFragment();
		                	 fragmentName = "inactiveTurnsFragment";
		    			 }else{
		    		            fragment = new Fragment2();
		    		            fragmentName = "fragment2";
	   			 		 }
   		 			}
	 			}
	 	 	}
        
//        Fragment newFragment = manager.findFragmentByTag(fragmentName);
//        if(newFragment == null)
//        	fragmentManager.addToBackStack(fragmentName);
//        else
//        	fragmentManager.addToBackStack(null);
        
        
        //manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //fragmentManager.addToBackStack(null);

        if (fragment!=null){
        	fragmentManager.replace(R.id.content_frame, fragment, fragmentName);
        	fragmentManager.commit(); 
        }  
		
		//Inicializo la grilla principal de turnos --------------------------------------------------------------------	     
//	     FragmentManager fragmentManager = getSupportFragmentManager();
//	     fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
//    	 //Si tiene mas de un turno pendiente mostrar pantalla de "Listado de turnos"
//    	 if (this.pendingTurnQty>1){
//		     Fragment1 f1 = (Fragment1)getSupportFragmentManager().findFragmentByTag("frag1");
//		     f1.updateListView();
//    	 }else{
//    		 //Si tiene un turno pendiente mostrar pantalla "Turno único"
//    		 if (this.pendingTurnQty==1){
//    			 Log.d(LOGTAG, "Usuario registrado y con un solo turno pendiente: Mostrar la pantalla con el diseño para un turno...");
//			     OneTurnFragment oneTurnfrag = (OneTurnFragment)getSupportFragmentManager().findFragmentByTag("oneTurnFrag");
//			     oneTurnfrag.updateView();
//    		 }
//    		 else{
//    			 if (this.pendingTurnQty==0 && this.disabledTurnQty>0){
//	    			//Si tiene todos los turnos vencidos se muestra las pantalla "Historial de Turnos"
//	    			 Log.d(LOGTAG, "Usuario registrado sin turnos: Mostrar pantalla turnos vencidos");
//	    			 Fragment inactiveTurnsFragment = new InactiveTurnsFragment();
//				     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();  
//				     fragmentTransaction.add(R.id.content_frame, inactiveTurnsFragment, "InactiveTurnsFrag");
//				     fragmentTransaction.commit();
//    			 }
//    			 else{
//    				 	Log.d(LOGTAG, "Usuario sin turnos (activos/inactivos): Mostrar pantalla alta de turno");
//    		            Fragment fragment = new Fragment2();
//    	           	 	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();  
//    	           	 	fragmentTransaction.add(R.id.content_frame, fragment, "AddTurnFrag2");
//    	           	 	fragmentTransaction.commit();
//    	           	 	//setFormatTitleActionBar("Agregar Turno");
//    			 }
//    		 }
//    	 }
//	    
//	     
	}
	
	//This is the handler that will manager to process the broadcast intent
//	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//	    @Override
//	    public void onReceive(Context context, Intent intent) {
//	    	
//	    	 //Obtengo la SharePreferences
//	         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
//	    	 Boolean appIsActive = prefs.getBoolean("active", Boolean.FALSE);
//	    	 
//	    	 if (!appIsActive){
//	    	
//		    	//Obtengo el presenter para la vista
//				mainPresenter = new MainPresenter(MainActivity.this);
//				
//		    	//Recupero la cantidad de turnos activos
//	    		int qtyActiveTurns=0;
//				try {
//					qtyActiveTurns = mainPresenter.getActiveTurnsQty();
//				} catch (UxorException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//	    		
//	    		//Si solo tiene un turno activo utilizo la info enviada dentro del mensaje de notificación
//	    		if (qtyActiveTurns==1){
//	
//					Bundle bundle = intent.getExtras();
//					
//					// Extract data included in the Intent
//					//String message = intent.getStringExtra("serverTurnId");
//					
//					BigDecimal serverTurnId = new BigDecimal(bundle.getString("serverTurnId"));
//					BigDecimal waitingTime = new BigDecimal(bundle.getString("waitingTime"));
//					BigDecimal turnsBefore = new BigDecimal(bundle.getString("turnsBefore"));
//					String turnStatus = bundle.getString("turnStatus");
//					
//					//Actualizo la info del turno
//					try {
//						mainPresenter.updateTurnNotification(serverTurnId, waitingTime, turnsBefore, turnStatus);
//					} catch (UxorException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//					//Obtengo la SharePreferences
//					SharedPreferences.Editor editor = prefs.edit();
//				    
//					//Seteo el flag
//					editor.putBoolean(Turnos.PROPERTY_MODE_UPDATE_TURNS_NOTIFICATIONS, Boolean.FALSE);     
//					editor.commit();
//	    		}
//	    	 }
//			
//	    }
//	};
		
	@Override
	public void onResume() {
 	    super.onResume(); 	
 	    
        isInFront = true;
//
//        //Actualizo la Action Bar
        supportInvalidateOptionsMenu();
//        
		//ActivityCompat.invalidateOptionsMenu(this);   
//		
//		//Seteo el activity al receiver que checkea el estado de la conexion
		ConnectionCheckReceiver.setActivity(this);
//		
//		
		if (this.pendingTurnQty>=1) {
			/*------------------ Veo si hay actualizaciones ---------------------------*/
	        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
			SharedPreferences.Editor editor = prefs.edit();
	    	
			if (prefs.getBoolean(Turnos.PROPERTY_MODE_UPDATE_TURNS_NOTIFICATIONS, Boolean.FALSE))
			{
				Log.e(LOGTAG, "Actulizacion de turnos (Main - onResume)");
	
	        	//Recupero las actualizaciones de todos los turnos activos
				try {
					mainPresenter.getTurnsNotifications();
		        	editor.putBoolean(Turnos.PROPERTY_MODE_UPDATE_TURNS_NOTIFICATIONS, Boolean.FALSE);     
				} catch (UxorException e) {
					Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
					Toast.makeText(this, "Error al recuperar las actualizaciones de turnos!!!", Toast.LENGTH_SHORT).show();
				}
			}
			if (prefs.getBoolean(Turnos.PROPERTY_MODE_UPDATE_SERVER_NOTIFICATIONS, Boolean.FALSE))
			{
				Log.e(LOGTAG, "Actulizacion de notificaciones por servicio (Main - onResume)");
				
				//Actualizo las noticias de los turnos activos
				try {	        				
					mainPresenter.getServerNotifications();
		        	editor.putBoolean(Turnos.PROPERTY_MODE_UPDATE_SERVER_NOTIFICATIONS, Boolean.FALSE);     
				} catch (UxorException e) {
					Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
					Toast.makeText(this, "Error al recuperar las noticias del servidor!!!", Toast.LENGTH_SHORT).show();
				}
			}
			
	    	editor.commit();
    	
		}

	}

	//Must unregister onPause()
	@Override
	protected void onPause() {
	    super.onPause();
        isInFront = false;
//	    getContext().unregisterReceiver(mMessageReceiver);
	}
	
	@Override
    protected void onStart() { //Inicia la App
        super.onStart();
         
        // Store our shared preference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("active", Boolean.TRUE);
        editor.commit();
        
        if (!isOnline())
        {
        	ApplicationHelper helper = new ApplicationHelper();
	        helper.showAlertDialog(this, this.getResources().getString(R.string.alertNoConnectionTitle), this.getResources().getString(R.string.alertNoConnectionMessage),false);
        }
        
   }
     
    @Override
    protected void onStop() {
        super.onStop();
         
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("active", Boolean.FALSE);
        editor.commit();
         
    }


	@Override
    protected void onNewIntent(Intent intent){ //Si la app esta activa y si hace click en la notificacion
        super.onNewIntent(intent);
//
//        
//      //Obtengo el presenter para la vista
//		mainPresenter = new MainPresenter(this);
//		Log.e(LOGTAG, "onNewIntent");
//
//    	//Recuperamos la información pasada en el intent
//        Bundle bundle = intent.getExtras();
//        if (bundle!=null && bundle.getString("flagUpdateTurns").equals("1")){
//        	try {
//		        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//				SharedPreferences.Editor editor = prefs.edit();
//
//	    		if (prefs.getBoolean(Turnos.PROPERTY_MODE_UPDATE_TURNS_NOTIFICATIONS, Boolean.FALSE))
//        		{
//        			Log.i(LOGTAG, "Actualizacion de Turnos (onNewIntent)");
//
//	        		//Recupero la cantidad de turnos activos
//	        		int qtyActiveTurns = mainPresenter.getActiveTurnsQty();
//	        		
//	        		//Si solo tiene un turno activo utilizo la info enviada dentro del mensaje de notificación
//	        		if (qtyActiveTurns==1){
//	        			Log.i(LOGTAG, "Tiene solo un turno activo");
//		        		BigDecimal serverTurnId = new BigDecimal(bundle.getString("serverTurnId"));
//		        		BigDecimal waitingTime = new BigDecimal(bundle.getString("waitingTime"));
//		        		BigDecimal turnsBefore = new BigDecimal(bundle.getString("turnsBefore"));
//		        		String turnStatus = bundle.getString("turnStatus");
//		        		
//		        		//Actualizo la info del turno
//						mainPresenter.updateTurnNotification(serverTurnId, waitingTime, turnsBefore, turnStatus);
//        				editor.putBoolean(Turnos.PROPERTY_MODE_UPDATE_TURNS_NOTIFICATIONS, Boolean.FALSE);     
//
//						//Refresco la info de la pantalla
//						OneTurnFragment oneTurnfrag = (OneTurnFragment)getSupportFragmentManager().findFragmentByTag("oneTurnFrag");
//					    oneTurnfrag.updateView();
//						
//	        		}else{
//	        			Log.i(LOGTAG, "Tiene mas de un turno activo");
//	        			
//	        		  //Recupero las actualizaciones de todos los turnos activos
//	        			try {
//	        				//Obtengo el presenter para la vista
//	        				//mainPresenter = new MainPresenter(this);
//	        				
//	        				mainPresenter.getTurnsNotifications();
//	        				editor.putBoolean(Turnos.PROPERTY_MODE_UPDATE_TURNS_NOTIFICATIONS, Boolean.FALSE);     
//
//	        			} catch (UxorException e) {
//	        				Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
//	        				Toast.makeText(this, "Error al recuperar las actualizaciones de turnos!!!", Toast.LENGTH_SHORT).show();
//	        			}
//	        		}
//        		
//        		}
//
//	    		
//	    		if (prefs.getBoolean(Turnos.PROPERTY_MODE_UPDATE_SERVER_NOTIFICATIONS, Boolean.FALSE))
//    			{
//    				Log.e(LOGTAG, "Actualizacion de notificaciones por servicio (onNewIntent)");
//
//    				//Actualizo las alertas de los turnos activos
//        			try {	        				
//        				mainPresenter.getServerNotifications();
//        				editor.putBoolean(Turnos.PROPERTY_MODE_UPDATE_SERVER_NOTIFICATIONS, Boolean.FALSE);     
//
//        			} catch (UxorException e) {
//        				Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
//        				Toast.makeText(this, "Error al recuperar las noticias del servidor!!!", Toast.LENGTH_SHORT).show();
//        			}
//    			}
//        						
//				//Seteo el flag
//				editor.commit();
//        		
//			} catch (UxorException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
        
    }

	public void setTituloSeccion(String title) {
		this.tituloSeccion=title;
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}

	@Override
	public void startView(Intent mainActivity) {
		startActivity(mainActivity);
	}
	
    public void updateServiceNews(){ //Si la app esta activa, se ejecuta el WS automaticamente
    	
      //Obtengo el presenter para la vista
		mainPresenter = new MainPresenter(this);
	
		Log.e(LOGTAG, "Actualizacion de notificaciones por servicio (updateServiceNews)");

		//Actualizo las alertas de los turnos activos
		try {	        			
			
			//Recupero la cantidad de turnos activos
			int qtyActiveTurns = mainPresenter.getActiveTurnsQty();
			
			//Si tengo turnos activos recupero el estado de los servicios
			if (qtyActiveTurns>=1){
				mainPresenter.getServerNotifications();
			}

		} catch (UxorException e) {
			Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
			Toast.makeText(this, "Error al recuperar las noticias del servidor!!!", Toast.LENGTH_SHORT).show();
		}        
    }
    
    public void updateTurnsNotifications(Intent intent){ //Si la app esta activa, se ejecuta el WS automaticamente
    	
        //Obtengo el presenter para la vista
  		mainPresenter = new MainPresenter(this);

  		Log.e(LOGTAG, "Actualizacion de turnos (updateTurnsNotifications)");
  		
		try {
	        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			SharedPreferences.Editor editor = prefs.edit();

			//Recupero la cantidad de turnos activos
			int qtyActiveTurns = mainPresenter.getActiveTurnsQty();
			

			//Si solo tiene un turno activo utilizo la info enviada dentro del mensaje de notificación
			if (qtyActiveTurns>0){
				if (qtyActiveTurns==1){
					Bundle bundle = intent.getExtras();
	
					Log.i(LOGTAG, "Tiene solo un turno activo");
		    		BigDecimal serverTurnId = new BigDecimal(bundle.getString("serverTurnId"));
		    		BigDecimal waitingTime = new BigDecimal(bundle.getString("waitingTime"));
		    		BigDecimal turnsBefore = new BigDecimal(bundle.getString("turnsBefore"));
		    		String turnStatus = bundle.getString("turnStatus");
		    		
		    		//Actualizo la info del turno
					mainPresenter.updateTurnNotification(serverTurnId, waitingTime, turnsBefore, turnStatus);
		
					//Refresco la info de la pantalla
					refreshView();				
				}else{
					Log.i(LOGTAG, "Tiene mas de un turno activo");
					
				  //Recupero las actualizaciones de todos los turnos activos
					try {
						mainPresenter.getTurnsNotifications();
		
					} catch (UxorException e) {
						Log.e(LOGTAG, e.getMessage().concat("-").concat(e.getCause().getMessage()));
						Toast.makeText(this, "Error al recuperar las actualizaciones de turnos!!!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		
		} catch (UxorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public Boolean isInFront()
    {
    	return isInFront;
    }
	
    @Override
    protected void onDestroy() {
    	//android.os.Process.killProcess(android.os.Process.myPid());
    	super.onDestroy();
    }
    
    
    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		setFormatTitleActionBar(getTitle().toString());
		//super.onBackPressed();
		
		//finish();
		
		if (doubleBackToExitPressedOnce) {
//	        super.onBackPressed();
//	        return;
	        
	        //finish();
	        //System.exit(0);
	        
	        this.moveTaskToBack(true);
	        return;
	    }

	    this.doubleBackToExitPressedOnce = true;
	    Toast.makeText(this, "Por favor, pulse de nuevo para salir", Toast.LENGTH_SHORT).show();

	    new Handler().postDelayed(new Runnable() {

	        @Override
	        public void run() {
	            doubleBackToExitPressedOnce=false;                       
	        }
	    }, 2000);
	}
    
}


