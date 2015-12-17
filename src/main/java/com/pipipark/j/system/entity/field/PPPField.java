package com.pipipark.j.system.entity.field;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import com.pipipark.j.system.IPPPark;
import com.pipipark.j.system.core.PPPConstant;
import com.pipipark.j.system.entity.exception.PPPEntityException;
import com.pipipark.j.system.exception.PPPServiceException;

/**
 * 字段封装类,
 * 封装实体字段的几个常用属性.
 * @author pipipark:cwj
 */
@SuppressWarnings({ "rawtypes", "serial" })
public final class PPPField<T>  implements IPPPark {

	/*
	 * 字段名.
	 */
	private String name;
	
	/*
	 * 字段类型.
	 */
	private Class<T> type;
	
	/*
	 * 字段值.
	 */
	private T value;
	
	/*
	 * 是否静态
	 */
	private Boolean isStatic = false;
	
	/*
	 * 是否final
	 */
	private Boolean isFinal = false;
	
	/*
	 * 保密域类型
	 */
	private int security;
	
	/*
	 * 字段注解.
	 */
	private List<Annotation> annotations;
	
	private PPPField(String name, Boolean isStatic, Boolean isFinal, Integer security, Class<T> type, T value, Annotation...annotations){
		this.name = name;
		this.type = type;
		this.value = value;
		this.isStatic = isStatic;
		this.isFinal = isFinal;
		this.security = security;
		this.annotations = Arrays.asList(annotations);
	}
	private PPPField(String name, Class<T> type, T value, Annotation...annotations){
		this(name, false, false, PPPConstant.Entities.FIELD_PRIVATE, type, value, annotations);
	}
	
	/**
	 * 静态方法,实例化返回PPPField字段对象.
	 * @param clazz(字段所属对象)
	 * @param fieldName(字段名)
	 * @return PPPField
	 */
	@SuppressWarnings("unchecked")
	public static final PPPField newInstance(Class<?> clazz, String fieldName) throws PPPServiceException{
		Field field = null;
		PPPField f = null;
		try {
			field = clazz.getDeclaredField(fieldName);
			if(field==null){
				return null;
			}
			field.setAccessible(true);
			Boolean isStatic = Modifier.isStatic(field.getModifiers());
			Boolean isFinal = Modifier.isFinal(field.getModifiers());
			Integer security = null;
			if(Modifier.isPublic(field.getModifiers())){
				security = PPPConstant.Entities.FIELD_PUBLIC;
			}else if(Modifier.isPrivate(field.getModifiers())){
				security = PPPConstant.Entities.FIELD_PRIVATE;
			}else if(Modifier.isProtected(field.getModifiers())){
				security = PPPConstant.Entities.FIELD_PROTECTED;
			}else{
				security = PPPConstant.Entities.FIELD_FRIENDY;
			}
			f = new PPPField(fieldName, isStatic, isFinal, security, field.getType(), null, field.getAnnotations());
		} catch (NoSuchFieldException | SecurityException e) {
			throw new PPPEntityException("PPPField can't got Exception!", e);
		} finally{
			if(field!=null){
				field.setAccessible(false);
			}
		}
		return f;
	}
	
	/**
	 * 静态方法,实例化返回PPPField字段对象.
	 * @param clazz(字段所属对象)
	 * @param fieldName(字段名)
	 * @param typeClass(字段类型)
	 * @return PPPField
	 */
	public static final <M> PPPField<M> newInstance(Class<?> objClazz, String fieldName, Class<M> typeClass) throws PPPServiceException{
		Field field = null;
		PPPField<M> f = null;
		try {
			field = objClazz.getDeclaredField(fieldName);
			if(field==null){
				return null;
			}
			field.setAccessible(true);
			Boolean isStatic = Modifier.isStatic(field.getModifiers());
			Boolean isFinal = Modifier.isFinal(field.getModifiers());
			Integer security = null;
			if(Modifier.isPublic(field.getModifiers())){
				security = PPPConstant.Entities.FIELD_PUBLIC;
			}else if(Modifier.isPrivate(field.getModifiers())){
				security = PPPConstant.Entities.FIELD_PRIVATE;
			}else if(Modifier.isProtected(field.getModifiers())){
				security = PPPConstant.Entities.FIELD_PROTECTED;
			}else{
				security = PPPConstant.Entities.FIELD_FRIENDY;
			}
			f = new PPPField<M>(fieldName, isStatic, isFinal, security, typeClass, null, field.getAnnotations());
		} catch (NoSuchFieldException | SecurityException e) {
			throw new PPPEntityException("PPPField can't got Exception!", e);
		} finally{
			if(field!=null){
				field.setAccessible(false);
			}
		}
		return f;
	}
	
	/**
	 * 查看该字段是否拥有某注解类型.并将其返回.
	 * @param annotation
	 * @return M <M extends Annotation>
	 */
	@SuppressWarnings("unchecked")
	public <M extends Annotation> M annotation(Class<M> annotation) {
		if(annotations==null){
			return null;
		}
		for (Annotation a : annotations) {
			if(a.annotationType() == annotation){
				return (M)a;
			}
		}
		return null;
	}
	public String name() {
		return name;
	}
	public Class<T> type() {
		return type;
	}
	public T value() {
		return value;
	}
	public Boolean isStatic() {
		return isStatic;
	}
	public Boolean isFinal(){
		return isFinal;
	}
	public int security(){
		return security;
	}
	public List<Annotation> annotations() {
		return annotations;
	}
}
