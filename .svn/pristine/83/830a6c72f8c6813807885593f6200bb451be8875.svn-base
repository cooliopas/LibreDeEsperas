package com.uxor.turnos.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class LstTurnDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7733403401729387884L;
	private String branchAlias;
	private String branchAddress;
	private String clientTypeDesc;
	private String mServiceDesc;
	private BigDecimal turnId;
	private String turnLetter;
	private Timestamp turnDate;
	private Timestamp turnHour;
	private String turnNumber;
	private BigDecimal turnBefore;
	private BigDecimal waitingTime;
	private BigDecimal serverTurnId;
	private String branchPhoneNumber;
	private String clientName;
	private String clientImageName;
	private String turnStatus;
	private Boolean serviceStatus;

	
	public LstTurnDto(String branchAlias, String branchAddress, String clientTypeDesc, String mServiceDesc,
			BigDecimal turnId, String turnLetter, Timestamp turnDate, Timestamp turnHour, 
			String turnNumber, BigDecimal turnBefore, BigDecimal waitingTime, BigDecimal serverTurnId, 
			String branchPhoneNumber, String clientName, String turnStatus, String clientImageName, Boolean serviceStatus){
		
		setBranchAlias(branchAlias);
		setBranchAddress(branchAddress);
		setClientTypeDesc(clientTypeDesc);
		setmServiceDesc(mServiceDesc);
		setTurnId(turnId);
		setTurnLetter(turnLetter);
		setTurnDate(turnDate);
		setTurnHour(turnHour);
		setTurnNumber(turnNumber);
		setTurnBefore(turnBefore);
		setWaitingTime(waitingTime);
		setServerTurnId(serverTurnId);
		setBranchPhoneNumber(branchPhoneNumber);
		setClientName(clientName);
		setClientImageName(clientImageName);
		setTurnStatus(turnStatus);
		setServiceStatus(serviceStatus);
	}


	public String getWaitingTimeHHMM() {
		
		int hours = this.getWaitingTime().intValue() / 60; //since both are ints, you get an int
   	 	int minutes = this.getWaitingTime().intValue() % 60;
   	 	String waitingTime = String.format("%02d:%02d", hours, minutes);
   	 
		return waitingTime;
	}
	
	public BigDecimal getWaitingTime() {
		return waitingTime;
	}


	public void setWaitingTime(BigDecimal waitingTime) {
		this.waitingTime = waitingTime;
	}


	public String getBranchAlias() {
		return branchAlias;
	}


	public void setBranchAlias(String branchAlias) {
		this.branchAlias = branchAlias;
	}


	public String getBranchAddress() {
		return branchAddress;
	}


	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}


	public String getClientTypeDesc() {
		return clientTypeDesc;
	}


	public void setClientTypeDesc(String clientTypeDesc) {
		this.clientTypeDesc = clientTypeDesc;
	}


	public String getmServiceDesc() {
		return mServiceDesc;
	}


	public void setmServiceDesc(String mServiceDesc) {
		this.mServiceDesc = mServiceDesc;
	}


	public BigDecimal getTurnId() {
		return turnId;
	}


	public void setTurnId(BigDecimal turnId) {
		this.turnId = turnId;
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


	public String getTurnLetter() {
		return turnLetter;
	}


	public void setTurnLetter(String turnLetter) {
		this.turnLetter = turnLetter;
	}


	public String getTurnNumber() {
		return turnNumber;
	}


	public void setTurnNumber(String turnNumber) {
		this.turnNumber = turnNumber;
	}


	public BigDecimal getTurnBefore() {
		return turnBefore;
	}


	public void setTurnBefore(BigDecimal turnBefore) {
		this.turnBefore = turnBefore;
	}


	public BigDecimal getServerTurnId() {
		return serverTurnId;
	}


	public void setServerTurnId(BigDecimal serverTurnId) {
		this.serverTurnId = serverTurnId;
	}


	public String getBranchPhoneNumber() {
		return branchPhoneNumber;
	}


	public void setBranchPhoneNumber(String branchPhoneNumber) {
		this.branchPhoneNumber = branchPhoneNumber;
	}


	public String getClientName() {
		return clientName;
	}


	public void setClientName(String clientName) {
		this.clientName = clientName;
	}


	public String getClientImageName() {
		return clientImageName;
	}


	public void setClientImageName(String clientImageName) {
		this.clientImageName = clientImageName;
	}


	public String getTurnStatus() {
		return turnStatus;
	}


	public void setTurnStatus(String turnStatus) {
		this.turnStatus = turnStatus;
	}
	
	public Boolean getServiceStatus() {
		return serviceStatus;
	}


	public void setServiceStatus(Boolean serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
}
