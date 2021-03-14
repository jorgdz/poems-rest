package com.jorgediaz.hbsb.exception.custom;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7692897659268310944L;
	
	public NotFoundException (String str) {
		super(str);
	}
}
