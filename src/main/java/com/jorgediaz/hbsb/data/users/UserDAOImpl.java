package com.jorgediaz.hbsb.data.users;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jorgediaz.hbsb.data.AbstractSession;
import com.jorgediaz.hbsb.entity.User;

@Repository
@Transactional
public class UserDAOImpl extends AbstractSession implements UserDAO {

	@Override
	public List<User> findAll() {
		List<User> users = this.getSession()
			.createQuery("SELECT DISTINCT u FROM User u left join fetch u.roles r ORDER BY u.id DESC", User.class)
			.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
			.getResultList();
		
		return users;
	}

	@Override
	public User findById(Long id) {
		List<User> user = this.getSession()
			.createQuery("FROM User u left join fetch u.roles WHERE u.id = :id", User.class)
			.setParameter("id", id)
			.list();
		
		return user.size() > 0 ? user.get(0) : null;
	}

	@Override
	public User findByEmail(String email) {
		List<User> user = this.getSession()
			.createQuery("FROM User u left join fetch u.roles WHERE u.email = :email", User.class)
			.setParameter("email", email)
			.list();
		
		return user.size() > 0 ? user.get(0) : null;
	}

	@Override
	public Serializable save(User user) {
		return this.getSession().save(User.class.getName(), user);
	}

	@Override
	public void update(User user) {
		this.getSession().update(user);
	}

	@Override
	public User findByEmailAndNotId(String email, Long id) {
		List<User> user = this.getSession()
			.createQuery("FROM User u left join fetch u.roles WHERE u.email = :email AND u.id != :id", User.class)
			.setParameter("email", email)
			.setParameter("id", id)
			.list();
		
		return user.size() > 0 ? user.get(0) : null;
	}

	@Override
	public void delete(User user) {
		this.getSession().delete(user);
	}

}
