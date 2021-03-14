package com.jorgediaz.hbsb.exception.custom;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -1145617039222811907L;
	
	public BadRequestException(String msg) {
		super(msg);
	}
}
