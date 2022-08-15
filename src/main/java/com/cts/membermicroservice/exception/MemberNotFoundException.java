package com.cts.membermicroservice.exception;

/**
 * A exception raised when member is not found
 * @author SuhruthY
 */
public class MemberNotFoundException extends Exception {

	private static final long serialVersionUID = -470685062285707614L;
	
	/**
	 * A constructor which takes message(string) input
	 * @param message - string to be displayed
	 */
	public MemberNotFoundException(String message) {
		super(message);
	}

}
