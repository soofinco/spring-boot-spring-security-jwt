package com.sofinco.solo.rest;

import com.sofinco.solo.security.JwtAuthenticationRequest;
import com.sofinco.solo.security.JwtTokenUtil;
import com.sofinco.solo.security.JwtUser;
import com.sofinco.solo.service.impl.JwtAuthenticationResponse;
import com.sofinco.solo.service.impl.JwtAuthenticationResponse.ResponseStatusEnum;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = {"Authentication"})
public class AuthenticationController extends BaseController {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			Device device) throws AuthenticationException {

		try {
			// Perform the security
			final Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
							authenticationRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Reload password post-security so we can generate token
			UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
			JwtUser user = (JwtUser) userDetails;
			final String token = jwtTokenUtil.generateToken(userDetails, device);

			// Return the token
			return ResponseEntity.ok(new JwtAuthenticationResponse(token, user, ResponseStatusEnum.SUCCESS));
		} catch (Exception e) {
			return ResponseEntity.ok(new JwtAuthenticationResponse(null, null, ResponseStatusEnum.ERROR));
		}
	}

	@RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

		if (jwtTokenUtil.canTokenBeRefreshed(token)) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, user, ResponseStatusEnum.SUCCESS));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

}
