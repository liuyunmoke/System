package com.pipipark.j.system.classscan.v2;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.pipipark.j.system.IPPPark;
import com.pipipark.j.system.classscan.v2.comparator.ScanEntityAscComparator;
import com.pipipark.j.system.classscan.v2.exception.PPPClassScanerError;
import com.pipipark.j.system.core.PPPLogger;
import com.pipipark.j.system.core.PPPString;
import com.pipipark.j.system.entity.PPPEntity;

@SuppressWarnings({"serial","rawtypes","unchecked"})
public abstract class PPPScaner<T> extends PPPEntity implements IPPPark {
	
	private Set<PPPScanEntity> _list = new TreeSet<PPPScanEntity>(new ScanEntityAscComparator());
	private Type _scanerType = null;
	
	void set(Class<?> clazz) {
		if(clazz != null){
			PPPLogger.info("scan target class: "+clazz.getName());
			PPPScanEntity entity = new PPPScanEntity();
			entity.setName(PPPString.aliasName(clazz));
			entity.setType(clazz);
			PPPScanerManager.register(clazz, this);
			_list.add(entity);
		}
	}
	
	public List<PPPScanEntity> list(){
		return new ArrayList<PPPScanEntity>(_list);
	}
	
	public List<Class<?>> types(){
		List<Class<?>> list = new ArrayList<Class<?>>();
		for (PPPScanEntity entity : _list) {
			list.add(entity.getType());
		}
		return list;
	}
	
	public Set<PPPScanEntity> getData(){
		for (PPPScanEntity entity : _list) {
			if(entity.getTarget()==null){
				entity.setTarget(target(entity));
			}
		}
		return _list;
	}

	Type type(){
		if(_scanerType==null){
			Type type = this.getClass().getGenericSuperclass();
			Type[] temp = ((ParameterizedType) type).getActualTypeArguments();
			if(temp==null || temp.length==0){
				throw new PPPClassScanerError("Scaner<T>'s T must be effective Type in Class["+this.getClass().getName()+"]");
			}
			_scanerType = temp[0];
		}
		return _scanerType;
	}
	
	protected Object target(PPPScanEntity entity){
			try {
				Object target = entity.getType().newInstance();
				if(target!=null && target instanceof PPPInitMethod){
					PPPInitMethod initObj = (PPPInitMethod)target;
					initObj.init_method(target);
				}
				return target;
			} catch (InstantiationException | IllegalAccessException e) {
				PPPLogger.error("Create scan object happend Exeption!", e);
				return null;
			}
	}
	
	@Override
	public void desc(StringBuilder string) throws Exception {
		
	}
}
