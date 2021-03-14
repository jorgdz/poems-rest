package com.jorgediaz.hbsb.services.exceptions;

import com.jorgediaz.hbsb.utils.response.JSONErrorResponse;

public interface HandlerService {
	
	/**
	 * 
	 * @param exception
	 * @param path
	 * @return a new error in JSON response
	 */
	JSONErrorResponse error (Exception exception, String path);	
}
