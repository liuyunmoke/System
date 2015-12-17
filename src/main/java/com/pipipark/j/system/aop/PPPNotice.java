package com.pipipark.j.system.aop;

import org.apache.commons.lang.StringUtils;
import com.pipipark.j.system.listener.PPPEvent;


/***
 * 事件驱动的代理抽象类.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public abstract class PPPNotice implements PPPEvent {
	
	/*
	 * 常量
	 */
	public static final String BEFORE_NOTICE = "Before";
	public static final String AFTER_NOTICE = "After";
	public static final String THROWABLE_NOTICE = "Throwable";

	/*
	 * 触发方法名.
	 */
	private String _fireMethodName;
	private String _type;
	
	public PPPNotice(String methodName){
		_fireMethodName = methodName;
	}
	
	/*
	 * 通知类型
	 */
	public void type(String type){
		_type = type;
	}
	
	public String name(){
		return _type;
	}
	
	/**
	 * 检查通知注册的方法名与methodName是否一致
	 * @param methodName
	 */
	public Boolean check(String methodName){
		if(StringUtils.isEmpty(methodName)){
			return false;
		}
		if(!methodName.equals(_fireMethodName)){
			return false;
		}
		return true;
	}
	
	/**
	 * 触发器
	 */
	public abstract void fireEvent();

}
