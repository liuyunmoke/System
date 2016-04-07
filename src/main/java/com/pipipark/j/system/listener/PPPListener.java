package com.pipipark.j.system.listener;

import java.util.Map;

import com.pipipark.j.system.IPPPark;


/**
 * 事件监听接口,
 * PPPEvent事件模型.
 * @author pipipark:cwj
 */
public interface PPPListener<T extends PPPEvent> extends IPPPark {

	@SuppressWarnings("unchecked")
	void addListener(T... events);
	
	void removeAllListener();
	
	void removeListener(T event);
	
	void removeListener(Class<T> eventType);
	
	void fireAllListener();
	
	void fireListener(T event);
	
	void fireListener(Class<T> eventType);
	
	void fireListener(Class<T> eventType, Map<String, Object>... p);
}
