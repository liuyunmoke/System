package com.pipipark.j.system.core;

import com.pipipark.j.system.core.exception.PPPVersionException;

/***
 * 版本及其排序.
 * @author pipipark:cwj
 */
public enum PPPVersion {
	
	Rat(1),
	Ox(2),
	Tiger(3),
	Rabbit(4),
	Dragon(5),
	Snake(6),
	Horse(7),
	Goat(8),
	Monkey(9),
	Rooster(10),
	Dog(11),
	Pig(12);

	int _order;
	PPPVersion(int order){
		_order = order;
	}
	public int value(){
		return _order;
	}
	public static PPPVersion get(String name) throws PPPVersionException{
		//断言name是否空.
		if(PPPString.isEmpty(name)){
			throw new IllegalArgumentException("You can't get empty name of vesion.");
		}
		
		PPPVersion version = Enum.valueOf(PPPVersion.class, PPPString.upFirst(name.toLowerCase()));
		//断言version是否空.
		if(version == null){
			throw new PPPVersionException("Version is not found.");
		}
		
		return version;
	}
	
	public static void main(String[] args) {
		
	}
}
