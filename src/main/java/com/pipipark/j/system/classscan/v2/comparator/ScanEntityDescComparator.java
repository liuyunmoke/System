package com.pipipark.j.system.classscan.v2.comparator;

import com.pipipark.j.system.classscan.v2.PPPScanEntity;
import com.pipipark.j.system.core.index.IndexDescComparator;

@SuppressWarnings("serial")
public class ScanEntityDescComparator extends IndexDescComparator {
	
	@Override
	public int compare(Object a, Object b) {
		PPPScanEntity ae = (PPPScanEntity)a;
		PPPScanEntity ab = (PPPScanEntity)b;
		return super.compare(ae.getClazz(), ab.getClazz());
	}
}
