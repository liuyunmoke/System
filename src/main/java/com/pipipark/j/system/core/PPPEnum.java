package com.pipipark.j.system.core;

import com.pipipark.j.system.IPPPark;

@SuppressWarnings("serial")
public abstract class PPPEnum implements IPPPark {

	public enum HttpMethod{
		get,
		post,
		head,
		put,
		move,
		copy,
		lock,
		options,
		trace,
		delete;
	}
}
