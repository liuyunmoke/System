package com.pipipark.j.system.core.exception;

import com.pipipark.j.system.exception.PPPServiceException;

/***
 * 实体类异常,
 * 当实体发生内部异常时抛出.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class PPPVersionException extends PPPServiceException {
	
	public PPPVersionException(String msg) {
		super(msg);
	}

}
