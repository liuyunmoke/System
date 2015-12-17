package com.pipipark.j.system.entity.exception;

import com.pipipark.j.system.exception.PPPServiceException;

/***
 * 实体类异常,
 * 当实体发生内部异常时抛出.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class PPPEntityException extends PPPServiceException {
	
	public PPPEntityException(String msg) {
		super(msg);
	}

	public PPPEntityException(String msg, Exception e) {
		super(msg, e);
	}

}
