package com.pipipark.j.system.testing.aop;

import com.pipipark.j.system.aop.PPPNotice;

@SuppressWarnings("serial")
public class ThrowableNotice extends PPPNotice {

	public ThrowableNotice(String name) {
		super(name);
	}

	@Override
	public void fireEvent() {
		System.out.println("notice throwable method!");
	}

}
