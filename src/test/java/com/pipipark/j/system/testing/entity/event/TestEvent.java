package com.pipipark.j.system.testing.entity.event;

import com.pipipark.j.system.listener.EventParams;
import com.pipipark.j.system.listener.PPPEvent;

@SuppressWarnings("serial")
public class TestEvent implements PPPEvent {
	
	private Integer sid = 0;
	
	public void add(Integer num){
		sid+=num;
	}

	@Override
	public void fireEvent(EventParams p) {
		System.out.println("fireEvent,sid="+sid+"!");
	}

}
