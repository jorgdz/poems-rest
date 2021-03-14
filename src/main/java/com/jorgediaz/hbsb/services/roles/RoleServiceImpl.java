package com.jorgediaz.hbsb.services.roles;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jorgediaz.hbsb.data.roles.RoleDAO;
import com.jorgediaz.hbsb.entity.Role;
import com.jorgediaz.hbsb.exception.custom.NoContentException;
import com.jorgediaz.hbsb.exception.custom.NotFoundException;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	private RoleDAO roleDao;
	
	public RoleServiceImpl(RoleDAO dao) {
		this.roleDao = dao;
	}
	
	@Override
	public List<Role> findAll() {
		List<Role> roles = this.roleDao.findAll();

		if(roles.size() <= 0) throw new NoContentException("No hay roles disponibles.");
		
		return roles;
	}
	
	@Override
	public Role findById(Long id) {
		Role role = this.roleDao.findById(id);
		if (role == null) throw new NotFoundException("Rol no encontrado.");
		
		return role;
	}

}
