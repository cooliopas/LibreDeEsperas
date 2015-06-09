package com.uxor.turnos.intent;

import java.math.BigDecimal;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.uxor.turnos.R;
import com.uxor.turnos.broadcast.GCMBroadcastReceiver;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.TurnosModel;
import com.uxor.turnos.util.Turnos;
import com.uxor.turnos.view.MainActivity;


public class GCMIntentService extends IntentService 
{
	private static final int NOTIF_ALERTA_ID = 1;
	private static final String TAG = "GCMIntentService";
	private static Activity activity;

	public GCMIntentService() {
        super("GCMIntentService");
        
    }
	
	public static void setActivity(Activity activity) {
		GCMIntentService.activity=activity;
	}
	
	public static Activity getActivity() {
		return GCMIntentService.activity;
	}
	
	@Override
    protected void onHandleIntent(Intent intent) 
	{
		// Waking up mobile if it is sleeping
//	      Turnos.acquireWakeLock(this);
        
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        
        String messageType = gcm.getMessageType(intent);
        Bundle extras = intent.getExtras();
        
        //Obtengo la SharePreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        
        //Verifico el estado del flag de actualizaciones de turnos
        Boolean appIsActive = prefs.getBoolean("active", Boolean.FALSE);

        if (!extras.isEmpty()){  
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)){
            	            	
            	final Intent aIntent = new Intent("unique_name");

            	if (extras.getString("typeNotification")!=null)
            	{
                	SharedPreferences.Editor editor = prefs.edit();

                	//Actualizacion de turnos
            		if (extras.getString("typeNotification").equals(Turnos.NOTIFICACION_TYPE_TTN.toString())) 
            		{
                    	mostrarNotificationTTN(extras);
                    	
		            	Log.e("GCMIntentService", "ServerTurnId = "+extras.getString("serverTurnId"));
                		updateServiceStatus(Long.valueOf((extras.getString("serverTurnId"))).longValue(),Boolean.TRUE);
                    	
            			if (!appIsActive || !((MainActivity)GCMIntentService.getActivity()).isInFront()){
        					Log.e("GCMIntentService", "Dont Update");
        					editor.putBoolean(Turnos.PROPERTY_MODE_UPDATE_TURNS_NOTIFICATIONS, Boolean.TRUE);
        				}
        				else
        				{
            				Log.e("GCMIntentService", "Actualizacion de turnos");

            				//put whatever data you want to send, if any
	    	            	aIntent.putExtra("serverTurnId", extras.getString("serverTurnId"));
	    	            	aIntent.putExtra("waitingTime", extras.getString("waitingTime"));
	    	            	aIntent.putExtra("turnsBefore", extras.getString("turnsBefore"));
	    	            	aIntent.putExtra("turnStatus", extras.getString("turnStatus"));
	    	            	aIntent.putExtra("typeNotification", extras.getString("typeNotification"));
            				
        					GCMIntentService.getActivity().runOnUiThread ( new Runnable()
        			        {
        			            public void run()
        			            {
        			            	((MainActivity)GCMIntentService.getActivity()).updateTurnsNotifications(aIntent); //VER!
        			            }
        			        } );
        					
	    	                
        				}
            			
            		}
            		else
            		{
            			//Actualizacion de Servicios (Alertas)
            			if (extras.getString("typeNotification").equals(Turnos.NOTIFICACION_TYPE_STN.toString())) 
            			{
    		            	mostrarNotificationSTN(extras);
                        	
            				if (!appIsActive || !((MainActivity)GCMIntentService.getActivity()).isInFront()){
            					Log.e("GCMIntentService", "Dont Update");
            					editor.putBoolean(Turnos.PROPERTY_MODE_UPDATE_SERVER_NOTIFICATIONS, Boolean.TRUE);
            				}
            				else
            				{
                				Log.e("GCMIntentService", "Actualizacion de notificaciones por servicio");

            					GCMIntentService.getActivity().runOnUiThread ( new Runnable()
            			        {
            			            public void run()
            			            {
            			            	((MainActivity)GCMIntentService.getActivity()).updateServiceNews();
            			            }
            			        } );
            				}
    		                
            			}
            			
            		}
           		  	editor.commit();

            	}
            	
//            	aIntent.putExtra("flagUpdateTurns", extras.getString("flagUpdateTurns"));    	            	

                //send broadcast
               // this.sendBroadcast(aIntent);
            
            }
        }
        
		
        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }
	
	private void mostrarNotificationTTN(Bundle extras) 
	{
		NotificationManager mNotificationManager =    
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		int requestCode=0;
		
		String msg = extras.getString("msg"); 
		String msg2 = extras.getString("msg2"); 
		String msg3 = extras.getString("msg3"); 
		String msg4 = extras.getString("msg4");
		
	 	
		String serverTurnId = extras.getString("serverTurnId");
		String waitingTime = extras.getString("waitingTime");
		String turnsBefore = extras.getString("turnsBefore");
		String turnStatus = extras.getString("turnStatus");
		String typeNotification = extras.getString("typeNotification");
		
		
		/* Add Big View Specific Configuration */
	      NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
	      inboxStyle.addLine(msg);
	      inboxStyle.addLine(msg2);
	      inboxStyle.addLine(msg3);
//	      inboxStyle.addLine("otra línea");
//	      inboxStyle.addLine("otra línea");
		
		NotificationCompat.Builder mBuilder = 
			new NotificationCompat.Builder(this)  
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Actualización de Turnos!!!")  
				.setContentText(msg)
				.setContentInfo(msg2)
				.setTicker("Nueva actualización de turnos!!!")
				.setAutoCancel(true)
				.setSubText(msg4)
				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		
		mBuilder.setStyle(inboxStyle);
		
		Intent notIntent =  new Intent(this, MainActivity.class); 
		notIntent.putExtra("flagUpdateTurns", "1");
		notIntent.putExtra("serverTurnId", serverTurnId);
		notIntent.putExtra("waitingTime", waitingTime);
		notIntent.putExtra("turnsBefore", turnsBefore);
		notIntent.putExtra("turnStatus", turnStatus);
		notIntent.putExtra("typeNotification", typeNotification);
		notIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		
//		PendingIntent contIntent = PendingIntent.getActivity(     
//				this, 0, notIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT); 
		
		PendingIntent contIntent = PendingIntent.getActivity(     
				this, requestCode++, notIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT); 
			
		mBuilder.setContentIntent(contIntent);
		
		mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
    }
	
	private void mostrarNotificationSTN(Bundle extras) 
	{
		NotificationManager mNotificationManager =    
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		int requestCode=0;
		
		String msg = extras.getString("msg"); 
		String msg2 = extras.getString("msg2"); 
		String msg3 = extras.getString("msg3"); 
		String msg4 = extras.getString("msg4");
		
	 	
//		String serverTurnId = extras.getString("serverTurnId");
		String typeNotification = extras.getString("typeNotification");
		
		
		/* Add Big View Specific Configuration */
	      NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
	      inboxStyle.addLine(msg);
	      inboxStyle.addLine(msg2);
	      inboxStyle.addLine(msg3);
//	      inboxStyle.addLine("otra línea");
//	      inboxStyle.addLine("otra línea");
		
		NotificationCompat.Builder mBuilder = 
			new NotificationCompat.Builder(this)  
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Actualización de Servicios!!!")  
				.setContentText(msg)
				.setContentInfo(msg2)
				.setTicker("Nueva actualización de servicios!!!")
				.setAutoCancel(true)
				.setSubText(msg4)
				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		
		mBuilder.setStyle(inboxStyle);
		
		Intent notIntent =  new Intent(this, MainActivity.class); 
//		notIntent.putExtra("flagUpdateTurns", "1");
//		notIntent.putExtra("serverTurnId", serverTurnId);
//		notIntent.putExtra("typeNotification", typeNotification);
		notIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		
//		PendingIntent contIntent = PendingIntent.getActivity(     
//				this, 0, notIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT); 
		
		PendingIntent contIntent = PendingIntent.getActivity(     
				this, requestCode++, notIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT); 
		
		mBuilder.setContentIntent(contIntent);
		
		mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
    }
	
	public void updateServiceStatus(Long serverTurnId, Boolean newStatus)
	{
		TurnosModel turnosModel = new TurnosModel();
		
		try {
			BigDecimal serviceId = turnosModel.getServiceIdByServerTurnId(serverTurnId);
			
			/*
			 * Si el serviceId es nulo significa que el turno existe en el servidor pero en la base de datos del celular no existe mas
			 * Esto puede ser porque el usuario desinstalo el programa o borro los datos desde el administrador de programas de android. 
			 */
			if (serviceId!=null){
				turnosModel.updateServiceStatus(serviceId, newStatus);
			}
		} catch (UxorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
