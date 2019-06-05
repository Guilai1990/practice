package com.bruce.producerConsumer;

import java.util.concurrent.BlockingDeque;

/**
 * @Author: Bruce
 * @Date: 2019/6/4 17:36
 * @Version 1.0
 */
public interface WorkStealingEnabledChannel<P> extends Channel<P> {

    P take(BlockingDeque<P> preferredQueue) throws InterruptedException;
}
