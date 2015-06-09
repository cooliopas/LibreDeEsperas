package com.uxor.turnos.presenter;
 
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.res.AssetManager;

import com.uxor.turnos.domain.ItemMedia;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.util.ItemMediaXmlHandler;
import com.uxor.turnos.util.Turnos;
import com.uxor.turnos.view.IAboutUsView;

public class AboutUsPresenter {
		 
	private IAboutUsView aboutUsView;

	public AboutUsPresenter(IAboutUsView view){
		this.aboutUsView = view;
		
	}
	
		
	public void loadMediaList() throws UxorException{		
		this.aboutUsView.setSocialMediaList(parseItemMediaXML());
	}
	
	
	private List<ItemMedia> parseItemMediaXML() {
        AssetManager assetManager = Turnos.getAppContext().getAssets();
        List<ItemMedia> listMedia = null;
        String text = "";
        String textXml = "";
        try {
        	String[] files = assetManager.list("");
            InputStream is = assetManager.open("aboutUsList.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
 
            ItemMediaXmlHandler myXMLHandler = new ItemMediaXmlHandler();
            xr.setContentHandler(myXMLHandler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);
    
            //Obtengo el menu izquierdo
            listMedia = myXMLHandler.getListMedia();
		  
            //Cierro el inputstrem
            is.close();
            
            
            //---------------------------------------------
            InputStream input = assetManager.open("prueba.txt");
            
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            
         // byte buffer into a string
            text = new String(buffer);
            
            InputStream inputA = assetManager.open("aboutUsList.xml");
            int sizeA = inputA.available();
            byte[] bufferA = new byte[sizeA];
            inputA.read(bufferA);
            inputA.close();
 
            // byte buffer into a string
            textXml = new String(bufferA);
            
            //-----------------------------------------------
           
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        
        return listMedia;
    }

}
