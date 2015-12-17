package com.pipipark.j.system.listener.comparator;

import com.pipipark.j.system.core.PPPComparator;
import com.pipipark.j.system.core.PPPConstant;

/***
 * 事件类比较器,
 * 实现同一类型事件根据索引排序.
 * @see IndexEvent.class
 * @author pipipark:cwj
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class EventComparator implements PPPComparator<Class> {

	@SuppressWarnings("unchecked")
	@Override
	public int compare(Class a, Class b) {
		IndexEvent ai = (IndexEvent)a.getAnnotation(IndexEvent.class);
		IndexEvent bi = (IndexEvent)b.getAnnotation(IndexEvent.class);
		int aInt = PPPConstant.Indexs.DEFAULT_INDEX;
		int bInt = PPPConstant.Indexs.DEFAULT_INDEX;
		if(ai!=null){
			aInt = ai.value();
		}
		if(bi!=null){
			bInt = bi.value();
		}
		if(aInt>bInt){
			return 1;
		}else if(aInt<bInt){
			return -1;
		}
		return 0;
	}

}
