package com.ebank;

import java.math.BigDecimal;
import java.util.Scanner;

import javax.security.auth.login.AccountNotFoundException;

import com.ebank.exceptions.InvalidAccountExceptions;

public class Tester {
	static Scanner userInput = new Scanner(System.in);
	static Integer choice;
	
	public static Account populateAccount(Account account){ 
			System.out.println("Enter account number");
			account.setAccountNumber(userInput.next());
			System.out.println("Enter account holder name ");
			account.setName(userInput.next());
			System.out.println("Enter account holder email ");
			account.setEmail(userInput.next());
			System.out.println("Enter account holder phone ");
			account.setPhoneNumber(userInput.nextLong());
			account.setBalance(BigDecimal.ZERO);
		return account;
	}
	
	public static int menuList()
	{
		//display our menu
		System.out.println("*** E Bank v1.0***");
		System.out.println("1. Add Account");
		System.out.println("2. Display an Account");
		System.out.println("3. Display All Accounts");
		System.out.println("4. Remove an Account");
		System.out.println("5. withdraw");
		System.out.println("6. deposit");
		System.out.println("7. transfer");
		System.out.println("8. search account using name");
		System.out.println("9. search account using email");
		System.out.println("10. search account using phone");
		System.out.println("11. Exit");
		System.out.println("**********************");
		System.out.println("Please enter your choice:");

		//get user input
		choice = userInput.nextInt();
		return choice;
	}
	
	public static void main(String[] args) throws AccountNotFoundException, InvalidAccountExceptions {
		AccountService accountService = new AccountServiceImpl();
		//declare a scanner for user Input
		
		Account account2 = new Account("1112223334", "harshad","harshad.pathan@jbilling.com" , (long) 77986257, new BigDecimal("1000"));
		Account account3 = new Account("3332221114", "Dipak","abc@zyz" , (long) 986257, new BigDecimal("1000"));
		AccountServiceImpl.eBank.put(account2.getAccountNumber(), account2);
		AccountServiceImpl.eBank.put(account3.getAccountNumber(), account3);
		
		
		while ((choice = Tester.menuList( ))  != 0){
			
		//switch the choice from user
		switch(choice){

		case 1 :Account account = Account.getAccountInstance();
				accountService.addAccount(populateAccount(account));
		break;
		case 2 :System.out.println("Enter account number");
			try {
				accountService.displayAccount(userInput.next());
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidAccountExceptions e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception exception){
				exception.printStackTrace();
			}
		break;
		case 3 :accountService.displayAllAccount();
		break;
		case 4 :System.out.println("Enter account number");
				accountService.removeAnAccount(userInput.next());
		break;
		case 5 :System.out.println("Enter account number and ammout");
				accountService.withdraw(userInput.next(), userInput.nextBigDecimal());
		break;
		case 6 :System.out.println("Enter account number and ammout");
				accountService.deposit(userInput.next(), userInput.nextBigDecimal());
		break;
		case 7 :System.out.println("Enter sender's account number ");
				String senderAcc= userInput.next();
				System.out.println("Enter receivers's account number ");
				String receiverAcc = userInput.next();
				System.out.println("Enter Ammount ");
				BigDecimal amount = userInput.nextBigDecimal();
				accountService.transfer(senderAcc, receiverAcc, amount);
		break;
		case 8 :System.out.println("Enter account holder's name ");
				System.out.println(accountService.searchAccountUsingName(userInput.next()));
		break;
		case 9 :System.out.println("Enter account holder's email ");
				System.out.println(accountService.searchAccountUsingEmail(userInput.next()));
		break;
		case 10:System.out.println("Enter account holder's phone ");
				System.out.println(accountService.searchAccountUsingPhone(userInput.nextInt()));
		break;
		case 11: System.exit(0);
		}
		}		
	}

}
