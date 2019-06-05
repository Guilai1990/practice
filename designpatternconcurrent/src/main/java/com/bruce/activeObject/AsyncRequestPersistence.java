package com.bruce.activeObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: Bruce
 * @Date: 2019/6/4 21:43
 * @Version 1.0
 */
public class AsyncRequestPersistence implements RequestPersistence {

    private static final long ONE_MINUTE_IN_SECONDS = 60;
    final static Logger logger = LoggerFactory.getLogger(AsyncRequestPersistence.class);
    final AtomicLong taskTimeConsumedPerInterval = new AtomicLong(0);
    final AtomicLong requestSubmittedPerInterval = new AtomicLong(0);

    private final DiskbasedRequestPersistence delegate =
            new DiskbasedRequestPersistence();

    private final ThreadPoolExecutor scheduler;

    private static class InstanceHolder {
        final static RequestPersistence INSTANCE = new AsyncRequestPersistence();
    }

    private AsyncRequestPersistence() {
        scheduler = new ThreadPoolExecutor(1, 3, 60 * ONE_MINUTE_IN_SECONDS, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(200), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t;
                t = new Thread(r, "AsyncRequestPersistence");
                return t;
            }
        });

        scheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(6);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (logger.isInfoEnabled()) {
                    logger.info("task count: " + requestSubmittedPerInterval
                            + ", Queue size: " + scheduler.getQueue().size()
                            + ", taskTimeConsumedPerInterval: "
                            + taskTimeConsumedPerInterval.get() + " ms");
                }
                taskTimeConsumedPerInterval.set(0);
                requestSubmittedPerInterval.set(0);
            }
        };

        scheduledExecutorService.scheduleAtFixedRate(task, 0,ONE_MINUTE_IN_SECONDS * 1000, TimeUnit.SECONDS);
    }

    public static RequestPersistence getInstance() {
        return InstanceHolder.INSTANCE;
    }


    @Override
    public void store(final MMSDeliverRequest request) {
        Callable<Boolean> methodRequest = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                long start = System.currentTimeMillis();
                try {
                    delegate.store(request);
                } finally {
                    taskTimeConsumedPerInterval.addAndGet(System.currentTimeMillis() - start);
                }
                return Boolean.TRUE;
            }
        };
        scheduler.submit(methodRequest);

        requestSubmittedPerInterval.incrementAndGet();
    }

    @Override
    public void close() throws IOException {
        scheduler.shutdown();
    }
}
