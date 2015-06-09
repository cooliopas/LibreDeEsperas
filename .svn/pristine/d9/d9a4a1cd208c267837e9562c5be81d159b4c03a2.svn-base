package com.uxor.turnos.domain;

import java.math.BigDecimal;


public class Branch {
	
	private BigDecimal id;
	private String alias;
	private String address;
	private BigDecimal cityId;
	private Client client;
	private String phoneNumber;
	private String state;
	
	public Branch(){
		
	}
	
	public Branch(BigDecimal id, String alias, String address, BigDecimal cityId, String phoneNumber, String state){
		setId(id);
		setAlias(alias);
		setAddress(address);
		setCityId(cityId);
		setPhoneNumber(phoneNumber);
		setState(state);
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getClientName() {
		return this.client.getName().concat("-").concat(this.alias);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public BigDecimal getCityId() {
		return cityId;
	}

	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
