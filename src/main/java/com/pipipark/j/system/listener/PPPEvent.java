package com.pipipark.j.system.listener;

import com.pipipark.j.system.IPPPark;


/**
 * 基础事件模型接口.
 * @author pipipark:cwj
 */
public interface PPPEvent extends IPPPark {
	
	public void fireEvent(EventParams p);
}
