package com.moon.producer;

import com.lmax.disruptor.RingBuffer;
import com.moon.dto.LongEvent;

import java.nio.ByteBuffer;

/**
 * Created by Paul on 2017/2/8.
 */
public class LongEventProducer {
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 用于发布事件，每调用一次就发布一次事件
     * 参数会通过事件传递给消费者
     * @param bb
     */
    public void onData(ByteBuffer bb){
        long sequence=ringBuffer.next();
        try{
            // 可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
            LongEvent event=ringBuffer.get(sequence);
            // 用上面的索引取出一个空的事件用于填充
            event.setValue(bb.getLong(0));
        }finally {
            // 发布事件
            ringBuffer.publish(sequence);
        }
    }
}
