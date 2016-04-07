package com.pipipark.j.system.testing.aop;

import com.pipipark.j.system.aop.PPPNotice;
import com.pipipark.j.system.listener.EventParams;

@SuppressWarnings("serial")
public class BeforeNotice extends PPPNotice {

	public BeforeNotice(String name) {
		super(name);
	}

	@Override
	public void fireEvent() {
		System.out.println("notice before method!");
	}

	@Override
	public void fireEvent(EventParams p) {
		
	}

}
