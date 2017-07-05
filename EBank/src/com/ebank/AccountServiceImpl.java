package com.ebank;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.AccountNotFoundException;

import org.omg.PortableServer.ThreadPolicyOperations;

import com.ebank.exceptions.InvalidAccountExceptions;
import com.ebank.validations.Validations;

public class AccountServiceImpl implements AccountService{

	public static Map<String,Account> eBank = new HashMap<>();
	
	@Override
	public void addAccount(Account account) throws InvalidAccountExceptions {
		
		if (null != account) {
			if (!Validations.isValidateAccountNumber(account.getAccountNumber())) 
				throw new InvalidAccountExceptions("Invalid accont");
			System.out.println(account);
			eBank.put(account.getAccountNumber(), account);
		}
	}

	@Override
	public void displayAccount(String accountNumber) throws AccountNotFoundException, InvalidAccountExceptions {

		validateAccount(accountNumber);
		System.out.println(eBank.get(accountNumber));	
	}

	@Override
	public void displayAllAccount() {
		
		eBank.entrySet().forEach(x-> System.out.println(x));
		
	}

	@Override
	public void removeAnAccount(String accountNumber) throws AccountNotFoundException, InvalidAccountExceptions {
		validateAccount(accountNumber);
		eBank.remove(accountNumber);
		
	}

	@Override
	public void withdraw(String accountNumber, BigDecimal ammount) throws AccountNotFoundException, InvalidAccountExceptions {
		validateAccount(accountNumber);
		Account account = eBank.get(accountNumber);
		account.setBalance(account.getBalance().subtract(ammount));
		eBank.put(account.getAccountNumber(),account);
		
	}

	@Override
	public void deposit(String accountNumber, BigDecimal ammount) throws AccountNotFoundException, InvalidAccountExceptions {
		validateAccount(accountNumber);
		Account account = eBank.get(accountNumber);
		account.setBalance(account.getBalance().add(ammount));
		eBank.put(account.getAccountNumber(),account);
		
	}

	@Override
	public Account searchAccountUsingName(String name) {
		return eBank.entrySet().stream().filter(account
				-> name.equals(account.getValue().getName())).findAny().get().getValue();
	}

	@Override
	public Account searchAccountUsingEmail(String email) {
		
		return eBank.entrySet().stream().filter(account
				-> email.equals(account.getValue().getEmail())).findAny().get().getValue();
	}

	@Override
	public Account searchAccountUsingPhone(Integer phone) {
		
		return eBank.entrySet().stream().filter(account
				-> phone.equals(account.getValue().getPhoneNumber())).findAny().get().getValue();
	}

	@Override
	public void transfer(String senderAcc, String receiverAcc, BigDecimal ammount) throws AccountNotFoundException, InvalidAccountExceptions {
		withdraw(senderAcc, ammount);
		deposit(receiverAcc, ammount);
		
	}

	public boolean isAccountExists(String accountNumber){
		return eBank.containsKey(accountNumber);
	}
	
	private void validateAccount(String accountNumber) throws InvalidAccountExceptions, AccountNotFoundException{
		if (!Validations.isValidateAccountNumber(accountNumber)) 
			throw new InvalidAccountExceptions("Invalid accont");
		if(!isAccountExists(accountNumber))
				throw new AccountNotFoundException("Account number do not exists");
	}
}
