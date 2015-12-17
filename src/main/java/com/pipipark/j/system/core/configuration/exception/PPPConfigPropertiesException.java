package com.pipipark.j.system.core.configuration.exception;

import com.pipipark.j.system.exception.PPPTipsException;

@SuppressWarnings("serial")
public class PPPConfigPropertiesException extends PPPTipsException {
	
	public PPPConfigPropertiesException(String msg) {
		super(msg);
	}

	public PPPConfigPropertiesException(String msg, Exception e) {
		super(msg, e);
	}

}
