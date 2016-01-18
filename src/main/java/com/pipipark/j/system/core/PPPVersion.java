package com.pipipark.j.system.core;

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
	public static PPPVersion get(String name){
		//断言name是否空.
		
		PPPVersion version = Enum.valueOf(PPPVersion.class, PPPString.upFirst(name.toLowerCase()));
		//断言version是否空.
		
		return version;
	}
	
	public static void main(String[] args) {
		
	}
}
