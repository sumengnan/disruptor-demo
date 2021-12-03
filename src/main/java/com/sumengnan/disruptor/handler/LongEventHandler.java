package com.sumengnan.disruptor.handler;
import com.lmax.disruptor.EventHandler;
import com.sumengnan.disruptor.event.LongEvent;

/**
 * LongEvent事件消息者，消息LongEvent事件
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("consumer:" + Thread.currentThread().getName() + " Event: value=" + event.getValue() + ",sequence=" + sequence + ",endOfBatch=" + endOfBatch);
    }
}