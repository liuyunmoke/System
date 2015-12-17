package com.pipipark.j.system.core;

import com.pipipark.j.system.IPPPark;

/***
 * 比较器接口,
 * 实现compareTo(T o)方法,
 * 要求数据中的实例都是同一个类型.
 * @author pipipark:cwj
 */
public interface PPPComparable<T> extends IPPPark,Comparable<T> {

}
