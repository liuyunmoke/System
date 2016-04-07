package com.pipipark.j.system.listener;

import java.util.HashMap;
import java.util.Map;

public class EventParams {

	private Map<String, Object> attr = new HashMap<String, Object>();

	public Map<String, Object> getAttr() {
		return attr;
	}

	public void setAttr(Map<String, Object> attr) {
		this.attr = attr;
	}
}
