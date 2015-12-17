package com.pipipark.j.system.entity.event;

import com.pipipark.j.system.listener.PPPEvent;

/***
 * 实体类缓存(一级缓存)回滚事件接口,
 * 出现异常等情况,从实体缓存中回滚数据到实体属性时触发.
 * @author pipipark:cwj
 */
public interface EntityRebackEvent extends PPPEvent {

}
