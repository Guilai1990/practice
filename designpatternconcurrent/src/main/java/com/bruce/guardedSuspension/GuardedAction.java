package com.bruce.guardedSuspension;

import java.util.concurrent.Callable;

/**
 * @Author: Bruce
 * @Date: 2019/5/30 21:24
 * @Version 1.0
 */
public abstract class GuardedAction<V> implements Callable<V> {

    protected final Predicate guard;

    public GuardedAction(Predicate guard) {
        this.guard = guard;
    }
}
