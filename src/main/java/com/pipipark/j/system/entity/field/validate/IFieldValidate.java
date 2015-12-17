package com.pipipark.j.system.entity.field.validate;

import java.lang.reflect.Field;

/**
 * 字段验证接口
 * @author pipipark:cwj
 */
public interface IFieldValidate {

	boolean handler(Field field, Object clazzObj);
}
