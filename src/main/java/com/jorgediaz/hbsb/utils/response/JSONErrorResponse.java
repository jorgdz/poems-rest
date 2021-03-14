package com.jorgediaz.hbsb.utils.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class JSONErrorResponse implements Serializable {

	private static final long serialVersionUID = -6409418210937938111L;
	
	@JsonIgnore
	private LocalDateTime timestamps;
	
	private String path;
	
	private int code;
	
	private String message;
	
	private List<String> details;
	
	private String exception;
	
	public JSONErrorResponse (Exception ex, String path, int code) {
		this.timestamps = LocalDateTime.now();
		this.message = ex.getMessage();
		this.exception = ex.getClass().getSimpleName();
		this.path = path;
		this.code = code;
	}
	
	public JSONErrorResponse (String message, Exception ex, String path, int code) {
		this.timestamps = LocalDateTime.now();
		this.message = message;
		this.exception = ex.getClass().getSimpleName();
		this.path = path;
		this.code = code;
	}
	
	public JSONErrorResponse (List<String> details, Exception ex, String path, int code) {
		this.timestamps = LocalDateTime.now();
		this.details = details;
		this.exception = ex.getClass().getSimpleName();
		this.path = path;
		this.code = code;
	}
	
	public JSONErrorResponse (String message, List<String> details, Exception ex, String path, int code) {
		this.timestamps = LocalDateTime.now();
		this.message = message;
		this.details = details;
		this.exception = ex.getClass().getSimpleName();
		this.path = path;
		this.code = code;
	}
}
