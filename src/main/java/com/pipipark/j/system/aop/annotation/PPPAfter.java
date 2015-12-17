package com.pipipark.j.system.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/***
 * 后置事件注解.
 * @see AfterNotice.class
 * @author pipipark:cwj
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PPPAfter {

	Class<?> value();
}
