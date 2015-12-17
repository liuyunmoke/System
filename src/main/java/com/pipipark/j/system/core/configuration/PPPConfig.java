package com.pipipark.j.system.core.configuration;

import java.util.List;

public interface PPPConfig {

	/**
	 * 根路径下所有properties文件中查找.
	 * @param key
	 */
	public String get(String key);
	
	/**
	 * 根路径下所有properties文件中查找,返回Integer类型.
	 * @param key
	 */
	public Integer getInteger(String key);
	
	/**
	 * 根路径下所有properties文件中查找,返回Long类型.
	 * @param key
	 */
	public Long getLong(String key);
	
	/**
	 * 根路径下所有properties文件中查找,返回Float类型.
	 * @param key
	 */
	public Float getFloat(String key);
	
	/**
	 * 根路径下所有properties文件中查找,返回Double类型.
	 * @param key
	 */
	public Double getDouble(String key);
	
	/**
	 * 根路径下所有properties文件中查找,返回布尔类型.
	 * @param key
	 */
	public Boolean getBoolean(String key);
	
	/**
	 * 根路径下所有properties文件中查找,返回集合类型.
	 * @param key
	 */
	public List<Object> getList(String key);
	
}
