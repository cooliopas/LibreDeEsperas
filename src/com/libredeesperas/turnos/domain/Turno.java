package com.uxor.turnos.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Turno implements KvmSerializable,Serializable{
	
	private BigDecimal turnId;
	private BigDecimal serverTurnId;
	private BigDecimal clientServiceId;
	private String letter;
	private Timestamp turnDate;
	private Timestamp turnHour;
	private String turnNumber;
	private String status;
	private BigDecimal waitingTime;
	private BigDecimal turnsBefore;
	private String gcmId;
	private String exceptionMessage;
	
	public Turno(){
	}
	
	
	
	public Turno(BigDecimal turnId, BigDecimal serverTurnId, BigDecimal clientServiceId, String letter, 
			Timestamp turnDate, Timestamp turnHour, String turnNumber, String status, BigDecimal waitinTime, 
			BigDecimal turnsBefore, String exceptionMessage){
		
		setTurnId(turnId);
		setServerTurnId(serverTurnId);
		setClientServiceId(clientServiceId);
		setLetter(letter);
		setTurnDate(turnDate);
		setTurnHour(turnHour);
		setTurnNumber(turnNumber);
		setStatus(status);
		setWaitingTime(waitinTime);
		setTurnsBefore(turnsBefore);
		setExceptionMessage(exceptionMessage);
	}



	public BigDecimal getTurnId() {
		return turnId;
	}



	public void setTurnId(BigDecimal turnId) {
		this.turnId = turnId;
	}



	public BigDecimal getServerTurnId() {
		return serverTurnId;
	}



	public void setServerTurnId(BigDecimal serverTurnId) {
		this.serverTurnId = serverTurnId;
	}



	public BigDecimal getClientServiceId() {
		return clientServiceId;
	}



	public void setClientServiceId(BigDecimal clientServiceId) {
		this.clientServiceId = clientServiceId;
	}



	public String getLetter() {
		return letter;
	}



	public void setLetter(String letter) {
		this.letter = letter;
	}



	public Timestamp getTurnDate() {
		return turnDate;
	}



	public void setTurnDate(Timestamp turnDate) {
		this.turnDate = turnDate;
	}



	public Timestamp getTurnHour() {
		return turnHour;
	}



	public void setTurnHour(Timestamp turnHour) {
		this.turnHour = turnHour;
	}


	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public BigDecimal getWaitingTime() {
		return waitingTime;
	}



	public void setWaitingTime(BigDecimal waitingTime) {
		this.waitingTime = waitingTime;
	}

	public BigDecimal getTurnsBefore() {
		return turnsBefore;
	}

	public void setTurnsBefore(BigDecimal turnsBefore) {
		this.turnsBefore = turnsBefore;
	}

	
	public String getGcmId() {
		return gcmId;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}
	
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	

	public String getTurnNumber() {
		return turnNumber;
	}


	public void setTurnNumber(String turnNumber) {
		this.turnNumber = turnNumber;
	}



	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
        case 0:
            return turnId;
        case 1:
            return serverTurnId;
        case 2:
            return clientServiceId;
        case 3:
        	return letter;
        case 4:
        	return turnDate;
        case 5:
        	return turnHour;
        case 6:
        	return turnNumber;
        case 7:
        	return status;
        case 8:
        	return waitingTime;
        case 9:
        	return turnsBefore;
        case 10:
        	return gcmId;
		}
		
 
    return null;
	}

	@Override
	public int getPropertyCount() {
		return 10;
	}

	@Override
	public void getPropertyInfo(int ind, Hashtable ht, PropertyInfo info) {
		switch(ind){
        case 0:
            info.type = BigDecimal.class.getClass();
            info.name = "turnId";
            break;
        case 1:
            info.type = BigDecimal.class.getClass();
            info.name = "serverTurnId";
            break;
        case 2:
            info.type = BigDecimal.class.getClass();
            info.name = "clientServiceId";
            break;
        case 3:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "letter";
            break;
        case 4:
            info.type = Timestamp.class.getClass();
            info.name = "turnDate";
            break;
        case 5:
            info.type = Timestamp.class.getClass();
            info.name = "turnHour";
            break;
        case 6:
            info.type = BigDecimal.class.getClass();
            info.name = "turnNumber";
            break;
        case 7:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "status";
            break;
        case 8:
            info.type = BigDecimal.class.getClass();
            info.name = "waitingTime";
            break;
        case 9:
            info.type = BigDecimal.class.getClass();
            info.name = "turnsBefore";
            break;
        case 10:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "gcmId";
            break;
   
        default:break;
        }
		
	}

	@Override
	public void setProperty(int ind, Object val) {
		 switch(ind){
	        case 0:
	        	turnId = (BigDecimal)val;
	            break;
	        case 1:
	        	serverTurnId = (BigDecimal)val;
	            break;
	        case 2:
	        	clientServiceId = (BigDecimal)val;
	            break;
	        case 3:
	        	letter = (String)val;
	        case 4:
	        	turnDate = (Timestamp)val;
	        case 5:
	        	turnHour = (Timestamp)val;
	        case 6:
	        	turnNumber = (String)val;
	        case 7:
	        	status = val.toString();
	        case 8:
	        	waitingTime = (BigDecimal)val;
	        case 9:
	        	turnsBefore = (BigDecimal)val;
	        case 10:
	        	gcmId = (String)val;
	        default:
	        	System.err.println ("FATAL: Unknown type : param"); 
	            break;
	        }
		
	}
	
	@Override
	public String toString() {
		return "Turn [turnId=" + turnId + ", serverTurnId=" + serverTurnId + "turnNumber=" + letter + turnNumber.toString() + "]";
	}
	

}
