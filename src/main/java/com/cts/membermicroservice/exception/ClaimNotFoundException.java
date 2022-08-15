package com.cts.membermicroservice.exception;

/**
 * A exception raised when claim is not found
 * @author SuhruthY
 */
public class ClaimNotFoundException extends Exception {
	
	private static final long serialVersionUID = -437385003247640553L;
	
	/**
	 * A constructor which takes message(string) input
	 * @param message - string to be displayed
	 */
	public ClaimNotFoundException(String message) {
		super(message);
	}
	
}
