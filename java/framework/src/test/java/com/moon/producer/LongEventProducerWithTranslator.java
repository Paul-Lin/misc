package com.moon.producer;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.moon.dto.LongEvent;

import java.nio.ByteBuffer;

/**
 * Created by Paul on 2017/2/8.
 */
public class LongEventProducerWithTranslator {


    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb) {
        ringBuffer.publishEvent((LongEvent longEvent, long l, ByteBuffer byteBuffer) -> {
            longEvent.setValue(byteBuffer.getLong(0));
        }, bb);
    }
}
