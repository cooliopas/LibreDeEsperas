package com.uxor.turnos.util;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.uxor.turnos.domain.Menu;
 
public class MenuLeftXmlHandler extends DefaultHandler {
 
	boolean currentElement = false;
	String currentValue = "";
 
	List<Menu> listMenu = new ArrayList<Menu>();
	Menu menu;
 
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Menu> getListMenu() {
		return listMenu;
	}

	public void setListMenu(List<Menu> listMenu) {
		this.listMenu = listMenu;
	}

	public void startElement(String uri, String localName, String qName,
		Attributes attributes) throws SAXException {
	 
		currentElement = true;
	 
		if (localName.equals("Menu")){
			listMenu = new ArrayList<Menu>();
		}else{
			if (localName.equals("Item")) {
				menu = new Menu();
			}
		}
   
	}
	 
	
 
 public void endElement(String uri, String localName, String qName)
 throws SAXException {
 
	  currentElement = false;
	 
	  if (localName.equalsIgnoreCase("Id"))
	   menu.setId(Integer.parseInt(currentValue.trim()));
	  else if (localName.equalsIgnoreCase("Description"))
	   menu.setDescripicion(currentValue.trim());
	  else if (localName.equalsIgnoreCase("Logo"))
		  menu.setLogo(currentValue.trim());
	  else if (localName.equalsIgnoreCase("Style"))
		  menu.setStyle(currentValue.trim());
	  if (localName.equalsIgnoreCase("Item"))
	   listMenu.add(menu);
	  
	  currentValue = "";
 }
 
 public void characters(char[] ch, int start, int length)
 throws SAXException {
 
  if (currentElement) {
   currentValue = currentValue + new String(ch, start, length);
  }
 
 }
 
}
