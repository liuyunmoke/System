package com.pipipark.j.system.core;

import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

import com.pipipark.j.system.IPPPark;

/***
 * Log日志.
 * 
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public abstract class PPPLogger implements IPPPark {

	@SuppressWarnings("rawtypes")
	private static final ConcurrentMap<Class, Logger> loggers = new ConcurrentHashMap<Class, Logger>();
	private static final Logger ppp = LoggerFactory.getLogger(PPPLogger.class);
	private static Boolean button = true;

	static {
		InputStream stream = null;
		stream = PPPLogger.class.getClassLoader()
				.getResourceAsStream("system/logs/logback-log.xml");
		if (stream != null) {
			LoggerContext lc = (LoggerContext) LoggerFactory
					.getILoggerFactory();
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(lc);
			lc.reset();
			try {
				configurator.doConfigure(stream);
				StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
			} catch (JoranException e) {
				button = false;
			}
		} else {
			button = false;
		}
	}

	private static Logger get(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}
		Logger logger = loggers.get(clazz);
		if (logger == null) {
			logger = LoggerFactory.getLogger(clazz);
			loggers.put(clazz, logger);
		}
		return logger;
	}

	private static Logger get(Object obj) {
		if (obj == null) {
			return null;
		}
		Class<?> clazz = obj.getClass();
		Logger logger = loggers.get(clazz);
		if (logger == null) {
			logger = LoggerFactory.getLogger(clazz);
			loggers.put(clazz, logger);
		}
		return logger;
	}
	
	/**
	 * debug
	 * 
	 * @param content
	 * @param e
	 */
	public static void debug(String content, Exception e) {
		if(!button){
			return;
		}
		ppp.debug(content, e);
	}

	public static void debug(String content, Object... arg) {
		if(!button){
			return;
		}
		ppp.debug(content, arg);
	}

	public static void debug(Class<?> clazz, String content, Exception e) {
		if(!button){
			return;
		}
		get(clazz).debug(content, e);
	}

	public static void debug(Class<?> clazz, String content, Object... arg) {
		if(!button){
			return;
		}
		get(clazz).debug(content, arg);
	}

	public static void debug(Object obj, String content, Exception e) {
		if(!button){
			return;
		}
		get(obj).debug(content, e);
	}

	public static void debug(Object obj, String content, Object... arg) {
		if(!button){
			return;
		}
		get(obj).debug(content, arg);
	}

	/**
	 * info
	 * 
	 * @param content
	 * @param e
	 */
	public static void info(String content, Exception e) {
		if(!button){
			return;
		}
		ppp.info(content, e);
	}

	public static void info(String content, Object... arg) {
		if(!button){
			return;
		}
		ppp.info(content, arg);
	}
	
	public static void systemInfo(String content) {
		info("★☆★☆★※ "+content+".............");
	}

	public static void info(Class<?> clazz, String content, Exception e) {
		if(!button){
			return;
		}
		get(clazz).info(content, e);
	}

	public static void info(Class<?> clazz, String content, Object... arg) {
		if(!button){
			return;
		}
		get(clazz).info(content, arg);
	}

	public static void info(Object obj, String content, Exception e) {
		if(!button){
			return;
		}
		get(obj).info(content, e);
	}

	public static void info(Object obj, String content, Object... arg) {
		if(!button){
			return;
		}
		get(obj).info(content, arg);
	}

	/**
	 * error
	 * 
	 * @param content
	 * @param e
	 */
	public static void error(String content, Throwable e) {
		if(!button){
			return;
		}
		ppp.error(content, e);
	}

	public static void error(String content, Object... arg) {
		if(!button){
			return;
		}
		ppp.error(content, arg);
	}

	public static void error(Class<?> clazz, String content, Throwable e) {
		if(!button){
			return;
		}
		get(clazz).error(content, e);
	}

	public static void error(Class<?> clazz, String content, Object... arg) {
		if(!button){
			return;
		}
		get(clazz).error(content, arg);
	}

	public static void error(Object obj, String content, Throwable e) {
		if(!button){
			return;
		}
		get(obj).error(content, e);
	}

	public static void error(Object obj, String content, Object... arg) {
		if(!button){
			return;
		}
		get(obj).error(content, arg);
	}
}
