package com.pipipark.j.system.exception;

import com.pipipark.j.system.core.PPPLogger;

/***
 * 业务性异常,
 * 需要try-catch.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class PPPServiceException extends Exception implements PPPThrowable {

	/**
	 * 构造
	 * @param msg 异常信息
	 */
	public PPPServiceException(String msg){
		this(msg, false, 1);
	}
	
	/**
	 * 构造
	 * @param msg 异常信息
	 * @param log 是否记录日志
	 */
	public PPPServiceException(String msg, boolean log){
		this(msg, log, 1);
	}
	
	/**
	 * 构造
	 * @param msg 异常信息
	 * @param log 是否记录日志
	 * @param weight 日志权重. 0:debug, 1:info, 2:error.
	 */
	public PPPServiceException(String msg, boolean log, int weight){
		super(msg);
		if(log){
			switch(weight){
				case 0: PPPLogger.debug(this, msg);break;
				case 1: PPPLogger.info(this, msg);break;
				case 2: PPPLogger.error(this, msg);break;
				default: PPPLogger.info(this, msg);
			}
		}
	}
	
	/**
	 * 已有异常的构造
	 * @param msg 异常信息
	 * @param log 是否记录日志
	 * @param weight 日志权重. 0:debug, 1:info, 2:error.
	 */
	public PPPServiceException(String msg, Exception e){
		this(msg, e, false);
	}
	
	/**
	 * 已有异常的构造
	 * @param msg 异常信息
	 * @param log 是否记录日志
	 * @param weight 日志权重. 0:debug, 1:info, 2:error.
	 */
	public PPPServiceException(String msg, Exception e, boolean log){
		this(msg, e, false, 1);
	}
	
	/**
	 * 已有异常的构造
	 * @param msg 异常信息
	 * @param log 是否记录日志
	 * @param weight 日志权重. 0:debug, 1:info, 2:error.
	 */
	public PPPServiceException(String msg, Exception e, boolean log, int weight){
		super(msg, e);
		if(log){
			switch(weight){
				case 0: PPPLogger.debug(this, msg);break;
				case 1: PPPLogger.info(this, msg);break;
				case 2: PPPLogger.error(this, msg);break;
				default: PPPLogger.info(this, msg);
			}
		}
	}
}
