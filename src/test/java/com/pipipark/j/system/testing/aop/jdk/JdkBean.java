package com.pipipark.j.system.testing.aop.jdk;

import com.pipipark.j.system.aop.annotation.PPPBefore;
import com.pipipark.j.system.testing.aop.BeforeNotice;

public class JdkBean implements IJdk {

	@Override
	@PPPBefore(BeforeNotice.class)
	public void testJdkProxy() {
		System.out.println("this is jdk proxy test!");
	}
	
}
