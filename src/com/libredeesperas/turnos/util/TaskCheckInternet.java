package com.uxor.turnos.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.os.AsyncTask;

public class TaskCheckInternet extends AsyncTask<String, Integer, Boolean>
{
    @Override
    protected Boolean doInBackground(String... params)
    {
//        InetAddress addr = null;
//        Boolean result =null;
//        try{
//            //addr = InetAddress.getByName(params[0]);
//            
//        	result = InetAddress.getByName("www.google.com").isReachable(5);
//            
//        }catch (UnknownHostException e){
//            e.printStackTrace();
//	    } catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return result;
        
        
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
