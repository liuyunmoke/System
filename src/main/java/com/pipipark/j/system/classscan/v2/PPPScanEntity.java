package com.pipipark.j.system.classscan.v2;

import com.pipipark.j.system.entity.PPPEntity;

@SuppressWarnings("serial")
public class PPPScanEntity extends PPPEntity {
	
	private String name;
	private Class<?> type;
	private Object target;

	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class<?> getType() {
		return type;
	}
	public void setType(Class<?> type) {
		this.type = type;
	}
	@Override
	public void desc(StringBuilder string) throws Exception {
		
	}
}
