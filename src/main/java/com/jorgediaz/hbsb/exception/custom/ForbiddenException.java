package com.jorgediaz.hbsb.exception.custom;

public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = -5698251357494637246L;
	
	public ForbiddenException(String message) {
		super(message);
	}
}
