package com.pipipark.j.system.classscan.v2;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.pipipark.j.system.IPPPark;
import com.pipipark.j.system.classscan.v2.exception.PPPClassScanerError;

@SuppressWarnings({ "serial","rawtypes" })
public class PPPScanerManager implements IPPPark {
	
	private static PPPScanerManager _me;
	private static final Object syncLock = new Object();
	private ConcurrentMap<String, PPPScaner> scaners = new ConcurrentHashMap<String, PPPScaner>();
//	private ConcurrentMap<Class, Object> objects = new ConcurrentHashMap<Class, Object>();
	private ConcurrentMap<Class, PPPScanEntity> classes = new ConcurrentHashMap<Class, PPPScanEntity>();
	
	private PPPScanerManager() {}
	
	private static final PPPScanerManager getInstance() {
		if (_me == null) {
			synchronized (syncLock) {
				_me = new PPPScanerManager();
			}
		}
		return _me;
	}
	
	static void set(String key, PPPScaner scaner){
		PPPScanerManager manager = getInstance();
		if(manager.scaners.containsKey(key)){
			throw new PPPClassScanerError("Scaner name must be exist only one!");
		}
		manager.scaners.put(key, scaner);
	}
	
//	static Class<?> exist(Class<?> key, Object target){
//		PPPScanerManager manager = getInstance();
//		Boolean bool = manager.classes.add(key);
//		if(!bool){
//			for (Iterator<Class<?>> ite = manager.classes.iterator();ite.hasNext();) {
//				Class<?> clazz = ite.next();
//				if(clazz==key){
//					return clazz;
//				}
//			}
//		}
//		return key;
//	}
	
//	public static Set<Class<?>> allScanClasses(){
//		PPPScanerManager manager = getInstance();
//		return manager.classes;
//	}
	
	public static PPPScaner[] allScaner(){
		PPPScanerManager manager = getInstance();
		return manager.scaners.values().toArray(new PPPScaner[0]);
	}
	
	public static PPPClassesScaner[] classesScaner(){
		PPPScanerManager manager = getInstance();
		List<PPPClassesScaner> ss = new ArrayList<PPPClassesScaner>();
		for (PPPScaner s : manager.scaners.values()) {
			if(s instanceof PPPClassesScaner){
				ss.add((PPPClassesScaner)s);
			}
		}
		return ss.toArray(new PPPClassesScaner[0]);
	}
	
	public static PPPAnnotationScaner[] annotationsScaner(){
		PPPScanerManager manager = getInstance();
		List<PPPAnnotationScaner> ss = new ArrayList<PPPAnnotationScaner>();
		for (PPPScaner s : manager.scaners.values()) {
			if(s instanceof PPPAnnotationScaner){
				ss.add((PPPAnnotationScaner)s);
			}
		}
		return ss.toArray(new PPPAnnotationScaner[0]);
	}
	
	public static PPPScaner scaner(String scanerName){
		PPPScanerManager manager = getInstance();
		return manager.scaners.get(scanerName);
	}
	
	@SuppressWarnings("unchecked")
	public static <M extends PPPScaner> M scaner(Class<M> clazz){
		PPPScanerManager manager = getInstance();
		for (PPPScaner s : manager.scaners.values()) {
			if(s.getClass() == clazz){
				return (M)s;
			}
		}
		return null;
	}
	
}
