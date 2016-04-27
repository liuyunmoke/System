package com.pipipark.j.system.exception;

import com.pipipark.j.system.core.PPPLogger;

/***
 * 提示性异常,
 * 无需try-catch.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class PPPTipsException extends RuntimeException implements PPPThrowable {

	public PPPTipsException(String msg){
		super(msg);
		PPPLogger.info(this, msg);
	}
	
	public PPPTipsException(String msg, Exception e){
		super(msg, e);
		PPPLogger.info(this, msg, e);
	}
}
