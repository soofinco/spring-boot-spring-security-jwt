package com.sofinco.solo.repository;

import com.sofinco.solo.model.persistent.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByRoleName (String role);
}