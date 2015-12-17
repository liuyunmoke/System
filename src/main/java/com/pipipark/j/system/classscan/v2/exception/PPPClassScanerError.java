package com.pipipark.j.system.classscan.v2.exception;

import com.pipipark.j.system.exception.PPPSystemError;

@SuppressWarnings("serial")
public class PPPClassScanerError extends PPPSystemError {
	
	public PPPClassScanerError(String msg) {
		super(msg);
	}

	public PPPClassScanerError(String msg, Error e) {
		super(msg, e);
	}
	
	public PPPClassScanerError(String msg, Exception e) {
		super(msg, e);
	}

}
