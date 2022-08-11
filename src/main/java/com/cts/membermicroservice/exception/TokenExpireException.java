package com.cts.membermicroservice.exception;

public class TokenExpireException extends Exception {

	private static final long serialVersionUID = -1417583094555470814L;

	public TokenExpireException(String msg) {
		super(msg);
	}

}
