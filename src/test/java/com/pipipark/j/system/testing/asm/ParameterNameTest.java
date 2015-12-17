package com.pipipark.j.system.testing.asm;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.pipipark.j.system.asm.Asms;
import com.pipipark.j.system.core.PPPLogger;

public class ParameterNameTest {
	public void method1(String param1, String param2) {
//		System.out.println(param1 + param2);
	}

	public static void main(String[] args) throws Exception {
		Class<ParameterNameTest> clazz = ParameterNameTest.class;
		Method method = clazz.getDeclaredMethod("method1", String.class,
				String.class);
		String[] parameterNames = Asms.getMethodParameterNamesByAsm4(clazz, method);
		PPPLogger.info(ParameterNameTest.class, Arrays.toString(parameterNames));
//		System.out.println(Arrays.toString(parameterNames));
	}
}
