package com.pipipark.j.system.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.pipipark.j.system.IPPPark;
import com.pipipark.j.system.core.PPPConstant;
import com.pipipark.j.system.entity.cache.EntityCache;
import com.pipipark.j.system.entity.comparator.IndexEntity;
import com.pipipark.j.system.entity.event.EntityRefreshEvent;
import com.pipipark.j.system.entity.event.EntityRebackEvent;
import com.pipipark.j.system.entity.exception.PPPEntityException;
import com.pipipark.j.system.entity.field.PPPField;
import com.pipipark.j.system.exception.PPPTipsException;
import com.pipipark.j.system.exception.PPPServiceException;
import com.pipipark.j.system.listener.PPPEventListener;

/**
 * 实体抽象类
 * @see 提供json序列化,克隆,自定义组装内容等功能.
 * @author pipipark:cwj
 */
@SuppressWarnings({"rawtypes","serial"})
public abstract class PPPEntity extends PPPEventListener implements IPPPark,Comparable<PPPEntity> {


	/*
	 * gson构建对象
	 */
	private transient Gson _gson;
	
	/*
	 * install工具
	 */
	private transient StringBuilder _string = new StringBuilder();
	
	/*
	 * 实体一级缓存,保存每次refresh之前的数据备份
	 */
	private transient EntityCache _cache = new EntityCache();
	
	
	/**
	 * 把数据刷新到缓存EntityCache,并触发刷新事件.
	 * @throws PPPEntityException 
	 */
	@SuppressWarnings("unchecked")
	public void refresh() throws PPPEntityException{
		if(_cache.isEmpty()){
			List<PPPField> fs = fields();
			for (PPPField f : fs) {
				_cache.put(f.name(), f.value());
			}
		}else{
			for (Iterator<String> ite = _cache.keySet().iterator();ite.hasNext();) {
				String fieldName = ite.next();
				Object val = this.fieldValue(fieldName);
				Object cacheVal = _cache.get(fieldName);
				if(cacheVal == val || cacheVal.equals(val)){
					continue;
				}
				_cache.put(fieldName, val);
			}
		}
		
		fireListener(EntityRefreshEvent.class);
	}
	
	/**
	 * 从EntityCache缓存回滚数据,并触发回滚事件.
	 * @throws PPPEntityException 
	 */
	@SuppressWarnings("unchecked")
	public void reback() throws PPPEntityException{
		
		fireListener(EntityRebackEvent.class);
	}

	/**
	 * gson.
	 * @return String
	 */
	public String json() {
		return gson().toJson(this);
	}
	
	/**
	 * gson,是否缓存获取.
	 * @return String
	 */
	public String Json(boolean cache) {
		if(cache){
			gson().toJson(this._cache);
		}
		return gson().toJson(this);
	}

	/**
	 * 获得该字段的序列化名称.
	 * 
	 * @param fieldName
	 * @throws PPPEntityException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */
	public String serializedName(String fieldName) throws PPPEntityException {
		Field field;
		try {
			field = getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			SerializedName sn = field.getAnnotation(SerializedName.class);
			field.setAccessible(false);
			if (sn != null) {
				return sn.value();
			}
		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException e) {
			throw new PPPEntityException("Get field serializedName Exception!", e);
		}
		return fieldName;
	}

	/**
	 * 赋值.
	 * 
	 * @param fieldName
	 * @param value
	 * @throws PPPEntityException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void fieldValue(String fieldName, Object value) throws PPPEntityException {
		try {
			Field field = getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(this, value);
			field.setAccessible(false);
		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException | IllegalAccessException e) {
			throw new PPPEntityException("Set field value Exception!", e);
		}
	}

	/**
	 * 泛型取值.
	 * 
	 * @param fieldName
	 * @param clazz
	 * @return <M>
	 * @throws PPPEntityException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public <M> M fieldValue(String fieldName, Class<M> clazz)
			throws PPPEntityException {
		try {
			Field field = getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			M m = (M) field.get(this);
			field.setAccessible(false);
			return m;
		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException | IllegalAccessException e) {
			throw new PPPEntityException("Get field value Exception!", e);
		}
	}

	/**
	 * 根据序列化名称取值,泛型.
	 * 
	 * @param serializedName
	 * @param clazz
	 * @return <M>
	 * @throws PPPEntityException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */
	public <M> M serializedFieldValue(String serializedName, Class<M> clazz)
			throws PPPEntityException {
		try {
			Field[] fields = getClass().getDeclaredFields();
			for (Field f : fields) {
				SerializedName sn = f.getAnnotation(SerializedName.class);
				if (sn != null && sn.value().equals(serializedName)) {
					return fieldValue(f.getName(), clazz);
				}
			}
			return null;
		} catch (SecurityException | IllegalArgumentException e) {
			throw new PPPEntityException("Get field value Exception!", e);
		}
	}

	/**
	 * 取值.
	 * 
	 * @param fieldName
	 * @return Object
	 * @throws PPPEntityException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Object fieldValue(String fieldName) throws PPPEntityException {
		try {
			Field field = getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			Object val = field.get(this);
			field.setAccessible(false);
			return val;
		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException | IllegalAccessException e) {
			throw new PPPEntityException("Get field value Exception!", e);
		}
	}

	/**
	 * 取值,泛型.
	 * 
	 * @param serializedName
	 * @return Object
	 * @throws PPPEntityException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */
	public Object serializedFieldValue(String serializedName)
			throws PPPEntityException {
		try {
			Field[] fields = getClass().getDeclaredFields();
			for (Field f : fields) {
				SerializedName sn = f.getAnnotation(SerializedName.class);
				if (sn != null && sn.value().equals(serializedName)) {
					return fieldValue(f.getName());
				}
			}
			return null;
		} catch (SecurityException | IllegalArgumentException e) {
			throw new PPPEntityException("Get field value Exception!", e);
		}
	}

