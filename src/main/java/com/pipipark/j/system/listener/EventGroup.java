package com.pipipark.j.system.listener;

import java.util.Vector;

import com.pipipark.j.system.IPPPark;

/***
 * 事件组,
 * 保存同一类型的所有事件对象.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class EventGroup<T extends PPPEvent> extends Vector<T> implements IPPPark {


	public void addEvent(T event){
		this.add(event);
	}
	
	public void removeEvent(T event){
		this.remove(event);
	}
	
	public void clearEvents(){
		this.clear();
	}
	
	public void fireEvent(T event){
		if(!this.contains(event)){
			return;
		}
		for (PPPEvent e : this) {
			if(event==e){
				e.fireEvent();
				break;
			}
		}
	}
	
	public void fireAllEvents(){
		for (PPPEvent e : this) {
			e.fireEvent();
		}
	}
}
