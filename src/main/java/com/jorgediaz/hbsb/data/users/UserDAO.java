package com.jorgediaz.hbsb.data.users;

import java.io.Serializable;
import java.util.List;

import com.jorgediaz.hbsb.entity.User;

public interface UserDAO {
	
	/**
	 * 
	 * @return all users
	 */
	List<User> findAll();
		
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
	 * @return id new user
	 */
	Serializable save (User user);
	
	/**
	 * 
	 * @param user
	 * @param id
	 */
	void update (User user);
	
	/**
	 * 
	 * @param email
	 * @param id
	 * @return one user by email and ignore current id
	 */
	User findByEmailAndNotId (String email, Long id);
	
	/**
	 * 
	 * @param id
	 */
	void delete (User user);
}
