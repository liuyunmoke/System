package com.pipipark.j.system.aop;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.pipipark.j.system.aop.annotation.PPPAfter;
import com.pipipark.j.system.aop.annotation.PPPBefore;
import com.pipipark.j.system.aop.annotation.PPPThrowable;
import com.pipipark.j.system.entity.SimplePPPEntity;

/***
 * cglib动态代理工厂.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class CglibProxy<T> extends SimplePPPEntity implements MethodInterceptor,PPPProxy<T> {

	private Enhancer enhancer = new Enhancer();
	private T original;
	private T target;

	@SuppressWarnings("unchecked")
	public CglibProxy(T original) {
		this.original = original;
		enhancer.setSuperclass(original.getClass());
		enhancer.setCallback(this);
		enhancer.setClassLoader(original.getClass().getClassLoader());
		target = (T)enhancer.create();
	}
	
	@Override
	public T original(){
		return this.original;
	}
	
	@Override
	public T target(){
		return this.target;
	}
	
	/**
	 * 通知类型.
	 * PPPAfterNotice.class,
	 * PPPBeforeNotice.class,
	 * PPPThrowableNotice.class.
	 * @see PPPNotice.class
	 * @return PPPProxy接口
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PPPProxy<T> before(PPPNotice before) {
		before.type("Before");
		this.addListener(before);
		return this;
	}
	@Override
	@SuppressWarnings("unchecked")
	public PPPProxy<T> after(PPPNotice after) {
		after.type("After");
		this.addListener(after);
		return this;
	}
	@Override
	@SuppressWarnings("unchecked")
	public PPPProxy<T> throwable(PPPNotice throwable) {
		throwable.type("Throwable");
		this.addListener(throwable);
		return this;
	}
	
	/**
	 * 代理对象执行方法,拦截器.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws java.lang.Throwable {
		Method originalMethod = original.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
		PPPBefore ab = originalMethod.getAnnotation(PPPBefore.class);
		
		if(ab!=null){
			Class<?> clazz = ab.value();
			if(PPPNotice.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())){
				Constructor<?> con = clazz.getDeclaredConstructor(new Class[]{String.class});
				con.setAccessible(true);
				PPPNotice notice = (PPPNotice)con.newInstance(method.getName());
				con.setAccessible(false);
				notice.fireEvent();
			}
		}else{
			List<PPPNotice> befores = this.getEvents(PPPNotice.class);
			if(befores!=null && befores.size()>0){
				for (PPPNotice b : befores) {
					if(PPPNotice.BEFORE_NOTICE.equals(b.name()) && b.check(method.getName())){
						this.fireListener(b);
					}
				}
			}
		}
		
		Object result = null;
		try{
			result = proxy.invokeSuper(obj, args);
		}catch(Exception ex){
			throwable(originalMethod);
			throw ex;
		}catch(Error er){
			throwable(originalMethod);
			throw er;
		}
		
		PPPAfter aa = originalMethod.getAnnotation(PPPAfter.class);
		if(aa!=null){
			Class<?> clazz = aa.value();
			if(PPPNotice.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())){
				Constructor<?> con = clazz.getDeclaredConstructor(new Class[]{String.class});
				con.setAccessible(true);
				PPPNotice notice = (PPPNotice)con.newInstance(method.getName());
				con.setAccessible(false);
				notice.fireEvent();
			}
		}else{
			List<PPPNotice> afters = this.getEvents(PPPNotice.class);
			if(afters!=null && afters.size()>0){
				for (PPPNotice a : afters) {
					if(PPPNotice.AFTER_NOTICE.equals(a.name()) && a.check(method.getName())){
						this.fireListener(a);
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 异常通知.
	 * @param method
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	private void throwable(Method method) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//查看是否有注解异常通知.
		PPPThrowable at = method.getAnnotation(PPPThrowable.class);
		if(at!=null){
			Class<?> clazz = at.value();
			if(PPPNotice.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())){
				Constructor<?> con = clazz.getDeclaredConstructor(new Class[]{String.class});
				con.setAccessible(true);
				PPPNotice notice = (PPPNotice)con.newInstance(method.getName());
				con.setAccessible(false);
				notice.fireEvent();
			}
		}else{
			List<PPPNotice> exceptions = this.getEvents(PPPNotice.class);
			if(exceptions!=null && exceptions.size()>0){
				for (PPPNotice e : exceptions) {
					if(PPPNotice.THROWABLE_NOTICE.equals(e.name()) && e.check(method.getName())){
						this.fireListener(e);
					}
				}
			}
		}
	}
}
