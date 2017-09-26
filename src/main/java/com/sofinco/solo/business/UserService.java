package com.sofinco.solo.business;

import com.sofinco.solo.model.persistent.User;

public interface UserService extends CRUDService<User> {

	User findByUsername(String email);

}