package com.pipipark.j.system.classscan.v2.util;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;

public class VfsPatternUtils extends VfsUtils {

	public static Object getVisitorAttribute() {
		return doGetVisitorAttribute();
	}

	public static String getPath(Object resource) {
		return doGetPath(resource);
	}

	public static Object findRoot(URL url) throws IOException {
		return getRoot(url);
	}

	public static void visit(Object resource, InvocationHandler visitor) throws IOException {
		Object visitorProxy = Proxy.newProxyInstance(
				VIRTUAL_FILE_VISITOR_INTERFACE.getClassLoader(),
				new Class<?>[] {VIRTUAL_FILE_VISITOR_INTERFACE}, visitor);
		invokeVfsMethod(VIRTUAL_FILE_METHOD_VISIT, resource, visitorProxy);
	}
}
