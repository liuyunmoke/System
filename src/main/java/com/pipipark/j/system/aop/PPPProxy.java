package com.pipipark.j.system.aop;

/***
 * 代理接口
 * @author pipipark:cwj
 */
public interface PPPProxy<T> {

	T original();
	T target();
	PPPProxy<T> before(PPPNotice after);
	PPPProxy<T> after(PPPNotice before);
	PPPProxy<T> throwable(PPPNotice throwable);
}
