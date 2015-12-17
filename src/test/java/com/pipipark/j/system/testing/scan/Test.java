package com.pipipark.j.system.testing.scan;

import com.pipipark.j.system.classscan.v2.PPPScan;
import com.pipipark.j.system.classscan.v2.PPPScaner;
import com.pipipark.j.system.classscan.v2.PPPScanerManager;
import com.pipipark.j.system.core.PPPLogger;

public class Test {

	public static void main(String[] args) {
		PPPScan.scaner("com");
		PPPScaner<?>[] ss = PPPScanerManager.allScaner();
		for (PPPScaner<?> p : ss) {
			PPPLogger.debug(p.getClass().getName());
		}
	}

}
