package com.bruce.producerConsumer;

/**
 * @Author: Bruce
 * @Date: 2019/6/4 16:10
 * @Version 1.0
 */
public interface Channel<P> {

    P take() throws InterruptedException;

    void put(P product) throws InterruptedException;
}
