package com.jorgediaz.hbsb.services.roles;

import java.util.List;

import com.jorgediaz.hbsb.entity.Role;

public interface RoleService {
	
	/**
	 * 
	 * @return all roles
	 */
	List<Role> findAll ();
	
	/**
	 * 
	 * @param id
	 * @return Role by id
	 */
	Role findById (Long id);
}
