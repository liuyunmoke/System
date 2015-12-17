package com.pipipark.j.system.core;

import com.pipipark.j.system.IPPPark;

/**
 * 对象辅助工具类.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public abstract class PPPObject implements IPPPark {
	
	public static final Boolean isEmpty(Object o){
		if(o==null){
			return true;
		}
		return false;
	}
	
}
