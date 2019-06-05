package com.bruce.twoPhaseTermination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Bruce
 * @Date: 2019/5/31 0:08
 * @Version 1.0
 */
public abstract class AbstractTerminatableThread extends Thread implements Terminatable {

    final static Logger LOG = LoggerFactory.getLogger(AbstractTerminatableThread.class);

    private final boolean DEBUG = true;

    public final TerminationToken terminationToken;

    public AbstractTerminatableThread() {
        this(new TerminationToken());
    }

    public AbstractTerminatableThread(TerminationToken terminationToken) {
        this.terminationToken = terminationToken;
        terminationToken.register(this);
    }

    protected abstract void doRun() throws Exception;

    protected void doCleanup(Exception cause) {

    }

    protected void doTerminate() {}

    @Override
    public void run() {
        Exception ex = null;
        try {
            for (;;) {
                if (terminationToken.isToShutdown()
                        && terminationToken.reservations.get() <= 0) {
                    break;
                }
                doRun();
            }
        } catch (Exception e) {
            ex = e;
            if (e instanceof InterruptedException) {
                if (DEBUG) {
                    LOG.debug("", e);
                }
            } else {
                LOG.error("", e);
            }
        } finally {
            try {
                doCleanup(ex);
            } finally {
                terminationToken.notifyThreadTermination(this);
            }
        }
    }

    @Override
    public void interrupt() {
        terminate();
    }

    @Override
    public void terminate() {
        terminationToken.setToShutdown(true);
        try {
            doTerminate();
        } finally {
            if (terminationToken.reservations.get() <= 0) {
                super.interrupt();
            }
        }
    }

    public void terminate(boolean waitUtilThreadTerminate) {
        terminate();
        if (waitUtilThreadTerminate) {
            try {
                this.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
