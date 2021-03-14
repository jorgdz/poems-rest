package com.jorgediaz.hbsb.exception.custom;

public class NoContentException extends RuntimeException {

	private static final long serialVersionUID = -5744272146901177331L;
	
	public NoContentException(String str) {
		super(str);
	}
}
