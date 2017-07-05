package com.ebank.validations;

public class Validations {

	static public boolean isValidateAccountNumber(String accountNumber)  {
		
		if(accountNumber.length() < 10)
			return false;
		return true;
	}
}
