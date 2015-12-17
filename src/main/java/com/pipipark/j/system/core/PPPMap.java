package com.pipipark.j.system.core;

import java.util.HashMap;
import java.util.Map;

import com.pipipark.j.system.IPPPark;
import com.pipipark.j.system.core.map.PPPDoubleMap;
import com.pipipark.j.system.core.map.PPPTripleMap;


@SuppressWarnings("serial")
public abstract class PPPMap implements IPPPark {
	
	public static final <K,V> java.util.Map<K,V> map(){
		return new HashMap<K, V>();
	}
	
	public static final <K,KK,V> PPPDoubleMap<K,KK,V> doubleKey(){
		return PPPDoubleMap.create();
	}
	
	public static final <X,Y,Z,V> PPPTripleMap<X,Y,Z,V> threeKey(){
		return PPPTripleMap.create();
	}
	
	
	public static final Boolean isEmpty(Object o){
		if(o==null){
			return true;
		}
		if(o instanceof PPPDoubleMap){
			Map<?,?> m = (Map<?,?>)o;
			return m.isEmpty();
		}else if(o instanceof PPPDoubleMap){
			PPPDoubleMap<?,?,?> m = (PPPDoubleMap<?,?,?>)o;
			return m.isEmpty();
		}else if(o instanceof PPPTripleMap){
			PPPTripleMap<?,?,?,?> m = (PPPTripleMap<?,?,?,?>)o;
			return m.isEmpty();
		}
		
		return false;
	}
}
