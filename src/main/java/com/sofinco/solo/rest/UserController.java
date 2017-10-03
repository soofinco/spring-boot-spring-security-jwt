package com.sofinco.solo.rest;

import com.sofinco.solo.model.persistent.Role;
import com.sofinco.solo.model.persistent.RoleName;
import com.sofinco.solo.security.JwtTokenUtil;
import com.sofinco.solo.security.JwtUser;
import com.sofinco.solo.service.UserService;
import com.sofinco.solo.model.persistent.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Api(value = "user", description = "Users Controller API")
@RestController
public class UserController extends BaseController {
	@Autowired
    UserService userService;

	@RequestMapping(value = "/user/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<User> addUser(@RequestBody User user) {
		
		if (user !=null) {
			userService.saveOrUpdate(user);
			return new ResponseEntity<>(user,HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}


	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@RequestMapping(value = "/connectedUser", method = RequestMethod.GET)
	public JwtUser getAuthenticatedUser(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader).substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		return (JwtUser) userDetailsService.loadUserByUsername(username);
		
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	public ResponseEntity<User> getAuthenticatedUser() {
		User usr = new User();
		Role rle= new Role();
		rle.setRoleName(RoleName.ROLE_ADMIN);
		List<Role> rles = new ArrayList<>();
		rles.add(rle);
		usr.setRoles(rles);
		usr.setUsername("sof");
		usr.setPassword("sof");
		usr.setFirstName("sofien");
		usr.setLastName("mnassri");
		usr.setEmail("sof@gmail.com");
		usr.setEnabled(Boolean.TRUE);
		userService.saveOrUpdate(usr);
		return new ResponseEntity<>(usr,HttpStatus.OK);
	}

}
