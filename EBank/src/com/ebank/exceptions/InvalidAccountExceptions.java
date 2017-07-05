package com.ebank.exceptions;

public class InvalidAccountExceptions extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703603213944350060L;
	String s1;
	public InvalidAccountExceptions(String s2) {
		s1 = s2;
	} 
	@Override
	public String toString() { 
		return ("InvalidAccountExceptions : "+s1);
	}
}
