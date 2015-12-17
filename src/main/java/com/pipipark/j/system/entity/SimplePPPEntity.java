package com.pipipark.j.system.entity;

/**
 * PPPEntity默认实现
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class SimplePPPEntity extends PPPEntity {

	@Override
	public void desc(StringBuilder string) {
		string.append(this.getClass().getName()+" entity.");
	}
	
}
