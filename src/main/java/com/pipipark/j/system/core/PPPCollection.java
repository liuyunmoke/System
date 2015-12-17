package com.pipipark.j.system.core;

import java.util.Collection;

import com.pipipark.j.system.IPPPark;

/**
 * 对象辅助工具类.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public abstract class PPPCollection implements IPPPark {
	
	public static final Boolean isEmpty(Object o){
		if(o==null){
			return true;
		}
		if(o.getClass().isArray()){
			Object[] os = (Object[])o;
			if(os.length==0){
				return true;
			}
		}else if(o instanceof Collection){
			Collection<?> os = (Collection<?>)o;
			if(os.size()==0){
				return true;
			}
		}
		
		return false;
	}
	
}
