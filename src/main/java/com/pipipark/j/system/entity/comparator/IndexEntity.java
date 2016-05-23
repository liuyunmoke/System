package com.pipipark.j.system.entity.comparator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.pipipark.j.system.core.PPPConstant;



/***
 * 实体类索引注解.
 * @author pipipark:cwj
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IndexEntity {

	PPPConstant.Index value() default PPPConstant.Index.Default;
}
