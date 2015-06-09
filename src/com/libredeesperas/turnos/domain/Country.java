package com.uxor.turnos.domain;

import java.math.BigDecimal;

public class Country {
	private BigDecimal id;
	private String description;
	
	public Country(){
	}
	
	public Country(BigDecimal id, String description){
		this.id = id;
		this.description = description;
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

	@Override
	public String toString() {
		return this.getId().toString()+ "-" + this.description;
	}
	
}