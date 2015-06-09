package com.uxor.turnos.domain;

import java.math.BigDecimal;

public class ClientType {

	private BigDecimal id;
	private String description;
	
	public ClientType(){
		
	}
	
	public ClientType(BigDecimal id, String description){
		setId(id);
		setDescription(description);
	}
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
