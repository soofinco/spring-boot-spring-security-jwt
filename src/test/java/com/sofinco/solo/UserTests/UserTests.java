package com.sofinco.solo.UserTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sofinco.solo.business.UserService;
import com.sofinco.solo.model.persistent.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

	@Autowired
	private UserService usrService;

	@Test
	public void testSaveUser() throws Exception {
		
		User usr = new User();
		usr.setEnabled(Boolean.TRUE);
		usr.setLastName("mnassri");
		usr.setName("sofien");
		usr.setPassword("soofinco");
		usr.setUsername("sofien");

		usrService.saveOrUpdate(usr);

	}

}
