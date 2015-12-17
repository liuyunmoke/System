package com.pipipark.j.system.classscan.v2.exception;

import com.pipipark.j.system.exception.PPPTipsException;

@SuppressWarnings("serial")
public class PPPClassScanerException extends PPPTipsException {
	
	public PPPClassScanerException(String msg) {
		super(msg);
	}

	public PPPClassScanerException(String msg, Exception e) {
		super(msg, e);
	}

}
