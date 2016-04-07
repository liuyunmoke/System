package com.pipipark.j.system.testing.aop;

import com.pipipark.j.system.aop.PPPNotice;
import com.pipipark.j.system.listener.EventParams;

@SuppressWarnings("serial")
public class AfterNotice extends PPPNotice {

	public AfterNotice(String name) {
		super(name);
	}

	@Override
	public void fireEvent() {
		System.out.println("notice after method!");
	}

	@Override
	public void fireEvent(EventParams p) {
		
	}

}
