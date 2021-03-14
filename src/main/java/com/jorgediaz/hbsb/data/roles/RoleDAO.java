package com.jorgediaz.hbsb.data.roles;

import java.util.List;

import com.jorgediaz.hbsb.entity.Role;

public interface RoleDAO {
	
	/**
	 * 
	 * @return all roles
	 */
	List<Role> findAll ();
	
	/**
	 * 
	 * @param id
	 * @return one role by id
	 */
	Role findById (Long id);
}
