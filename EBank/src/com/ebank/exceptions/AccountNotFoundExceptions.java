package com.ebank.exceptions;

public class AccountNotFoundExceptions extends  Exception{

	String s1;
	public AccountNotFoundExceptions(String s2) {
		s1 = s2;
	} 
	@Override
	public String toString() { 
		return ("AccountNotFoundExceptions : "+s1);
	}
}
