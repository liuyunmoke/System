package com.pipipark.j.system.entity.comparator;

import com.pipipark.j.system.core.PPPComparator;
import com.pipipark.j.system.core.PPPConstant;
import com.pipipark.j.system.entity.PPPEntity;

/***
 * 实体类比较器,
 * 实现同一类型实体根据索引排序.
 * @see IndexEntity.class
 * @author pipipark:cwj
 */
@SuppressWarnings("serial")
public class EntityComparator implements PPPComparator<PPPEntity> {

	@Override
	public int compare(PPPEntity a, PPPEntity b) {
		IndexEntity ai = a.getClass().getAnnotation(IndexEntity.class);
		IndexEntity bi = b.getClass().getAnnotation(IndexEntity.class);
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
