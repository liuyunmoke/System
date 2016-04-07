package com.pipipark.j.system.testing.aop;

import com.pipipark.j.system.aop.PPPNotice;
import com.pipipark.j.system.listener.EventParams;

@SuppressWarnings("serial")
public class ThrowableNotice extends PPPNotice {

	public ThrowableNotice(String name) {
		super(name);
	}

	@Override
	public void fireEvent() {
		System.out.println("notice throwable method!");
	}

	@Override
	public void fireEvent(EventParams p) {
		
	}

}
