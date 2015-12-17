package com.pipipark.j.system.exception;

import com.pipipark.j.system.core.PPPLogger;

/***
 * 业务性异常,
 * 重要性:"强",记入日志文件.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class PPPServiceException extends Exception implements PPPThrowable {


	public PPPServiceException(String msg){
		super(msg);
		PPPLogger.error(this, msg);
	}
	
	public PPPServiceException(String msg, Exception e){
		super(msg, e);
		PPPLogger.error(this, msg, e);
	}
}
