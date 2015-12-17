package com.pipipark.j.system.aop.exception;

import com.pipipark.j.system.exception.PPPServiceException;

@SuppressWarnings("serial")
public class PPPProxyException extends PPPServiceException {
	
	public PPPProxyException() {
		super("Fire PPPThrowableNotice.");
	}
	
	public PPPProxyException(String msg) {
		super(msg);
	}

	public PPPProxyException(String msg, Exception e) {
		super(msg, e);
	}
}
