package com.pipipark.j.system.core.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.pipipark.j.system.core.map.exception.PPPDoubleMapKeyNullException;
import com.pipipark.j.system.entity.SimplePPPEntity;

@SuppressWarnings("serial")
public class PPPDoubleMap<K, KK, V> extends SimplePPPEntity {

	private final Map<PPPDoubleKey<K, KK>, V> _map = new HashMap<PPPDoubleKey<K, KK>, V>();
	
	public static <K,KK,V> PPPDoubleMap<K,KK,V> create(){
		return new PPPDoubleMap<K,KK,V>();
	}

	public V put(K k, KK kk, V value) {
		if (k == null || kk == null) {
			throw new PPPDoubleMapKeyNullException();
		}
		PPPDoubleKey<K, KK> key = new PPPDoubleKey<K, KK>(k, kk);
		return _map.put(key, value);
	}
	
	public void putAll(PPPDoubleMap<K, KK, V> m){
		if(m==null){
			return;
		}
		this._map.putAll(m._map);
	}

	public V get(K k, KK kk) {
		if (k == null || kk == null) {
			return null;
		}
		PPPDoubleKey<K, KK> key = new PPPDoubleKey<K, KK>(k, kk);
		return _map.get(key);
	}

	public V remove(K k, KK kk) {
		if (k == null || kk == null) {
			return null;
		}
		PPPDoubleKey<K, KK> key = new PPPDoubleKey<K, KK>(k, kk);
		return _map.remove(key);
	}

	public Set<PPPDoubleKey<K, KK>> keySet() {
		return _map.keySet();
	}

	public Collection<V> values() {
		return _map.values();
	}

	public int size() {
		return _map.size();
	}

	public boolean isEmpty() {
		return 0 == _map.size();
	}
	
	public int clear() {
		int size = _map.size();
		_map.clear();
		return size;
    }
	
	public boolean containsKey(K k, KK kk) {
		if (k == null || kk == null) {
			return false;
		}
		PPPDoubleKey<K, KK> key = new PPPDoubleKey<K, KK>(k, kk);
		return _map.containsKey(key);
    }
	
	public boolean containsValue(V value) {
		return _map.containsValue(value);
    }
	
	public static void main(String[] args) {
		PPPDoubleMap<String, Integer, Integer> map = PPPDoubleMap.create();
		map.put("", 1, 2);
		PPPDoubleMap<String, Integer, Integer> map2 = PPPDoubleMap.create();
		map2.put("", 2, 3);
//		Map<String,String> mm = new HashMap<String, String>();
//		mm.putAll(m);
		System.out.println(map.size());
	}
}
