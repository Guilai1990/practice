package com.bruce.activeObject;

import java.io.Closeable;

/**
 * @Author: Bruce
 * @Date: 2019/6/4 21:44
 * @Version 1.0
 */
public interface RequestPersistence extends Closeable {

    void store(MMSDeliverRequest request);
}