	/**
	 * 克隆对象.
	 * @return 
	 * 
	 * @return PPPEntity
	 * @throws PPPEntityException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public <M extends PPPEntity> M cloneEntity() throws PPPEntityException {
		ByteArrayOutputStream byteOut;
		ObjectOutputStream out;
		M entity = null;
		try {
			byteOut = new ByteArrayOutputStream();
			out = new ObjectOutputStream(byteOut);
			out.writeObject(this);
			ByteArrayInputStream byteIn = new ByteArrayInputStream(
					byteOut.toByteArray());
			ObjectInputStream in = new ObjectInputStream(byteIn);
			entity = (M) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new PPPEntityException("Clone entity Exception!", e);
		}
		_init(this,entity);
		return entity;
	}
	
	/**
	 * 克隆实体,对应内部初始化.
	 * @param PPPEntity from
	 * @param PPPEntity target
	 */
	private void _init(PPPEntity entity, PPPEntity target){
		target.gson();
		target._string = new StringBuilder();
		target._string.append(entity._string.toString());
		target._cache = new EntityCache();
		for (Iterator<String> ite = entity._cache.keySet().iterator();ite.hasNext();) {
			String fieldName = ite.next();
			target._cache.put(fieldName, entity._cache.get(fieldName));
		}
	}

	/**
	 * 重写toString方法,输出组装后的字符串.
	 * @return String
	 * @throws PPPTipsException
	 */
	@Override
	public String toString() {
		if (_string != null && _string.length() > 0) {
			_string.delete(0, _string.length());
		}
		try {
			desc(_string);
		} catch (Exception e) {
			throw new PPPTipsException("Install entity happen Exception!");
		}
//		if (_string == null || _string.toString().trim().length() <= 0) {
//			throw new PPPTipsException("Install entity's property \"string\" can't be empty!");
//		}
		return _string.toString();
	}

	/**
	 * 返回MD5加密值.
	 * 
	 * @param fieldName
	 * @return String
	 * @throws PPPEntityException
	 * @throws NoSuchAlgorithmException
	 */
	public String md5(String fieldName) throws PPPEntityException {
		try {
			Object val = this.fieldValue(fieldName);
			if (val == null) {
				return "";
			}
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(val.toString().getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new PPPEntityException("Md5 hex happen Exception!");
		}
	}
	
	/**
	 * 实例化实体内部gson对象.
	 * @return Gson
	 */
	public Gson gson(){
		if(_gson==null){
			_gson = new GsonBuilder()
//			.setPrettyPrinting()
			.enableComplexMapKeySerialization()
			.setDateFormat("yyyy-MM-dd HH:mm:ss")
//			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.create();
		}
		return _gson;
	}
	
	
	/**
	 * 返回实体的字段集合
	 * @return List<PPPField>
	 * @see PPPField
	 * @throws PPPEntityException
	 * @throws IllegalArgumentException
	 */
	public List<PPPField> fields() throws PPPEntityException{
		List<PPPField> list = new ArrayList<PPPField>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			if(Modifier.isStatic(field.getModifiers())){
				continue;
			}
			try {
				PPPField f = field(field.getName());
				if(f!=null){
					list.add(f);
				}
			} catch (IllegalArgumentException e) {
				throw new PPPEntityException("Refresh cache happen one reflect Exception!", e);
			}
		}
		return list;
	}
	
	/**
	 * 返回json对应的map对象.
	 * @return Map<String, Object>
	 */
	public Map<String, Object> jsonMap(){
		return gson().fromJson(json(), new TypeToken<Map<String, Object>>(){}.getType());
	}
	
	/**
	 * 返回某字段
	 * @param name
	 * @return PPPField
	 * @throws PPPEntityException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public PPPField field(String name) throws PPPEntityException{
		PPPField pf = null;
		try {
			pf = PPPField.newInstance(this.getClass(), name);
		} catch (PPPServiceException e) {
			throw new PPPEntityException("PPPField can't got Exception!", e);
		}
		return pf;
	}
	
	/**
	 * 描述信息,子类实现.
	 * @throws Exception
	 */
	public abstract void desc(StringBuilder string) throws Exception;
	

	/**
	 * 比较器,index越小排序越靠前.
	 * @return int(-1,1,0)
	 */
	@Override
	public int compareTo(PPPEntity target) {
		IndexEntity mineBi = this.getClass().getAnnotation(IndexEntity.class);
		IndexEntity targetBi = target.getClass().getAnnotation(IndexEntity.class);
		int mineInt = PPPConstant.Indexs.DEFAULT_INDEX;
		int targetInt = PPPConstant.Indexs.DEFAULT_INDEX;
		
		if(mineBi!=null){
			mineInt = mineBi.value();
		}
		if(targetBi!=null){
			targetInt = targetBi.value();
		}
		if(mineInt>targetInt){
			return 1;
		}else if(mineInt<targetInt){
			return -1;
		}
		return 0;
	}

}
