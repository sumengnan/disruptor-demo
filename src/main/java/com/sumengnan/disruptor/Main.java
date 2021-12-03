package com.sumengnan.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.sumengnan.disruptor.event.LongEvent;
import com.sumengnan.disruptor.factory.LongEventFactory;
import com.sumengnan.disruptor.handler.LongEventHandler;
import com.sumengnan.disruptor.producer.LongEventProducer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();

        // 定义用于事件处理的线程池，Disruptor 通过 java.util.concurrent.ExecutorService 提供的线程来触发 Consumer 的事件处理
        ExecutorService executor = Executors.newCachedThreadPool();
        // 指定事件工厂
        LongEventFactory factory = new LongEventFactory();
        // 指定 ring buffer字节大小，必需为2的N次方(能将求模运算转为位运算提高效率 )，否则影响性能
        int bufferSize = 1024 * 1024;
        // 单线程模式，获取额外的性能
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());
        // 设置事件业务处理器---消费者
        disruptor.handleEventsWith(new LongEventHandler());
        // 启动disruptor线程
        disruptor.start();
        // 获取 ring buffer环，用于接取生产者生产的事件
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        // 为 ring buffer指定事件生产者
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        //LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);
        for (int i = 0; i<100000; i++) {
            producer.produceData(i);// 生产者生产数据
        }
        disruptor.shutdown(); //关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
        executor. shutdown(); //关闭 disruptor 使用的线程池；如果需要的话，必须手动关闭， disruptor 在 shutdown 时不会自动关闭；
        System.out.println(String.format("总共耗时%s毫秒", (System.currentTimeMillis() - beginTime)));
    }
}
