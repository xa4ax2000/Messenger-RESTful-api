package org.tutorial.javabrains.messenger.model;

public class ErrorMessage {

	/*--------------------------Private Class Variables--------------------------*/

	private String errorMessage;
	private int errorCode;
	private String documentation;
	
	/*------------------------------Constructor(s)-------------------------------*/

	public ErrorMessage(){
		
	}
	
	public ErrorMessage(String errorMessage, int errorCode, String documentation){
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.documentation = documentation;
	}

	/*------------------------------Getters/Setters------------------------------*/
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	
}
