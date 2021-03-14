package com.jorgediaz.hbsb.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.jorgediaz.hbsb.exception.custom.BadRequestException;
import com.jorgediaz.hbsb.exception.custom.ConflictException;
import com.jorgediaz.hbsb.exception.custom.ForbiddenException;
import com.jorgediaz.hbsb.exception.custom.NoContentException;
import com.jorgediaz.hbsb.exception.custom.NotFoundException;
import com.jorgediaz.hbsb.services.exceptions.HandlerService;
import com.jorgediaz.hbsb.utils.response.JSONErrorResponse;

@ControllerAdvice
public class HandlerException {
	
	@Autowired
	private HandlerService serviceHandler;
	
	
	/**
	 * 
	 * @param request
	 * @param exception
	 * @return Error in JSON not content
	 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler({ NoContentException.class })
	@ResponseBody
	public JSONErrorResponse noContent (HttpServletRequest request, Exception exception) {
		return new JSONErrorResponse(exception, request.getRequestURI(), HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 
	 * @param request
	 * @param exception
	 * @return Error in JSON not found resource
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
    	NoHandlerFoundException.class,
    	NotFoundException.class
    })
	@ResponseBody
    public JSONErrorResponse notFoundException(HttpServletRequest request, Exception exception) {
		return this.serviceHandler.error(exception, request.getRequestURI());
	}
	
	/**
	 * 
	 * @param request
	 * @param exception
	 * @return Error in JSON bad request exception
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({
    	BadRequestException.class,
    	org.springframework.dao.DuplicateKeyException.class,
    	org.springframework.web.HttpRequestMethodNotSupportedException.class,
		org.springframework.web.bind.MissingRequestHeaderException.class,
		org.springframework.web.bind.MissingServletRequestParameterException.class,
		org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class,
		org.springframework.http.converter.HttpMessageNotReadableException.class,
		org.springframework.web.bind.MethodArgumentNotValidException.class
    })
	@ResponseBody
	public JSONErrorResponse badRequestException (HttpServletRequest request, Exception exception) {
		return this.serviceHandler.error(exception, request.getRequestURI());
	}
	
	/**
	 * 
	 * @param request
	 * @param exception
	 * @return error conflict in JSON 
	 */
	@ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
    	ConflictException.class
    })
	@ResponseBody
    public JSONErrorResponse conflictException(HttpServletRequest request, Exception exception) {
    	return this.serviceHandler.error(exception, request.getRequestURI()) ;
    }
	
	/**
	 * 
	 * @param request
	 * @param exception
	 * @return error forbidden Exception in JSON
	 */
	@ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({
    	ForbiddenException.class
    })
	@ResponseBody
    public JSONErrorResponse forbiddenException (HttpServletRequest request, Exception exception) {
    	return new JSONErrorResponse(exception, request.getRequestURI(), HttpStatus.FORBIDDEN.value());
    }
	
	/**
	 * 
	 * @param request
	 * @param exception
	 * @return Any unexpected error in JSON
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
	@ResponseBody
    public JSONErrorResponse unexpectedException(HttpServletRequest request, Exception exception) {
    	return new JSONErrorResponse(exception, request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
