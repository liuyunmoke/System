package com.pipipark.j.system.aop.exception;

import com.pipipark.j.system.exception.PPPSystemError;

@SuppressWarnings("serial")
public class PPPProxyError extends PPPSystemError {
	
	public PPPProxyError() {
		super("Fire PPPThrowableNotice.");
	}
	
	public PPPProxyError(String msg) {
		super(msg);
	}

	public PPPProxyError(String msg, Throwable e) {
		super(msg, e);
	}

}
