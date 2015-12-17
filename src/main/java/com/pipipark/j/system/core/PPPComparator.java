package com.pipipark.j.system.core;

import java.util.Comparator;

import com.pipipark.j.system.IPPPark;

/***
 * 比较器接口,
 * 实现compare(T o1, T o2),
 * 由工具类实现,用于比较两个类(可以是不同类型).
 * @author pipipark:cwj
 */
public interface PPPComparator<T> extends IPPPark,Comparator<T> {

}
