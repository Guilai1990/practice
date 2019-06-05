package com.bruce.guardedSuspension;


import java.util.concurrent.Callable;

/**
 * @Author: Bruce
 * @Date: 2019/5/30 21:21
 * @Version 1.0
 */
public interface Blocker {

    <V> V callWithGuarded(GuardedAction<V> guardedAction) throws Exception;

    void signalAfter(Callable<Boolean> stateOperation) throws Exception;

    void signal() throws InterruptedException;

    void broadcastAfter(Callable<Boolean> stateOperation) throws Exception;
}
