package com.cts.membermicroservice.exception;

/**
 * A exception raised when premium is not found
 * @author SuhruthY
 */
public class PremiumNotFoundException extends Exception {

	private static final long serialVersionUID = 2390445424544528341L;
	
	/**
	 * A constructor which takes message(string) input
	 * @param message - string to be displayed
	 */
	public PremiumNotFoundException(String message) {
		super(message);
	}

}
