package com.cts.membermicroservice.exception;

public class MemberNotFoundException extends Exception {

	private static final long serialVersionUID = -470685062285707614L;

	public MemberNotFoundException(String message) {
		super(message);
	}

}
