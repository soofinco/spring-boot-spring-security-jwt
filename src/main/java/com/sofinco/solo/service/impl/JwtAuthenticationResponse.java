package com.sofinco.solo.service.impl;

import java.io.Serializable;

import com.sofinco.solo.security.JwtUser;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;
    public enum ResponseStatusEnum {
		SUCCESS, ERROR, WARNING, NO_ACCESS
	};
    private final String token;
    private final JwtUser jwtUser;
    private ResponseStatusEnum operationStatus;


    public JwtAuthenticationResponse(String token,JwtUser jwtUser,ResponseStatusEnum operationStatus) {
        this.token = token;
        this.jwtUser = jwtUser;
        this.operationStatus = operationStatus;
    }

    public String getToken() {
        return this.token;
    }

	public JwtUser getJwtUser() {
		return jwtUser;
	}

	public ResponseStatusEnum getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(ResponseStatusEnum operationStatus) {
		this.operationStatus = operationStatus;
	}
	
	
    
}
