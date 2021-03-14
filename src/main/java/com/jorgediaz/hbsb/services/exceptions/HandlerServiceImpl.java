package com.jorgediaz.hbsb.services.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.jorgediaz.hbsb.exception.custom.BadRequestException;
import com.jorgediaz.hbsb.exception.custom.ConflictException;
import com.jorgediaz.hbsb.exception.custom.NotFoundException;
import com.jorgediaz.hbsb.utils.response.JSONErrorResponse;

@Service
public class HandlerServiceImpl implements HandlerService {

	@Override
	public JSONErrorResponse error(Exception exception, String path) {
		JSONErrorResponse err = someException(exception, path);
		
		if(exception instanceof NoHandlerFoundException) err = notfoundException((NoHandlerFoundException) exception, path);
		if(exception instanceof NotFoundException) err = notfoundException((NotFoundException) exception, path);
		if(exception instanceof BadRequestException) err = badException((BadRequestException) exception, path);
		if(exception instanceof MethodArgumentNotValidException) err = badException((MethodArgumentNotValidException) exception, path);
		if(exception instanceof MethodArgumentTypeMismatchException) err = badException((MethodArgumentTypeMismatchException) exception, path);
		if(exception instanceof MissingServletRequestParameterException) err = badException((MissingServletRequestParameterException) exception, path);
		if(exception instanceof MissingRequestHeaderException) err = badException((MissingRequestHeaderException) exception, path);
		if(exception instanceof HttpMessageNotReadableException) err = badException((HttpMessageNotReadableException) exception, path);
		if(exception instanceof HttpRequestMethodNotSupportedException) err = badException((HttpRequestMethodNotSupportedException) exception, path);
		if(exception instanceof ConflictException) err = conflictException((ConflictException) exception, path);
		if(exception instanceof ArithmeticException) err = conflictException((ArithmeticException) exception, path);
	
		return err;
	}

	private JSONErrorResponse badException(HttpMessageNotReadableException exception, String path) {
		return new JSONErrorResponse(exception.getMostSpecificCause().getLocalizedMessage(), 
			exception, path, HttpStatus.BAD_REQUEST.value());
	}

	private JSONErrorResponse badException(HttpRequestMethodNotSupportedException exception, String path) {
		String message = "No se admite peticiones de tipo " + exception.getMethod();
		List<String> details = Arrays.asList(exception.getSupportedMethods());
		return new JSONErrorResponse(message, details, exception, path, HttpStatus.BAD_REQUEST.value());
	}

	private JSONErrorResponse badException(MissingRequestHeaderException exception, String path) {
		return new JSONErrorResponse("El valor de la cabecera " + exception.getHeaderName() + " no es válido.", 
			exception, path, HttpStatus.BAD_REQUEST.value());
	}

	private JSONErrorResponse badException(MissingServletRequestParameterException exception, String path) {
		String message = "Se esperaba un parametro con el nombre " + exception.getParameterName()+ " de tipo "+ exception.getParameterType();
		return new JSONErrorResponse(message, exception, path, HttpStatus.BAD_REQUEST.value());
	}

	private JSONErrorResponse badException(MethodArgumentTypeMismatchException exception, String path) {
		return new JSONErrorResponse("No se puede convertir el valor " +  exception.getValue() 
    			+ " a un tipo de dato " + exception.getRequiredType().getSimpleName(), 
    			exception, path, HttpStatus.BAD_REQUEST.value());
	}

	private JSONErrorResponse badException(MethodArgumentNotValidException exception, String path) {
		BindingResult bindingResult = exception.getBindingResult();
		
		String message = "Ha ocurrido un error al validar los campos de "+ bindingResult.getObjectName();
		List<FieldError> errors = bindingResult.getFieldErrors();
		List<String> listErrors = new ArrayList<String>();
		
		errors.forEach(e -> listErrors.add(e.getDefaultMessage()));
		return new JSONErrorResponse(message, listErrors, exception, path, HttpStatus.BAD_REQUEST.value());
	}

	private JSONErrorResponse badException(BadRequestException exception, String path) {
		return new JSONErrorResponse(exception.getMessage(), exception, path, HttpStatus.BAD_REQUEST.value());
	}

	private JSONErrorResponse someException(Exception exception, String path) {
		return new JSONErrorResponse(exception, path, HttpStatus.BAD_REQUEST.value());
	}

	private JSONErrorResponse notfoundException(NoHandlerFoundException exception, String path) {
		return new JSONErrorResponse("No se ha encontrado la ruta solicitada.", exception, path, HttpStatus.NOT_FOUND.value());
	}

	private JSONErrorResponse notfoundException(NotFoundException exception, String path) {
		return new JSONErrorResponse(exception, path, HttpStatus.NOT_FOUND.value());
	}

	private JSONErrorResponse conflictException(ConflictException exception, String path) {
		return new JSONErrorResponse(exception, path, HttpStatus.CONFLICT.value());
	}

	private JSONErrorResponse conflictException(ArithmeticException exception, String path) {
		return new JSONErrorResponse("Error al realizar la operación matemática", exception, path, HttpStatus.CONFLICT.value());
	}

}
