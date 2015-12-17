package com.pipipark.j.system.testing.aop;

import com.pipipark.j.system.aop.PPPNotice;

@SuppressWarnings("serial")
public class BeforeNotice extends PPPNotice {

	public BeforeNotice(String name) {
		super(name);
	}

	@Override
	public void fireEvent() {
		System.out.println("notice before method!");
	}

}
