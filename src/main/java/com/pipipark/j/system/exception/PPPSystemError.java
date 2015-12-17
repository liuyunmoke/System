package com.pipipark.j.system.exception;

import com.pipipark.j.system.core.PPPLogger;


/***
 * 崩溃性异常,
 * 重要性:"极高",导致系统无法继续使用,单独记入重要日志文件.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class PPPSystemError extends Error implements PPPThrowable {

	public PPPSystemError(String msg){
		super(msg);
		PPPLogger.error(this, msg);
	}
	
	public PPPSystemError(String msg, Throwable e){
		super(msg, e);
		PPPLogger.error(this, msg, e);
	}

}
