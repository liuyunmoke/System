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

@SuppressWarnings({"serial"})
public abstract class PPPScaner<T> extends PPPEntity implements IPPPark {
	
	private Set<PPPScanEntity> _list = new TreeSet<PPPScanEntity>(new ScanEntityAscComparator());
//	private Set _list = new TreeSet();
	private Type _scanerType = null;
	
	void set(Class<?> clazz) {
//		Object obj = create(cls);
//		if(obj!=null){
//			PPPLogger.info("scan object: "+obj.getClass().getName());
//			if(obj instanceof PPPInitMethod){
//				PPPInitMethod initObj = (PPPInitMethod)obj;
//				initObj.init_method(obj);
//			}
//			_list.add(obj);
//		}
		if(clazz != null){
			PPPLogger.info("scan target class: "+clazz.getName());
//			Object target = create(clazz);
//			if(target==null){
//				return;
//			}
//			PPPScanerManager.exist(clazz, target);
			
			
			PPPScanEntity entity = new PPPScanEntity();
			entity.setName(PPPString.aliasName(clazz));
			entity.setClazz(clazz);
			config(entity);
			_list.add(entity);
		}
	}
	
	public List<Class<?>> list(){
		List<Class<?>> list = new ArrayList<Class<?>>();
		for (PPPScanEntity entity : _list) {
			list.add(entity.getClazz());
		}
		return list;
	}
	
	public Set<?> data(){
		for (PPPScanEntity entity : _list) {
			if(entity.getTarget()==null){
				Object target = target(entity);
				entity.setTarget(target);
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
	
	protected void config(PPPScanEntity entity){
	}
	
	protected Object target(PPPScanEntity entity){
		try {
			return entity.getClazz().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			PPPLogger.error("Create scan object happend Exeption!", e);
			return null;
		}
	}
	
	@Override
	public void desc(StringBuilder string) throws Exception {
		
	}
}
