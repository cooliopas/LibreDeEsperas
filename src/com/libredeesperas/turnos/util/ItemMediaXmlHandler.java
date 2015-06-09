package com.uxor.turnos.util;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.uxor.turnos.domain.ItemMedia;
 
public class ItemMediaXmlHandler extends DefaultHandler {
 
	boolean currentElement = false;
	String currentValue = "";
 
	List<ItemMedia> listMedia = new ArrayList<ItemMedia>();
	ItemMedia itemMedia;
 
	public ItemMedia getItemMedia() {
		return itemMedia;
	}

	public void setItemMedia(ItemMedia itemMedia) {
		this.itemMedia = itemMedia;
	}

	public List<ItemMedia> getListMedia() {
		return listMedia;
	}

	public void setListMedia(List<ItemMedia> listMedia) {
		this.listMedia = listMedia;
	}

	public void startElement(String uri, String localName, String qName,
		Attributes attributes) throws SAXException {
	 
		currentElement = true;
	 
		if (localName.equals("List")){
			listMedia = new ArrayList<ItemMedia>();
		}else{
			if (localName.equals("Item")) {
				itemMedia = new ItemMedia();
			}
		}
   
	}
	 
	
 
 public void endElement(String uri, String localName, String qName)
 throws SAXException {
 
	  currentElement = false;
	 
	  if (localName.equalsIgnoreCase("Id"))
		  itemMedia.setId(Integer.parseInt(currentValue.trim()));
	  else if (localName.equalsIgnoreCase("Description"))
		  itemMedia.setDescripcion(currentValue.trim());
	  else if (localName.equalsIgnoreCase("Logo"))
		  itemMedia.setLogo(currentValue.trim());
	  else if (localName.equalsIgnoreCase("Uri1"))
		  itemMedia.setUri1(currentValue.trim());
	  else if (localName.equalsIgnoreCase("Uri2"))
		  itemMedia.setUri2(currentValue.trim());
	  if (localName.equalsIgnoreCase("Item"))
		  listMedia.add(itemMedia);
	  
	  currentValue = "";
 }
 
 public void characters(char[] ch, int start, int length)
 throws SAXException {
 
  if (currentElement) {
   currentValue = currentValue + new String(ch, start, length);
  }
 
 }
 
}
