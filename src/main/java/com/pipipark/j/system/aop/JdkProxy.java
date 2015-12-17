package com.pipipark.j.system.aop;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.List;

import com.pipipark.j.system.aop.annotation.PPPAfter;
import com.pipipark.j.system.aop.annotation.PPPBefore;
import com.pipipark.j.system.aop.annotation.PPPThrowable;
import com.pipipark.j.system.entity.SimplePPPEntity;

/***
 * jdk代理工厂,生产代理后的对象.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class JdkProxy<T> extends SimplePPPEntity implements PPPProxy<T> {

	/*
	 * 待代理对象.
	 */
	private T original;
	
	/*
	 * 代理对象.
	 */
	private T target;
	
	/**
	 * get方法.
	 * @return 代理后对象.
	 */
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
		before.type(PPPNotice.BEFORE_NOTICE);
		this.addListener(before);
		return this;
	}
	@Override
	@SuppressWarnings("unchecked")
	public PPPProxy<T> after(PPPNotice after) {
		after.type(PPPNotice.AFTER_NOTICE);
		this.addListener(after);
		return this;
	}
	@Override
	@SuppressWarnings("unchecked")
	public PPPProxy<T> throwable(PPPNotice throwable) {
		throwable.type(PPPNotice.THROWABLE_NOTICE);
		this.addListener(throwable);
		return this;
	}

	/**
	 * 构造函数.
	 * @param original,待代理的对象(待代理对象必须是待代理接口的实现类)
	 * @param clazz,待代理接口
	 */
	@SuppressWarnings("unchecked")
	public JdkProxy(T original, Class<T> clazz) {
		this.original = original;
		this.target = (T) Proxy.newProxyInstance(original.getClass().getClassLoader(), 
			new Class[] { clazz },
			new JdkInvocationHandler<T>(this, this.original)
		);
	}
	
	/**
	 * jdk代理后的实际执行类.
	 * @author pipipark:cwj
	 */
	private class JdkInvocationHandler<TT> implements InvocationHandler{
		
		/*
		 * jdkProxy工厂独享.
		 */
		private JdkProxy<TT> jdkProxy;
		
		/*
		 * jdk原始对象.
		 */
		private TT jdkOriginal;
		
		public JdkInvocationHandler(JdkProxy<TT> jdkProxy, TT jdkOriginal){
			this.jdkProxy = jdkProxy;
			this.jdkOriginal = jdkOriginal;
		}

		/**
		 * 执行方法.
		 */
		@SuppressWarnings("unchecked")
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws java.lang.Throwable {
			//获得原始对象的方法签名.
			Method originalMethod = jdkOriginal.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
			
			//查看是否注解前置通知
			PPPBefore ab = originalMethod.getAnnotation(PPPBefore.class);
			if(ab!=null){
				//有前置通知注解时候,取得通知类,构造后触发前置通知.
				Class<?> clazz = ab.value();
				if(!Modifier.isAbstract(clazz.getModifiers()) && PPPNotice.class.isAssignableFrom(clazz)){
					Constructor<?> con = clazz.getDeclaredConstructor(new Class[]{String.class});
					con.setAccessible(true);
					PPPNotice notice = (PPPNotice)con.newInstance(method.getName());
					con.setAccessible(false);
					notice.fireEvent();
				}
			}else{
				//无前置通知注解时候,查看是否在工厂类上添加了前置事件,并检查每个前置事件的作用方法签名(签名后,只对签名的方法调用前置事件).
				List<PPPNotice> befores = jdkProxy.getEvents(PPPNotice.class);
				if(befores!=null && befores.size()>0){
					for (PPPNotice b : befores) {
						if(PPPNotice.BEFORE_NOTICE.equals(b.name()) && b.check(method.getName())){
							jdkProxy.fireListener(b);
						}
					}
				}
			}
			
			//返回值.
			Object result = null;
			try{
				//执行方法,填值.
				result = method.invoke(jdkOriginal, args);
			}catch(Exception ex){
				//Exception异常通知.
				throwable(originalMethod);
				throw ex;
			}catch(Error er){
				//SystemError异常通知.
				throwable(originalMethod);
				throw er;
			}
			
			//查看是否有注解后置通知,其他注释同前置通知.
			PPPAfter aa = originalMethod.getAnnotation(PPPAfter.class);
			if(aa!=null){
				Class<?> clazz = aa.value();
				if(!Modifier.isAbstract(clazz.getModifiers()) && PPPNotice.class.isAssignableFrom(clazz)){
					Constructor<?> con = clazz.getDeclaredConstructor(new Class[]{String.class});
					con.setAccessible(true);
					PPPNotice notice = (PPPNotice)con.newInstance(method.getName());
					con.setAccessible(false);
					notice.fireEvent();
				}
			}else{
				List<PPPNotice> afters = jdkProxy.getEvents(PPPNotice.class);
				if(afters!=null && afters.size()>0){
					for (PPPNotice a : afters) {
						if(PPPNotice.AFTER_NOTICE.equals(a.name()) && a.check(method.getName())){
							jdkProxy.fireListener(a);
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
				if(!Modifier.isAbstract(clazz.getModifiers()) && PPPNotice.class.isAssignableFrom(clazz)){
					Constructor<?> con = clazz.getDeclaredConstructor(new Class[]{String.class});
					con.setAccessible(true);
					PPPNotice notice = (PPPNotice)con.newInstance(method.getName());
					con.setAccessible(false);
					notice.fireEvent();
				}
			}else{
				List<PPPNotice> exceptions = jdkProxy.getEvents(PPPNotice.class);
				if(exceptions!=null && exceptions.size()>0){
					for (PPPNotice e : exceptions) {
						if(PPPNotice.THROWABLE_NOTICE.equals(e.name()) && e.check(method.getName())){
							jdkProxy.fireListener(e);
						}
					}
				}
			}
		}
	}
}
