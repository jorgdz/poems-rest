package com.jorgediaz.hbsb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jorgediaz.hbsb.services.roles.RoleService;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class RoleController {
	
	private RoleService serviceRole;
	
	public RoleController(RoleService service) {
		this.serviceRole = service;
	}
	
	/**
	 * 
	 * @return all roles
	 */
	@GetMapping(value = "/roles", produces = "application/json")
	public ResponseEntity<?> findAll () {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(this.serviceRole.findAll());
	}
	
	/**
	 * 
	 * @param id
	 * @return one role by id
	 */
	@GetMapping(value = "/roles/{id}", produces = "application/json")
	public ResponseEntity<?> find (@PathVariable Long id) {
		return ResponseEntity
			.status(HttpStatus.FOUND)
			.body(this.serviceRole.findById(id));
	}
}
