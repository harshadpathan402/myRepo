package com.ebank;

import java.math.BigDecimal;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import com.ebank.exceptions.InvalidAccountExceptions;

public interface AccountService {
	
	public void addAccount(Account account) throws AccountNotFoundException, InvalidAccountExceptions;
	public void displayAccount(String accountNumber) throws AccountNotFoundException, InvalidAccountExceptions;
	public void displayAllAccount();
	public void removeAnAccount(String accountNumber) throws AccountNotFoundException, InvalidAccountExceptions;
	public void transfer(String senderAcc,String receiverAcc,BigDecimal ammount) throws AccountNotFoundException, InvalidAccountExceptions;
	public void withdraw(String accountNumber,BigDecimal ammount) throws AccountNotFoundException, InvalidAccountExceptions;
	public void deposit(String accountNumber,BigDecimal ammount) throws AccountNotFoundException, InvalidAccountExceptions;
	public Account searchAccountUsingName(String name);
	public Account searchAccountUsingEmail(String email);
	public Account searchAccountUsingPhone(Integer phone);
	
}
