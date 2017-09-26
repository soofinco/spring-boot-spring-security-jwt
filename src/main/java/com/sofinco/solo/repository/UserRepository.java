package com.sofinco.solo.repository;

import org.springframework.data.repository.CrudRepository;

import com.sofinco.solo.model.persistent.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	 User  findByUsername(String username);
}
