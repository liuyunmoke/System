package com.pipipark.j.system.classscan.v2.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.pipipark.j.system.IPPPark;
import com.pipipark.j.system.core.PPPLogger;

/**
 * 类查找辅助工具 ClassLoader.
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public final class ClassesLoader extends ClassLoader implements IPPPark {
	
	@SuppressWarnings("deprecation")
	protected Class<?> loadClass(String fileName, InputStream in) {
		Class<?> result = null;
        try {
            List<Byte> l = new ArrayList<Byte>();
            int c;
            while ((c = in.read()) != -1) {
                l.add(new Byte((byte)c));
            }
            byte[] b = new byte[l.size()];
            for (int i = 0; i < l.size(); i++) {
                b[i] = l.get(i).byteValue();
            }
            result = defineClass(b, 0, b.length);
        } catch (Throwable e) {
        	try {
        		result = this.findClass(fileName);
			} catch (ClassNotFoundException e1) {
				PPPLogger.debug(this, "Class "+fileName+" can't be found!");
			}
        }
        if(result==null){
        	PPPLogger.debug(this, "Class "+fileName+" can't be found!");
        }
        return result;
    }
}
