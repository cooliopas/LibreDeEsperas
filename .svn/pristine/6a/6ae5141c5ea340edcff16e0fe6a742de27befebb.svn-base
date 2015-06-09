package com.uxor.turnos.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class ServerNew implements KvmSerializable,Serializable{
	
	private BigDecimal serverNewId;
	private BigDecimal serverTurnId;
	private Timestamp recivedDate;
	private String type;
	private String message;
	
	public ServerNew(){
	}
	
	public ServerNew(BigDecimal serverNewId, BigDecimal serverTurnId, 
	Timestamp recivedDate, String type, String message){
		this.serverNewId=serverNewId;
		this.serverTurnId=serverTurnId;
		this.recivedDate=recivedDate;
		this.type=type;
		this.message=message;
	}

	public BigDecimal getServerNewId() {
		return serverNewId;
	}

	public void setServerNewId(BigDecimal serverNewId) {
		this.serverNewId = serverNewId;
	}

	public BigDecimal getServerTurnId() {
		return serverTurnId;
	}

	public void setServerTurnId(BigDecimal serverTurnId) {
		this.serverTurnId = serverTurnId;
	}

	public Timestamp getRecivedDate() {
		return recivedDate;
	}

	public void setRecivedDate(Timestamp recivedDate) {
		this.recivedDate = recivedDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	

	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
        case 0:
            return serverNewId;
        case 1:
            return serverTurnId;
        case 2:
            return recivedDate;
        case 3:
        	return type;
        case 4:
        	return message;
		}
		
 
    return null;
	}

	@Override
	public int getPropertyCount() {
		return 6;
	}

	@Override
	public void getPropertyInfo(int ind, Hashtable ht, PropertyInfo info) {
		switch(ind){
        case 0:
            info.type = BigDecimal.class.getClass();
            info.name = "serverNewId";
            break;
        case 1:
            info.type = BigDecimal.class.getClass();
            info.name = "serverTurnId";
            break;
        case 2:
            info.type = Timestamp.class.getClass();
            info.name = "recivedDate";
            break;
        case 3:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "type";
            break;
        case 4:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "message";
            break;
        default:break;
        }
		
	}

	@Override
	public void setProperty(int ind, Object val) {
		 switch(ind){
	        case 0:
	        	serverNewId = (BigDecimal)val;
	            break;
	        case 1:
	        	serverTurnId = (BigDecimal)val;
	            break;
	        case 2:
	        	recivedDate = (Timestamp)val;
	            break;
	        case 3:
	        	type = (String)val;
	        case 4:
	        	message = (String)val;
	        default:
	        	System.err.println ("FATAL: Unknown type : param"); 
	            break;
	        }
	}
	
	@Override
	public String toString() {
		return "ServerNew [serverNewId=" + serverNewId + ", serverTurnId=" + serverTurnId + "message=" + message + "]";
	}
	

}
