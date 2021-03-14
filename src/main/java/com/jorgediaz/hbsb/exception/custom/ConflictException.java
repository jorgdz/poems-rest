package com.jorgediaz.hbsb.exception.custom;

public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = 2570184329434662528L;
	
	public ConflictException(String msg) {
		super(msg);
	}
}
