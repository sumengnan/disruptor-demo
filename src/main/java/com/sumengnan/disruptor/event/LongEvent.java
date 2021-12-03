package com.sumengnan.disruptor.event;
import java.io.Serializable;

/**
 * 定义事件数据，本质是个普通JavaBean
 */
@SuppressWarnings("serial")
public class LongEvent implements Serializable {

    private long value;

    public LongEvent() {
        super();
    }

    public LongEvent(long value) {
        super();
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongEvent [value=" + value + "]";
    }
}