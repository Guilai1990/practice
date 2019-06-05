package com.bruce.producerConsumer;

import java.util.concurrent.BlockingQueue;

/**
 * @Author: Bruce
 * @Date: 2019/6/4 16:16
 * @Version 1.0
 */
public class BlockingQueueChannel<P> implements Channel<P> {

    private final BlockingQueue<P> queue;

    public BlockingQueueChannel(BlockingQueue<P> queue) {
        this.queue = queue;
    }

    @Override
    public P take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public void put(P product) throws InterruptedException {
        queue.put(product);
    }
}
