package com.sumengnan.disruptor.producer;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.sumengnan.disruptor.event.LongEvent;

/**
 * LongEvent事件生产者，Disruptor提供另外一种形式的调用来简化事件生产者的操作，并确保 publish 总是得到调用。
 */
public class LongEventProducerWithTranslator {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void produceData(long value) {
        ringBuffer.publishEvent(TRANSLATOR, value);
    }

    // 使用EventTranslator, 封装获取Event的过程
    private static final EventTranslatorOneArg<LongEvent, Long> TRANSLATOR = new EventTranslatorOneArg<LongEvent, Long>() {

        @Override
        public void translateTo(LongEvent event, long sequeue, Long value) {
            event.setValue(value);
        }
    };
}