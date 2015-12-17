package com.pipipark.j.system.classscan.v2;

import com.pipipark.j.system.entity.PPPEntity;

@SuppressWarnings("serial")
public class PPPScanEntity extends PPPEntity {
	
	private String name;
	private Class<?> clazz;
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
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}


	@Override
	public void desc(StringBuilder string) throws Exception {
		
	}
}
