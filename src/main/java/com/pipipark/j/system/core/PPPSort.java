package com.pipipark.j.system.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.pipipark.j.system.core.index.IndexAscComparator;
import com.pipipark.j.system.core.index.IndexDescComparator;

public abstract class PPPSort {
	
	public final static void desc(Collection<?> arr){
		Collections.sort(Arrays.asList(arr.toArray()), new IndexDescComparator());
	}
	
	public final static void asc(Collection<?> arr){
		Collections.sort(Arrays.asList(arr.toArray()), new IndexAscComparator());
	}
}
