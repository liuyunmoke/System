package com.pipipark.j.system.core.configuration.exception;

import com.pipipark.j.system.exception.PPPTipsException;

@SuppressWarnings("serial")
public class PPPConfigXmlException extends PPPTipsException {
	
	public PPPConfigXmlException(String msg) {
		super(msg);
	}

	public PPPConfigXmlException(String msg, Exception e) {
		super(msg, e);
	}

}
