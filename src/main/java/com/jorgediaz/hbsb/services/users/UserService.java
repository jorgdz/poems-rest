package com.jorgediaz.hbsb.services.users;

import java.io.Serializable;
import java.util.List;

import com.jorgediaz.hbsb.entity.User;

public interface UserService {
	
	/**
	 * 
	 * @return all users
	 */
	List<User> findAll ();
		
	/**
	 * 
	 * @param id
	 * @return user by id
	 */
	User findById (Long id);
	
	/**
	 * 
	 * @param email
	 * @return user by email
	 */
	User findByEmail (String email);
	
	/**
	 * 
	 * @param user
	 */
	Serializable save (User user);
	
	/**
	 * 
	 * @param user
	 */
	void update (User user);
	
	/**
	 * 
	 * @param id
	 */
	void deleteById (Long id);
}
