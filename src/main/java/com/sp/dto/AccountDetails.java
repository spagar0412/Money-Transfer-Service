package com.sp.dto;

public class AccountDetails 
{
	private Name name;
	private Address address;
	private String countryCode; //	2 -letter country code used identify account location e.g (GB,US) 
	private String branchCode;/* branch code can be used to calculate fees
 							   * or maintain ledger for transactions of Similar bank but different branch.*/
	private String phone ;
	
	public Name getAccountName() {
		return name;
	}
	public void setAccountName(Name name) {
		this.name = name;
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
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
