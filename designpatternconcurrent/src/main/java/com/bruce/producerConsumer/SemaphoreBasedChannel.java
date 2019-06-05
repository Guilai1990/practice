package com.bruce.producerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * @Author: Bruce
 * @Date: 2019/6/4 17:07
 * @Version 1.0
 */
public class SemaphoreBasedChannel<P> implements Channel<P> {

    private final BlockingQueue<P> queue;
    private final Semaphore semaphore;

    public SemaphoreBasedChannel(BlockingQueue<P> queue, int flowLimit) {
        this.queue = queue;
        this.semaphore = new Semaphore(flowLimit);
    }

    @Override
    public P take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public void put(P product) throws InterruptedException {
        semaphore.acquire();
        try {
            queue.put(product);
        } finally {
            semaphore.release();
        }
    }
}
