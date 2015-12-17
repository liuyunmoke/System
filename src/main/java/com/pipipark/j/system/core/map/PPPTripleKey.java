package com.pipipark.j.system.core.map;

public class PPPTripleKey<X, Y, Z> {

	private final X x;
	private final Y y;
	private final Z z;

	public PPPTripleKey(X x, Y y, Z z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public X first(){
		return this.x;
	}
	
	public Y middle(){
		return this.y;
	}
	
	public Z last(){
		return this.z;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public final boolean equals(Object o) {
		if (this == o){
			return true;
		}
		if (!(o instanceof PPPTripleKey)){
			return false;
		}
		PPPTripleKey key = (PPPTripleKey) o;
		return x == key.x && y == key.y && z == key.z;
	}
	
	@Override
    public final int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        result = 31 * result + z.hashCode();
        return result;
    }
}
