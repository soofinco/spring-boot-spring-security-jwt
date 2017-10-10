package com.sofinco.solo.rest;

import com.sofinco.solo.security.JwtTokenUtil;
import com.sofinco.solo.security.JwtUser;
import com.sofinco.solo.service.UserService;

import io.swagger.annotations.Api;

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


import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = {"Users"})
public class UserController extends BaseController {
	@Autowired
	UserService userService;

	@RequestMapping(value = "/user/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<User> addUser(@RequestBody User user) {

		if (user != null) {
			userService.saveOrUpdate(user);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
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
	public ResponseEntity<JwtUser> getAuthenticatedUser(HttpServletRequest request) {

		if (request.getHeader(tokenHeader) == null) {
			return new ResponseEntity<JwtUser>(HttpStatus.NO_CONTENT);
		}
		String token = request.getHeader(tokenHeader).substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		return new ResponseEntity<> ((JwtUser) userDetailsService.loadUserByUsername(username),HttpStatus.OK);

	}

//	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
//	public ResponseEntity<User> getAuthenticatedUser() {
//		User usr = new User();
//		Role rle = new Role();
//		rle.setRoleName(RoleName.ROLE_ADMIN);
//		List<Role> rles = new ArrayList<>();
//		rles.add(rle);
//		usr.setRoles(rles);
//		usr.setUsername("sofinco");
//		usr.setPassword("sofinco");
//		usr.setFirstName("sofien");
//		usr.setLastName("mnassri");
//		usr.setEmail("sof@gmail.com");
//		usr.setEnabled(Boolean.TRUE);
//		userService.saveOrUpdate(usr);
//		return new ResponseEntity<>(usr, HttpStatus.OK);
//	}

}
