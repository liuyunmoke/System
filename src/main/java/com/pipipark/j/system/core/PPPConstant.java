package com.pipipark.j.system.core;

import com.pipipark.j.system.IPPPark;

/**
 * 常量工具类,
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public abstract class PPPConstant implements IPPPark {
	
	
	/**
	 * 系统常量工具类,
	 * 默认字符集,业务路径等.
	 */
	public static abstract class Systems{
		static{
			OS_NAME = System.getProperty("os.name");
			OS_ARCH = System.getProperty("os.arch");
			FILE_SEPARATOR = System.getProperty("file.separator");
			PATH_SEPARATOR = System.getProperty("path.separator");
			LINE_SEPARATOR = System.getProperty("line.separator");
			USER_HOME = System.getProperty("user.home");
			USER_DIR = System.getProperty("user.dir");
			USER_NAME = System.getProperty("user.name");
			JAVA_VERSION = System.getProperty("java.version");
			JAVA_HOME = System.getProperty("java.home");
		}
		
		/**
		 * 默认字符集
		 */
		public static final String DEFAULT_CHARSET = "UTF-8";
		
		/**
		 * 默认查找的资源路径
		 */
		public static final String DEFAULT_RESOURCE_PATH = "com/pipipark/j/resource/";
		
		/**
		 * 当前操作系统名称
		 */
		public static final String OS_NAME;
		
		
		/**
		 * 当前操作系统架构(如:x86)
		 */
		public static final String OS_ARCH;
		
		/**
		 * 当前操作系统默认文件分隔符(如: Unix="/")
		 */
		public static final String FILE_SEPARATOR;
		
		
		/**
		 * 当前操作系统默认路径分隔符(如: Unix=":")
		 */
		public static final String PATH_SEPARATOR;
		
		/**
		 * 当前操作系统默认换行符(如: Unix="\n")
		 */
		public static final String LINE_SEPARATOR;
		
		/**
		 * 当前系统用户主目录(如: windows="c:/user/Administrator")
		 */
		public static final String USER_HOME;
		
		/**
		 * 当前系统用户工作目录
		 */
		public static final String USER_DIR;
		
		/**
		 * 当前系统用户名称
		 */
		public static final String USER_NAME;
		
		/**
		 * 当前系统java版本
		 */
		public static final String JAVA_VERSION;
		
		/**
		 * 当前系统java目录
		 */
		public static final String JAVA_HOME;
	}
	
	
	
	/**
	 * 排序常量工具类,
	 * 提供0-10000的级别常量,"0"表示优先级最高级别,"10000"为优先级最低,默认是"5500".
	 */
	public static abstract class Indexs {
		public static final int HIGHEST_INDEX = 1000;
		public static final int DEFAULT_INDEX = 5500;
		public static final int MINIMUM_INDEX = 10000;
		public static final int INDEX_UNIT = 50;
	}
	
	/**
	 * 实体工具类,
	 * 一些反射使用的工具.
	 */
	public static abstract class Entities {
		public static final int FIELD_PRIVATE = 1;
		public static final int FIELD_PROTECTED = 2;
		public static final int FIELD_PUBLIC = 3;
		public static final int FIELD_FRIENDY = 4;
	}
	
	
	/***
	 * 日期格式化常量工具类.
	 */
	public abstract class DateFormats {
		public static final String yyyy_MM_dd = "yyyy-MM-dd";
		public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
		public static final String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
		public static final String yyyyMMdd = "yyyyMMdd";
		public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
		public static final String year_month_day = "yyyy年MM月dd日";
	}
	
}
