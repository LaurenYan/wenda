package com.nowcoder.wenda.async;

import java.util.List;

/**
 * Created by ${ywj} on 2018/1/2.
 */
public interface EventHandler {
    //处理event
    void doHandle(EventModel model);
    //关心哪些event
    List<EventType> getSupportEventTypes();
}
