package com.pipipark.j.system.core.configuration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.pipipark.j.system.core.configuration.exception.PPPConfigPropertiesException;

public class PPPPropertiesConfig implements PPPConfig {
	
	private List<PropertiesConfiguration> configs;
	
	public PPPPropertiesConfig(String path){
		configs = new ArrayList<PropertiesConfiguration>();
		try {
			if(path.toLowerCase().endsWith(".properties")){
				configs.add(new PropertiesConfiguration(path));
			}
		} catch (ConfigurationException e) {
			throw new PPPConfigPropertiesException("could not load properties file by ["+path+"].", e);
		}
	}
	
	@Override
	public String get(String key){
		String result = null;
		for (PropertiesConfiguration propertiesConfiguration : configs) {
			try{
				String value = propertiesConfiguration.getString(key);
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
		for (PropertiesConfiguration propertiesConfiguration : configs) {
			try{
				Integer value = propertiesConfiguration.getInteger(key, null);
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
		for (PropertiesConfiguration propertiesConfiguration : configs) {
			try{
				Long value = propertiesConfiguration.getLong(key, null);
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
		for (PropertiesConfiguration propertiesConfiguration : configs) {
			try{
				Float value = propertiesConfiguration.getFloat(key, null);
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
		for (PropertiesConfiguration propertiesConfiguration : configs) {
			try{
				Double value = propertiesConfiguration.getDouble(key, null);
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
		for (PropertiesConfiguration propertiesConfiguration : configs) {
			try{
				Boolean value = propertiesConfiguration.getBoolean(key, null);
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
		for (PropertiesConfiguration propertiesConfiguration : configs) {
			try{
				List<Object> value = propertiesConfiguration.getList(key, null);
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
