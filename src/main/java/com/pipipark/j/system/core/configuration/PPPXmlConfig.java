package com.pipipark.j.system.core.configuration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.pipipark.j.system.core.configuration.exception.PPPConfigXmlException;

public class PPPXmlConfig implements PPPConfig{
	
	private List<XMLConfiguration> configs;
	
	public PPPXmlConfig(String path){
		configs = new ArrayList<XMLConfiguration>();
		try {
			if(path.toLowerCase().endsWith(".xml")){
				configs.add(new XMLConfiguration(path));
			}
		} catch (ConfigurationException e) {
			throw new PPPConfigXmlException("could not load xml file by ["+path+"].", e);
		}
	}

	@Override
	public String get(String key){
		String result = null;
		for (XMLConfiguration xmlConfiguration : configs) {
			try{
				String value = xmlConfiguration.getString(key);
				if(value!=null){
					result = value;
					break;
				}
			}catch(Exception e){
				continue;
			}
		}
		return result;
	}
	
	@Override
	public Integer getInteger(String key){
		Integer result = null;
		for (XMLConfiguration xmlConfiguration : configs) {
			try{
				Integer value = xmlConfiguration.getInteger(key, null);
				if(value!=null){
					result = value;
					break;
				}
			}catch(Exception e){
				continue;
			}
		}
		return result;
	}
	
	@Override
	public Long getLong(String key){
		Long result = null;
		for (XMLConfiguration xmlConfiguration : configs) {
			try{
				Long value = xmlConfiguration.getLong(key, null);
				if(value!=null){
					result = value;
					break;
				}
			}catch(Exception e){
				continue;
			}
		}
		return result;
	}
	
	@Override
	public Float getFloat(String key){
		Float result = null;
		for (XMLConfiguration xmlConfiguration : configs) {
			try{
				Float value = xmlConfiguration.getFloat(key, null);
				if(value!=null){
					result = value;
					break;
				}
			}catch(Exception e){
				continue;
			}
		}
		return result;
	}
	
	@Override
	public Double getDouble(String key){
		Double result = null;
		for (XMLConfiguration xmlConfiguration : configs) {
			try{
				Double value = xmlConfiguration.getDouble(key, null);
				if(value!=null){
					result = value;
					break;
				}
			}catch(Exception e){
				continue;
			}
		}
		return result;
	}
	
	@Override
	public Boolean getBoolean(String key){
		Boolean result = null;
		for (XMLConfiguration xmlConfiguration : configs) {
			try{
				Boolean value = xmlConfiguration.getBoolean(key, null);
				if(value!=null){
					result = value;
					break;
				}
			}catch(Exception e){
				continue;
			}
		}
		return result;
	}

	@Override
	public List<Object> getList(String key) {
		List<Object> result = null;
		for (XMLConfiguration xmlConfiguration : configs) {
			try{
				List<Object> value = xmlConfiguration.getList(key, null);
				if(result==null){
					result = value;
				}else{
					result.addAll(value);
				}
			}catch(Exception e){
				continue;
			}
		}
		return result;
	}
	
}
