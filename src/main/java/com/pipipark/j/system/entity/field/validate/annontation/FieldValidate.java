package com.pipipark.j.system.entity.field.validate.annontation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.pipipark.j.system.entity.field.validate.FieldValidateEnum;

/**
 * 字段验证注解
 * @see 注解类 FieldValidateEnum
 * @author pipipark:cwj
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldValidate {

	FieldValidateEnum[] value() default {FieldValidateEnum.NotNull};
}
