package com.pipipark.j.system.core.map.exception;

import com.pipipark.j.system.exception.PPPTipsException;

@SuppressWarnings("serial")
public class PPPDoubleMapKeyNullException extends PPPTipsException {
	
	public PPPDoubleMapKeyNullException() {
		super("PPPDoubleMap's key can't be NULL!");
	}
	
	public PPPDoubleMapKeyNullException(String msg) {
		super(msg);
	}

	public PPPDoubleMapKeyNullException(String msg, Exception e) {
		super(msg, e);
	}

}
