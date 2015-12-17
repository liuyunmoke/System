package com.pipipark.j.system.entity.field.validate;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import com.pipipark.j.system.core.PPPConstant;
import com.pipipark.j.system.core.PPPLogger;

import org.apache.commons.lang.StringUtils;

/**
 * 字段验证枚举类
 * 
 * @see 一些公共的常用的验证 权重分为100个等级,前10个系统保留为最高最优先验证级别.
 * @author pipipark:cwj
 */
public enum FieldValidateEnum {

	/*
	 * 非Null
	 */
	NotNull(PPPConstant.Indexs.DEFAULT_INDEX-3000, "value can't be null!",
			new IFieldValidate() {
				public boolean handler(Field field, Object obj) {
					try {
						field.setAccessible(true);
						if (field.get(obj) != null) {
							return true;
						}
					} catch (IllegalArgumentException | IllegalAccessException
							| SecurityException e) {
						PPPLogger.debug(obj, "field validate failure!", e);
					} finally {
						field.setAccessible(false);
					}
					return false;
				}
			}),
	/*
	 * 非空String,非空List,非空Map
	 */
	NotEmpty(PPPConstant.Indexs.DEFAULT_INDEX, "content can't be empty!",
			new IFieldValidate() {
				public boolean handler(Field field, Object obj) {
					try {
						field.setAccessible(true);
						Object valueObj = field.get(obj);
						if (Collection.class.isAssignableFrom(field.getType())) {
							// 检验集合
							Collection<?> value = (Collection<?>) valueObj;
							if (!value.isEmpty()) {
								return true;
							}
						} else if (field.getType() == String.class) {
							// 检验字符串
							String value = (String) valueObj;
							if (!StringUtils.isEmpty(value)) {
								return true;
							}
						} else if (Map.class.isAssignableFrom(field.getType())) {
							// 检验Map
							Map<?, ?> value = (Map<?, ?>) valueObj;
							if (!value.isEmpty()) {
								return true;
							}
						}
					} catch (IllegalArgumentException | IllegalAccessException
							| SecurityException e) {
						PPPLogger.debug(obj, "field validate failure!", e);
					} finally {
						field.setAccessible(false);
					}
					return false;
				}
			}),

	/*
	 * 数字非负验证
	 */
	NotNegativeNumber(PPPConstant.Indexs.HIGHEST_INDEX-3000,
			"number can't be less than zero!", new IFieldValidate() {
				public boolean handler(Field field, Object obj) {
					try {
						field.setAccessible(true);
						Object valueObj = field.get(obj);
						char first = valueObj.toString().charAt(0);
						if(!"-".equals(first)){
							return true;
						}
					} catch (IllegalArgumentException | IllegalAccessException
							| SecurityException e) {
						PPPLogger.debug(obj, "field validate failure!", e);
					} finally {
						field.setAccessible(false);
					}
					return false;
				}
			});

	/*
	 * 排序.
	 */
	private Integer _serial;

	/*
	 * 提示内容.
	 */
	private String _promptContent;

	/*
	 * 处理方法.
	 */
	private IFieldValidate _action;

	private FieldValidateEnum(Integer serial, String prompt,
			IFieldValidate action) {
		this._serial = serial;
		this._promptContent = prompt;
		this._action = action;
	}

	public Integer value() {
		return this._serial;
	}

	public String prompt() {
		return this._promptContent;
	}

	public FieldValidateEnum instance() {
		return this;
	}

	public boolean validate(Field field, Object clazzObj) {
		return this._action.handler(field, clazzObj);
	}
}
