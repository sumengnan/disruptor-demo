package com.sumengnan.disruptor.factory;
import com.lmax.disruptor.EventFactory;
import com.sumengnan.disruptor.event.LongEvent;

/**
 * 定义事件工厂，实例化LongEvent事件
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    public LongEvent newInstance() {
        return new LongEvent();
    }
}