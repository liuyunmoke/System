package com.pipipark.j.system.testing.aop;

import com.pipipark.j.system.aop.PPPNotice;

@SuppressWarnings("serial")
public class AfterNotice extends PPPNotice {

	public AfterNotice(String name) {
		super(name);
	}

	@Override
	public void fireEvent() {
		System.out.println("notice after method!");
	}

}
