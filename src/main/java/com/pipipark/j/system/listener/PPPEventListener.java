package com.pipipark.j.system.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/***
 * 事件监听者.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class PPPEventListener<T extends PPPEvent> implements PPPListener<T> {
	
	/**
	 * 根据类型保存事件实例对象.
	 */
	private transient ConcurrentMap<Class<T>, EventGroup<T>> events = null;

	
	/**
	 * 添加监听事件.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addListener(final T... es) {
		init();
		for (T event : es) {
			EventGroup<T> eg;
			Class<T> key = (Class<T>)event.getClass();
			if(events.containsKey(key)){
				eg = events.get(key);
			}else{
				eg = new EventGroup<T>();
				events.put(key, eg);
			}
			eg.add(event);
		}
	}
	
	/**
	 * 删除全部监听.
	 */
	@Override
	public void removeAllListener() {
		if(!chenck()){
			return;
		}
		events.clear();
	}
	
	/**
	 * 删除指定监听事件.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void removeListener(T event) {
		if(!chenck()){
			return;
		}
		Class<T> key = (Class<T>)event.getClass();
		EventGroup<T> eg = events.get(key);
		if(eg!=null){
			eg.remove(event);
		}
	}

	/**
	 * 删除某类型的全部监听事件.
	 */
	@Override
	public void removeListener(Class<T> eventType) {
		if(!chenck()){
			return;
		}
		List<Class<T>> list = new ArrayList<Class<T>>();
		for (Iterator<Class<T>> ite = events.keySet().iterator();ite.hasNext();) {
			Class<T> clazz = ite.next();
			if(eventType.isAssignableFrom(clazz)){
				list.add(clazz);
			}
		}
		for (Class<T> clazz : list) {
			events.remove(clazz);
		}
	}
	
	/**
	 * 触发全部监听事件.
	 */
	@Override
	public void fireAllListener() {
		if(!chenck()){
			return;
		}
		for (Class<T> clazz : events.keySet()) {
			EventGroup<T> eg = events.get(clazz);
			eg.fireAllEvents();
		}
	}

	/**
	 * 触发指定监听事件.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void fireListener(T event) {
		if(!chenck()){
			return;
		}
		Class<T> key = (Class<T>)event.getClass();
		EventGroup<T> eg = events.get(key);
		if(eg!=null){
			eg.fireEvent(event);
		}
	}
	
	

	/**
	 * 触发某类型全部监听事件.
	 */
	@Override
	public void fireListener(Class<T> eventType) {
		if(!chenck()){
			return;
		}
		List<EventGroup<T>> list = new ArrayList<EventGroup<T>>();
		for (Iterator<Class<T>> ite = events.keySet().iterator();ite.hasNext();) {
			Class<T> clazz = ite.next();
			if(eventType.isAssignableFrom(clazz)){
				EventGroup<T> eventGroup = events.get(clazz);
				if(eventGroup!=null){
					list.add(eventGroup);
				}
			}
		}
		for (EventGroup<T> eventGroup : list) {
			eventGroup.fireAllEvents();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <M extends T> List<M> getEvents(Class<M> eventType) {
		if(!chenck()){
			return null;
		}
		List<M> list = new ArrayList<M>();
		for (Iterator<Class<T>> ite = events.keySet().iterator();ite.hasNext();) {
			Class<T> clazz = ite.next();
			if(eventType.isAssignableFrom(clazz)){
				EventGroup<M> eventGroup = (EventGroup<M>)events.get(clazz);
				if(eventGroup!=null){
					for (M t : eventGroup) {
						list.add(t);
					}
				}
			}
		}
		return list;
	}


	/**
	 * 初始化events,用来保存所有监听的事件对象.
	 */
	private void init(){
		events = new ConcurrentHashMap<Class<T>, EventGroup<T>>();
	}
	
	/**
	 * 检查events.
	 * @return
	 */
	private boolean chenck(){
		if(events==null || events.isEmpty()){
			return false;
		}
		return true;
	}
}
