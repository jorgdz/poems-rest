package com.jorgediaz.hbsb.data.roles;
import java.util.List;

import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jorgediaz.hbsb.data.AbstractSession;
import com.jorgediaz.hbsb.entity.Role;

@Repository
@Transactional
public class RoleDAOImpl extends AbstractSession implements RoleDAO {
	
	@Override
	public List<Role> findAll() {
		List<Role> roles = this.getSession()
			.createQuery("SELECT DISTINCT r FROM Role r left join fetch r.users ORDER BY r.id ASC", Role.class)
			.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
			.getResultList();
		
		roles = this.getSession()
			.createQuery("SELECT DISTINCT r FROM Role r left join fetch r.privileges ORDER BY r.id ASC", Role.class)
			.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
			.getResultList();
				
		return roles;
	}
	
	@Override
	public Role findById(Long id) {
		List<Role> objects = this.getSession()
			.createQuery("FROM Role r left join fetch r.users WHERE r.id = :id", Role.class)
			.setParameter("id", id)
			.getResultList();
		
		objects = this.getSession()
			.createQuery("FROM Role r left join fetch r.privileges WHERE r.id = :id", Role.class)
			.setParameter("id", id)
			.getResultList();
		
		return objects.size() > 0 ? objects.get(0) : null;
	}

}
