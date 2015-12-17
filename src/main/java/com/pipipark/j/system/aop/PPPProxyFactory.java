package com.pipipark.j.system.aop;

import com.pipipark.j.system.IPPPark;

/***
 * 动态代理
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public abstract class PPPProxyFactory implements IPPPark {

	/**
	 * jdk方式代理,创建对象快,反射执行.
	 * @param target,要代理的对象.
	 * @param clazz,target要代理的接口类(包含要代理的接口方法).
	 * @return JdkProxy
	 */
	public static final <M,N extends M> PPPProxy<M> jdkProxy(final N target, Class<M> clazz) {
		return new JdkProxy<M>(target, clazz);
	}

	/**
	 * cglib方式代理,创建慢,执行快,适合单例.
	 * @param target,要代理的对象.
	 * @return CglibProxy
	 */
	public static final <M> PPPProxy<M> cglibProxy(final M target) {
		return new CglibProxy<M>(target);
	}
}
