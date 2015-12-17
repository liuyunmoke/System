package com.pipipark.j.system.exception;

import com.pipipark.j.system.core.PPPLogger;

/***
 * 提示性异常,
 * 重要性:"弱",在debug模式下打印输出.
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
