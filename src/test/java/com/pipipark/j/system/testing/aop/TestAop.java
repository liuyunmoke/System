package com.pipipark.j.system.testing.aop;

import com.pipipark.j.system.aop.PPPNotice;
import com.pipipark.j.system.aop.PPPProxy;
import com.pipipark.j.system.aop.PPPProxyFactory;
import com.pipipark.j.system.exception.PPPServiceException;
import com.pipipark.j.system.testing.aop.cglib.CglibBean;
import com.pipipark.j.system.testing.aop.jdk.JdkBean;
import com.pipipark.j.system.testing.aop.jdk.IJdk;

public class TestAop {

	
	@SuppressWarnings("serial")
	public static void main(String args[]) throws PPPServiceException{
		System.out.println("jdkProxy:");
		PPPProxy<IJdk> jdkProxy = PPPProxyFactory.jdkProxy(new JdkBean(), IJdk.class);
		jdkProxy.after(new PPPNotice("testJdkProxy") {
			@Override
			public void fireEvent() {
				System.out.println("notice after method2!");
			}
		}).target().testJdkProxy();
		
		System.out.println("===========================华丽分界线===========================");
		
		PPPProxy<CglibBean> cgilbProxy = PPPProxyFactory.cglibProxy(new CglibBean());
		cgilbProxy.target().testCglibProxy();
	}
}
