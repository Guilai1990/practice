package com.bruce.guardedSuspension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Bruce
 * @Date: 2019/5/30 23:08
 * @Version 1.0
 */
public class ConditionVarBlocker implements Blocker {

    private Logger LOG = LoggerFactory.getLogger(ConditionVarBlocker.class);
    private final Lock lock;
    private final Condition condition;
    private final boolean allowAccess2Lock;

    public ConditionVarBlocker(Lock lock) {
        this(lock, true);
    }

    private ConditionVarBlocker(Lock lock, boolean allowAccess2Lock) {
        this.lock = lock;
        this.allowAccess2Lock = allowAccess2Lock;
        this.condition = lock.newCondition();
    }

    public ConditionVarBlocker() {
        this(false);
    }

    public ConditionVarBlocker(boolean allowAccess2Lock) {
        this(new ReentrantLock(), allowAccess2Lock);
    }

    public Lock getLock() {
        if (allowAccess2Lock) {
            return this.lock;
        }
        throw new IllegalStateException("Access to the lock disallowed.");
    }

    @Override
    public <V> V callWithGuarded(GuardedAction<V> guardedAction) throws Exception {
        lock.lockInterruptibly();
        V result;
        try {
            final Predicate guard = guardedAction.guard;
            while (!guard.evaluate()) {
                LOG.info("waiting...");
                condition.await();
            }
            result = guardedAction.call();
            return result;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void signalAfter(Callable<Boolean> stateOperation) throws Exception {
        lock.lockInterruptibly();
        try {
            if (stateOperation.call()) {
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void signal() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void broadcastAfter(Callable<Boolean> stateOperation) throws Exception {
        lock.lockInterruptibly();
        try {
            if (stateOperation.call()) {
                condition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
