package com.pipipark.j.system.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.pipipark.j.system.IPPPark;
import com.pipipark.j.system.core.configuration.PPPConfig;
import com.pipipark.j.system.core.configuration.PPPPropertiesConfig;
import com.pipipark.j.system.core.configuration.PPPXmlConfig;


/***
 * 配置文件读取工具.
 * @author pipipark:cwj
 *
 */
@SuppressWarnings("serial")
public abstract class PPPConfiguration implements IPPPark {
	
	private static ConcurrentMap<String, PPPConfig> _configs = new ConcurrentHashMap<String, PPPConfig>();
	
	/***
	 * properties文件读取器.
	 */
	public static abstract class Properties{
		public static final PPPConfig path(String path){
			if(!path.startsWith("/")){
				path="/"+path;
			}
			path = path.toLowerCase().trim();
			String key = PPPPropertiesConfig.class.getName()+":"+path;
			if(!_configs.containsKey(key)){
				_configs.put(key, new PPPPropertiesConfig(path));
			}
			return _configs.get(key);
		}
		
		public static void path(){
			path("/");
		}
		
	}
	
	
	/***
	 * xml文件读取器.
	 */
	public static abstract class Xml{
		
		public static final PPPConfig path(String path){
			if(!path.startsWith("/")){
				path="/"+path;
			}
			path = path.toLowerCase().trim();
			String key = PPPXmlConfig.class.getName()+":"+path;
			if(!_configs.containsKey(key)){
				_configs.put(key, new PPPXmlConfig(path));
			}
			return _configs.get(key);
		}
		
		public static void path(){
			path("/");
		}
		
	}
	
}
