package com.cts.membermicroservice.exception;

/**
 * A exception raised when token(jwt) is expired
 * @author SuhruthY
 */
public class TokenExpireException extends Exception {

	private static final long serialVersionUID = -1417583094555470814L;
	
	/**
	 * A constructor which takes message(string) input
	 * @param message - string to be displayed
	 */
	public TokenExpireException(String message) {
		super(message);
	}
	
}
