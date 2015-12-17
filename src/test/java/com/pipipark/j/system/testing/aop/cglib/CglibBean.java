package com.pipipark.j.system.testing.aop.cglib;

import com.pipipark.j.system.aop.annotation.PPPAfter;
import com.pipipark.j.system.aop.annotation.PPPBefore;
import com.pipipark.j.system.aop.annotation.PPPThrowable;
import com.pipipark.j.system.exception.PPPServiceException;
import com.pipipark.j.system.testing.aop.AfterNotice;
import com.pipipark.j.system.testing.aop.BeforeNotice;
import com.pipipark.j.system.testing.aop.ThrowableNotice;

public class CglibBean {

	@PPPBefore(BeforeNotice.class)
	@PPPAfter(AfterNotice.class)
	@PPPThrowable(ThrowableNotice.class)
	public void testCglibProxy() throws PPPServiceException {
		System.out.println("this is cglib proxy test!");
		throw new NullPointerException("is null exception.");
	}
}
