package com.sofinco.solo.service;

import com.sofinco.solo.model.persistent.User;

public interface UserService extends CRUDService<User> {

	User findByUsername(String email);

}