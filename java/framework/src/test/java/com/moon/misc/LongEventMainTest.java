package com.moon.misc;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.moon.dto.LongEvent;
import com.moon.dto.LongEventFactory;
import com.moon.handler.LongEventHandler;
import com.moon.producer.LongEventProducer;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Paul on 2017/2/8.
 */
public class LongEventMainTest {
    @Test
    public void test() throws InterruptedException {
        Executor executor= Executors.newCachedThreadPool();
        LongEventFactory factory=new LongEventFactory();
        int bufferSisze=1024;
        Disruptor<LongEvent> disruptor=new Disruptor<>(factory,bufferSisze,executor);
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer=disruptor.getRingBuffer();
        LongEventProducer producer=new LongEventProducer(ringBuffer);
        ByteBuffer bb=ByteBuffer.allocate(8);
        for(long l=0;true;l++){
            bb.putLong(0,1);
            producer.onData(bb);
            Thread.sleep(1000);
        }
    }

    @Test
    public void test8() throws InterruptedException {
        Executor executor=Executors.newCachedThreadPool();
        int bufferSize=1024;
        Disruptor<LongEvent> disruptor=new Disruptor<>(LongEvent::new,bufferSize,executor);
        disruptor.handleEventsWith((event,sequences,endOfBatch)->{
            System.out.println("Event: "+event.getValue());
        });
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer=disruptor.getRingBuffer();
        LongEventProducer producer=new LongEventProducer(ringBuffer);
        ByteBuffer bb=ByteBuffer.allocate(8);
        for(long l=0;true;l++){
            bb.putLong(0,1);
            ringBuffer.publishEvent((event,sequence,buffer)->event.getValue());
            Thread.sleep(1000);
        }
    }


}
