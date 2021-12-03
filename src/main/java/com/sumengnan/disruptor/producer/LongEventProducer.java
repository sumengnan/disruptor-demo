package com.sumengnan.disruptor.producer;
import com.lmax.disruptor.RingBuffer;
import com.sumengnan.disruptor.event.LongEvent;

/**
 * LongEvent事件生产者，生产LongEvent事件
 */
public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void produceData(long value) {
        long sequence = ringBuffer.next(); // 获得下一个Event槽的下标
        try {
            // 给Event填充数据
            LongEvent event = ringBuffer.get(sequence);
            event.setValue(value);
        } finally {
            // 发布Event，激活观察者去消费， 将sequence传递给该消费者
            // 注意，最后的 ringBuffer.publish() 方法必须包含在 finally 中以确保必须得到调用；如果某个请求的 sequence 未被提交，将会堵塞后续的发布操作或者其它的 producer。
            ringBuffer.publish(sequence);
        }
    }
}