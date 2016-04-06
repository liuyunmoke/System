package com.pipipark.j.system.core.map;

public class PPPDoubleKey<K, KK> {

	private final K k;
	private final KK kk;

	public PPPDoubleKey(K k, KK kk) {
		this.k = k;
		this.kk = kk;
	}
	
	public K first(){
		return this.k;
	}
	
	public KK last(){
		return this.kk;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public final boolean equals(Object o) {
		if (this == o){
			return true;
		}
		if (!(o instanceof PPPDoubleKey)){
			return false;
		}
		PPPDoubleKey key = (PPPDoubleKey) o;
		boolean kBool = false;
		if(k instanceof String){
			kBool = k.equals(key.k);
		}else{
			kBool = k == key.k;
		}
		boolean kkBool = false;
		if(kk instanceof String){
			kkBool = kk.equals(key.kk);
		}else{
			kkBool = kk == key.kk;
		}
		return kBool && kkBool;
	}
	
	@Override
    public final int hashCode() {
        int result = k.hashCode();
        result = 31 * result + kk.hashCode();
        return result;
    }
}
