package com.bruce.producerConsumer;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * @Author: Bruce
 * @Date: 2019/6/4 17:35
 * @Version 1.0
 */
public class WorkStealingChannel<T> implements WorkStealingEnabledChannel<T> {

    private final BlockingDeque<T>[] managedQueues;

    public WorkStealingChannel(BlockingDeque<T>[] managedQueues) {
        this.managedQueues = managedQueues;
    }

    @Override
    public T take(BlockingDeque preferredQueue) throws InterruptedException {

        BlockingDeque<T> targetQueue = preferredQueue;
        T product = null;

        // 从指定队列的队首获取产品
        if (null != targetQueue) {
            product = targetQueue.poll();
        }

        int queueIndex = -1;

        while (null == product) {
            queueIndex = (queueIndex + 1) % managedQueues.length;
            targetQueue = managedQueues[queueIndex];
            // 从其他其中一个队列的队尾窃取一个产品
            product = targetQueue.pollLast();
            if (preferredQueue == targetQueue) {
                break;
            }
        }

        if (null == product) {
            queueIndex = (int) (System.currentTimeMillis() % managedQueues.length);
            targetQueue = managedQueues[queueIndex];
            product = targetQueue.takeLast();
            System.out.println("stealed from " + queueIndex + ":" + product);
        }

        return product;
    }

    @Override
    public T take() throws InterruptedException {
        return take(null);
    }

    @Override
    public void put(T product) throws InterruptedException {
        int targetIndex = (product.hashCode() % managedQueues.length);
        BlockingQueue<T> targetQueue = managedQueues[targetIndex];
        targetQueue.put(product);
    }
}
