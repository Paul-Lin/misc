package com.moon.handler;

import com.lmax.disruptor.EventHandler;
import com.moon.dto.LongEvent;

/**
 * Created by Paul on 2017/2/8.
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println(longEvent.getValue());
    }
}
