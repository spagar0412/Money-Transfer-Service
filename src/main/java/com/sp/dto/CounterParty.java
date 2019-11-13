package com.sp.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Entity - can be other Bank or any Entity required for external Transaction
 */
public class CounterParty 
{
	public CounterParty(String id) 
	{
		this.id = UUID.randomUUID().toString();
		
	}
	private final String id;
	private String type; // "Businnes/federal" 
	private Name name;
	private String phone;
	private String email;
	private LocalDateTime created;
	private LocalDateTime lastupdated;
	private  List<Account> accountList ;
	private String currency;
	private Address address;
	private String countryCode;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public LocalDateTime getLastupdated() {
		return lastupdated;
	}
	public void setLastupdated(LocalDateTime lastupdated) {
		this.lastupdated = lastupdated;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getId() {
		return id;
	}
	public List<Account> getAccountList() {
		return accountList;
	}
	public void setAccountList(List<Account> accountList) {
		this.accountList = accountList;
	}
}
