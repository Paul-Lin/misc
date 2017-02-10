package com.moon.dto;

import com.lmax.disruptor.EventFactory;

/**
 * Created by Paul on 2017/2/8.
 */
public class LongEventFactory implements EventFactory{
    @Override
    public Object newInstance() {
        return new LongEvent();
    }
}
