package com.uxor.turnos.domain;

import java.math.BigDecimal;

public class State {
	private BigDecimal id;
	private String description;
	private BigDecimal countryId;
	
	public State(){
	}
	
	public State(BigDecimal id, String description, BigDecimal countryId){
		this.id = id;
		this.description = description;
		this.countryId = countryId;
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
	
	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	@Override
	public String toString() {
		return this.getId().toString()+ "-" + this.description;
	}
	
}