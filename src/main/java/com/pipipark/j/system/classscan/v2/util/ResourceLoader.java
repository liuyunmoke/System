package com.pipipark.j.system.classscan.v2.util;



public interface ResourceLoader {

	String CLASSPATH_URL_PREFIX = ResourceUtils.CLASSPATH_URL_PREFIX;


	Resource getResource(String location);

	ClassLoader getClassLoader();
}
