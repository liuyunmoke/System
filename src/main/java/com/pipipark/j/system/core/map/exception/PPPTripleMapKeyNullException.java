package com.pipipark.j.system.core.map.exception;

import com.pipipark.j.system.exception.PPPTipsException;

@SuppressWarnings("serial")
public class PPPTripleMapKeyNullException extends PPPTipsException {
	
	public PPPTripleMapKeyNullException() {
		super("PPPTripleMap's key can't be NULL!");
	}
	
	public PPPTripleMapKeyNullException(String msg) {
		super(msg);
	}

	public PPPTripleMapKeyNullException(String msg, Exception e) {
		super(msg, e);
	}

}
