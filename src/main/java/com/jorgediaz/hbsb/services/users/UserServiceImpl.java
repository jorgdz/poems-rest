package com.jorgediaz.hbsb.services.users;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jorgediaz.hbsb.data.users.UserDAO;
import com.jorgediaz.hbsb.entity.User;
import com.jorgediaz.hbsb.exception.custom.ConflictException;
import com.jorgediaz.hbsb.exception.custom.NoContentException;
import com.jorgediaz.hbsb.exception.custom.NotFoundException;

@Service("serviceUser")
@Transactional
public class UserServiceImpl implements UserService {
	
	private UserDAO userDAO;
	
	public UserServiceImpl(UserDAO dao) {
		this.userDAO = dao;
	}
	
	@Override
	public List<User> findAll() {
		List<User> users = this.userDAO.findAll();
		
		if (users.size() <= 0) throw new NoContentException("No se han encontrado usuarios disponibles.");
		
		return users;
	}

	@Override
	public User findById(Long id) {
		User user = this.userDAO.findById(id);
		
		if (user == null) throw new NotFoundException("Usuario no encontrado.");
		
		return user;
	}

	@Override
	public User findByEmail(String email) {
		User user = this.userDAO.findByEmail(email);
		
		if (user == null) 
			throw new NotFoundException("No se ha encontrado un usuario con correo " + email);
		
		return user;
	}

	@Override
	public Serializable save(User user) {
		User _user = this.userDAO.findByEmail(user.getEmail());
		if (_user != null) 
			throw new ConflictException(String.format("El correo '%s' ya está en uso", user.getEmail()));
			
		user.setEnabled(false);
		user.setTokenexpired(false);
		Serializable userID = this.userDAO.save(user);
		
		return userID;
	}

	@Override
	public void update(User user) {
		User currentUser = this.userDAO.findById(user.getId());
		
		if(currentUser == null) 
			throw new NotFoundException(String.format("No se ha encontrado un usuario con el id '%s'", user.getId()));
		
		if (user.getFirstname() != null && !user.getFirstname().isEmpty() && !user.getFirstname().equals(currentUser.getFirstname()))
			currentUser.setFirstname(user.getFirstname());
		
		if (user.getLastname() != null && !user.getLastname().isEmpty() && !user.getLastname().equals(currentUser.getLastname()))
			currentUser.setLastname(user.getLastname());
		
		if (user.getEmail() != null && !user.getEmail().isEmpty() && !user.getEmail().equals(currentUser.getEmail())) {
			User _user = this.userDAO.findByEmailAndNotId(user.getEmail(), user.getId());
			
			if (_user != null) throw new ConflictException(String.format("El correo '%s' ya está en uso.", user.getEmail()));
			
			currentUser.setEmail(user.getEmail());
		}
		
		if (user.getEnabled() != null && !user.getEnabled().equals(currentUser.getEnabled()))
			currentUser.setEnabled(user.getEnabled());
		
		if (user.getTokenexpired() != null && !user.getTokenexpired().equals(currentUser.getTokenexpired()))
			currentUser.setTokenexpired(user.getTokenexpired());
		
		currentUser.setRoles(user.getRoles());
		
		this.userDAO.update(currentUser);
	}

	@Override
	public void deleteById(Long id) {
		User user = this.findById(id);
		this.userDAO.delete(user);
	}

}
