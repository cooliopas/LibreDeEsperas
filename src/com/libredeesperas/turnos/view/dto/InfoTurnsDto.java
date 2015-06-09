package com.uxor.turnos.view.dto;

import java.math.BigDecimal;

public class InfoTurnsDto {
	
	private BigDecimal serverTurnId;
	private BigDecimal waitingTime;
	private BigDecimal turnsBefore;
	
	
	public BigDecimal getServerTurnId() {
		return serverTurnId;
	}
	public void setServerTurnId(BigDecimal serverTurnId) {
		this.serverTurnId = serverTurnId;
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
	
	

}
