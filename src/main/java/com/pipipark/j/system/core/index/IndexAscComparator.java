package com.pipipark.j.system.core.index;

import com.pipipark.j.system.annotation.PPPIndex;
import com.pipipark.j.system.core.PPPComparator;
import com.pipipark.j.system.core.PPPConstant;

/***
 * 内部通用索引比较器.
 * @see Index.class
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class IndexAscComparator implements PPPComparator<Object> {

	@Override
	public int compare(Object a, Object b) {
		PPPIndex ai;
		PPPIndex bi;
		if(a instanceof Class){
			ai = (PPPIndex)((Class<?>)a).getAnnotation(PPPIndex.class);
		}else{
			ai = a.getClass().getAnnotation(PPPIndex.class);
		}
		if(b instanceof Class){
			bi = (PPPIndex)((Class<?>)b).getAnnotation(PPPIndex.class);
		}else{
			bi = b.getClass().getAnnotation(PPPIndex.class);
		}
		int aInt = PPPConstant.Indexs.DEFAULT_INDEX;
		int bInt = PPPConstant.Indexs.DEFAULT_INDEX;
		if(ai!=null){
			aInt = ai.value();
		}
		if(bi!=null){
			bInt = bi.value();
		}
		if(aInt<bInt){
			return -1;
		}
		return 1;
	}

}
