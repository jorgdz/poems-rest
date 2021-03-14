package com.jorgediaz.hbsb.controller;
import java.io.Serializable;

import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;
import com.jorgediaz.hbsb.entity.User;
import com.jorgediaz.hbsb.services.users.UserService;

@Controller
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class UserController {
	
	private UserService serviceUser;
	
	public UserController(UserService service) {
		this.serviceUser = service;
	}
	
	
	/**
	 * Endpoint GET /users
	 * @return all users
	 */
	@GetMapping(value = "/users", headers="Accept=application/json")
	public ResponseEntity<?> findAll () {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(this.serviceUser.findAll());
	}
	
	
	/**
	 * Endpoint GET /users/:id
	 * @param id
	 * @return user by id
	 */
	@GetMapping(value = "/users/{id}", headers="Accept=application/json")
	public ResponseEntity<User> find (@PathVariable Long id) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(this.serviceUser.findById(id));
	}
	
	
	/**
	 * Endpoint POST /users
	 * @param user
	 * @param uriComponentBuilder
	 * @return a new user created
	 */
	@PostMapping(value = "/users", headers="Accept=application/json")
	public ResponseEntity<?> save (@Valid @RequestBody User user, UriComponentsBuilder uriComponentBuilder) {
		Serializable _user = this.serviceUser.save(user);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(uriComponentBuilder.path("/api/users/{id}").buildAndExpand(_user).toUri());
		return new ResponseEntity<String>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
	}
	
	
	/**
	 * Endpoint PUT /users/:id
	 * @param id
	 * @param user
	 * @param uriComponentBuilder
	 * @return user updated
	 */
	@PutMapping(value = "/users/{id}", headers="Accept=application/json")
	public ResponseEntity<?> update (@PathVariable Long id, @RequestBody User user, UriComponentsBuilder uriComponentBuilder) {
		user.setId(id);
		this.serviceUser.update(user);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(uriComponentBuilder.path("/api/users/{id}").buildAndExpand(id).toUri());
		return new ResponseEntity<String>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
	}
	
	
	/**
	 * Endpoint DELETE /users/:id
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/users/{id}", headers = "Accept=application/json")
	public ResponseEntity<?> destroy (@PathVariable Long id) {
		this.serviceUser.deleteById(id);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body("{\"message\":\"Usuario eliminado.\",\"id\":"+id+"}");
	}
	
}
