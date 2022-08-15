package com.cts.membermicroservice.exception;

/**
 * A exception raised when policy is not found
 * @author SuhruthY
 */
public class PolicyNotFoundException extends Exception {

	private static final long serialVersionUID = -3763272497234133662L;

	/**
	 * A constructor which takes message(string) input
	 * @param message - string to be displayed
	 */
	public PolicyNotFoundException(String message) {
		super(message);
	}

}
