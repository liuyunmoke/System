package com.pipipark.j.system.core.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.pipipark.j.system.core.map.exception.PPPTripleMapKeyNullException;
import com.pipipark.j.system.entity.SimplePPPEntity;

@SuppressWarnings("serial")
public class PPPTripleMap<X, Y, Z, V> extends SimplePPPEntity {

	private Map<PPPTripleKey<X, Y, Z>, V> _map = new HashMap<PPPTripleKey<X, Y, Z>, V>();
	
	public static <X,Y,Z,V> PPPTripleMap<X,Y,Z,V> create(){
		return new PPPTripleMap<X,Y,Z,V>();
	}

	public V put(X x, Y y, Z z, V value) {
		if (x == null || y == null || z == null) {
			throw new PPPTripleMapKeyNullException();
		}
		PPPTripleKey<X, Y, Z> key = new PPPTripleKey<X, Y, Z>(x, y, z);
		return _map.put(key, value);
	}
	
	public void putAll(PPPTripleMap<X, Y, Z, V> m){
		if(m==null){
			return;
		}
		this._map.putAll(m._map);
	}

	public V get(X x, Y y, Z z) {
		if (x == null || y == null || z == null) {
			return null;
		}
		PPPTripleKey<X, Y, Z> key = new PPPTripleKey<X, Y, Z>(x, y, z);
		return _map.get(key);
	}

	public V remove(X x, Y y, Z z) {
		if (x == null || y == null || z == null) {
			return null;
		}
		PPPTripleKey<X, Y, Z> key = new PPPTripleKey<X, Y, Z>(x, y, z);
		return _map.remove(key);
	}

	public Set<PPPTripleKey<X, Y, Z>> keySet() {
		if (_map != null) {
			return _map.keySet();
		}
		return null;
	}

	public Collection<V> values() {
		if (_map != null) {
			return _map.values();
		}
		return null;
	}

	public int size() {
		if (_map == null) {
			return 0;
		}
		return _map.size();
	}

	public boolean isEmpty() {
		if (_map == null) {
			return true;
		}
		return 0 == _map.size();
	}
	
	public int clear() {
		if (_map == null) {
			return 0;
		}
		int size = _map.size();
		_map.clear();
		return size;
    }
	
	public boolean containsKey(X x, Y y, Z z) {
		if (x == null || y == null || z == null) {
			return false;
		}
		PPPTripleKey<X, Y, Z> key = new PPPTripleKey<X, Y, Z>(x,y,z);
		return _map.containsKey(key);
    }
	
	public boolean containsValue(V value) {
		return _map.containsValue(value);
    }
	
	
	public static void main(String[] args) {
		PPPTripleMap<String, Integer, Integer, String> map = PPPTripleMap.create();
		map.put("", 1, 2, "....");
		System.out.println(map.get("", 2, 2));
	}
}
