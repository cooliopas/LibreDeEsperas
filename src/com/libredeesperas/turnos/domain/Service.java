package com.uxor.turnos.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Service {
	private BigDecimal id;
	private BigDecimal branchId;
	private String description;
	private String aditionalInfo;
	private Boolean enabled;
	private Timestamp lastUpadate;
	private Boolean futureTurns;
	private BigDecimal confirmCancelTurn;
	private Boolean numberQty;
	private Boolean waitingTime;
	private Boolean backLog;
	private String wsNews;
	private BigDecimal estimatedAvgQueue;
	private BigDecimal initialNumber;
	private String letter;
	private BigDecimal ticketMaxNumber;
	private String state;
	
	public Service(){
	}
	
	public Service(BigDecimal id, BigDecimal branchId, String description){
		setId(id);
		setBranchId(branchId);
		setDescription(description);
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAditionalInfo() {
		return aditionalInfo;
	}

	public void setAditionalInfo(String aditionalInfo) {
		this.aditionalInfo = aditionalInfo;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Timestamp getLastUpadate() {
		return lastUpadate;
	}

	public void setLastUpadate(Timestamp lastUpadate) {
		this.lastUpadate = lastUpadate;
	}

	public BigDecimal getConfirmCancelTurn() {
		return confirmCancelTurn;
	}

	public void setConfirmCancelTurn(BigDecimal confirmCancelTurn) {
		this.confirmCancelTurn = confirmCancelTurn;
	}

	public Boolean getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(Boolean waitingTime) {
		this.waitingTime = waitingTime;
	}

	public Boolean getBackLog() {
		return backLog;
	}

	public void setBackLog(Boolean backLog) {
		this.backLog = backLog;
	}

	public String getWsNews() {
		return wsNews;
	}

	public void setWsNews(String wsNews) {
		this.wsNews = wsNews;
	}
		
	public BigDecimal getEstimatedAvgQueue() {
		return estimatedAvgQueue;
	}

	public void setEstimatedAvgQueue(BigDecimal estimatedAvgQueue) {
		this.estimatedAvgQueue = estimatedAvgQueue;
	}

	public BigDecimal getInitialNumber() {
		return initialNumber;
	}

	public void setInitialNumber(BigDecimal initialNumber) {
		this.initialNumber = initialNumber;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public BigDecimal getTicketMaxNumber() {
		return ticketMaxNumber;
	}

	public void setTicketMaxNumber(BigDecimal ticketMaxNumber) {
		this.ticketMaxNumber = ticketMaxNumber;
	}

	public Boolean getFutureTurns() {
		return futureTurns;
	}

	public void setFutureTurns(Boolean futureTurns) {
		this.futureTurns = futureTurns;
	}

	public Boolean getNumberQty() {
		return numberQty;
	}

	public void setNumberQty(Boolean numberQty) {
		this.numberQty = numberQty;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return this.description;
	}
	
	
}
