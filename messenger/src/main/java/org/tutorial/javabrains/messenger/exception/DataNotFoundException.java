package org.tutorial.javabrains.messenger.exception;

public class DataNotFoundException extends RuntimeException{
	
	/*--------------------------Class Serial ID Generated------------------------*/

	private static final long serialVersionUID = 4776948951981410652L;
	
	/*------------------------------Constructor(s)-------------------------------*/
	
	public DataNotFoundException(String message){
		super(message);
	}
}
