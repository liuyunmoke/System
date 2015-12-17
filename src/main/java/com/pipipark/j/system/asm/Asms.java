package com.pipipark.j.system.asm;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class Asms {
	

	    private Asms() {
	    }

	    /**
	     * 
	     * <p>
	     * 比较参数类型是否一致
	     * </p>
	     * 
	     * @param types
	     *            asm的类型({@link Type})
	     * @param clazzes
	     *            java 类型({@link Class})
	     * @return
	     */
	    @SuppressWarnings("unused")
		private static boolean sameType(Type[] types, Class<?>[] clazzes) {
	        // 个数不同
	        if (types.length != clazzes.length) {
	            return false;
	        }

	        for (int i = 0; i < types.length; i++) {
	            if (!Type.getType(clazzes[i]).equals(types[i])) {
	                return false;
	            }
	        }
	        return true;
	    }

	    /**
	     * 
	     * <p>
	     * 获取方法的参数名
	     * </p>
	     * 
	     * @return
	     */
	    public static String[] getMethodParameterNamesByAsm4(Class<?> clazz, final Method method) {  
	        final Class<?>[] parameterTypes = method.getParameterTypes();  
	        if (parameterTypes == null || parameterTypes.length == 0) {  
	            return null;  
	        }  
	        final Type[] types = new Type[parameterTypes.length];  
	        for (int i = 0; i < parameterTypes.length; i++) {  
	            types[i] = Type.getType(parameterTypes[i]);  
	        }  
	        final String[] parameterNames = new String[parameterTypes.length];  
	  
	        String className = clazz.getName();  
	        int lastDotIndex = className.lastIndexOf(".");  
	        className = className.substring(lastDotIndex + 1) + ".class";  
	        InputStream is = clazz.getResourceAsStream(className);  
	        try {  
	            ClassReader classReader = new ClassReader(is);  
	            classReader.accept(new ClassVisitor(Opcodes.ASM4) {  
	                @Override  
	                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {  
	                    // 只处理指定的方法  
	                    Type[] argumentTypes = Type.getArgumentTypes(desc);  
	                    if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {  
	                        return null;  
	                    }  
	                    return new MethodVisitor(Opcodes.ASM4) {  
	                        @Override  
	                        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {  
	                            // 静态方法第一个参数就是方法的参数，如果是实例方法，第一个参数是this  
	                            if (Modifier.isStatic(method.getModifiers())) {
	                            	try{
	                            		 parameterNames[index] = name;
	                            	}catch(Exception e){
	                            	}
	                            }  
	                            else if (index > 0) {  
	                            	try{
	                            		parameterNames[index - 1] = name;
	                            	}catch(ArrayIndexOutOfBoundsException e){}
	                            }  
	                        }  
	                    };  
	  
	                }  
	            }, 0);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return parameterNames;  
	    } 
	    

	    public static void main(String[] args) throws SecurityException,
	            NoSuchMethodException {
	        String[] s = getMethodParameterNamesByAsm4(Asms.class, Asms.class.getMethod(
	                "getMethodParameterNamesByAsm4", Class.class ,Method.class));
	        System.out.println(Arrays.toString(s));

	        s = getMethodParameterNamesByAsm4(Asms.class, Asms.class.getDeclaredMethod("sameType",
	                Type[].class, Class[].class));
	        System.out.println(Arrays.toString(s));

	        s = getMethodParameterNamesByAsm4(Asms.class, Asms.class.getDeclaredMethod("visitAnnotation",
	                String.class,Boolean.TYPE));
	        System.out.println(Arrays.toString(s));

	        // 对String，Object，thread等jdk自带类型不起作用
	    }
}
