package com.ebank;

import java.math.BigDecimal;

import com.ebank.exceptions.InvalidAccountExceptions;

public class Account {
	
	private String  accountNumber;
	private String name;
	private String email;
	private Long  phoneNumber;
	private BigDecimal balance;
	
	public Account() {
		this(null,null,null,null,null);
	}
	
	public Account(String accountNumber, String name, String email, Long phoneNumber, BigDecimal balance) {
		
		this.accountNumber = accountNumber;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.balance = balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public static Account getAccountInstance(){
		return new Account();
	}
	
	@Override
	public String toString() {
		return String.format("Account [accountNumber=%s, name=%s, email=%s, phoneNumber=%s, balance=%s]", accountNumber,
				name, email, phoneNumber, balance);
	}
	
}
