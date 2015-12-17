package com.pipipark.j.system.entity.field.validate;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.pipipark.j.system.IPPPark;
import com.pipipark.j.system.entity.field.validate.annontation.FieldValidate;
import com.pipipark.j.system.exception.PPPServiceException;

/**
 * 字段验证工具类
 * @see 必先验证字段值是否为空.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class FieldVaildater implements IPPPark {

	public static final void vaildate(Object fieldObject) throws PPPServiceException{
		Field[] fields = fieldObject.getClass().getDeclaredFields();
		for (Field field : fields) {
			FieldValidate validateAnnotation = field.getAnnotation(FieldValidate.class);
			if(validateAnnotation==null){
				continue;
			}
			//强制验证是否空对象.
			List<FieldValidateEnum> ves = Arrays.asList(validateAnnotation.value());
			if(!ves.contains(FieldValidateEnum.NotNull)){
				if(!FieldValidateEnum.NotNull.validate(field,fieldObject)){
					throw new PPPServiceException("The field \""+field.getName()+"\" "+FieldValidateEnum.NotNull.prompt());
				}
			}
			//验证其他条件.
			for (FieldValidateEnum validateEnum : ves) {
				if(!validateEnum.validate(field,fieldObject)){
					throw new PPPServiceException("The field \""+field.getName()+"\" "+validateEnum.prompt());
				}
			}
		}
	}
	
}
