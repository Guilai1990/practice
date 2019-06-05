package com.bruce.guardedSuspension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

/**
 * @Author: Bruce
 * @Date: 2019/5/30 21:20
 * @Version 1.0
 */
public class AlarmAgent {

    public Logger LOG = LoggerFactory.getLogger(AlarmAgent.class);

    private volatile boolean connectedToServer = false;

    private final Predicate agentConnected = new Predicate() {

        @Override
        public boolean evaluate() {
            return connectedToServer;
        }
    };

    private final Blocker blocker = new ConditionVarBlocker();

    private final Timer heartbeatTimer = new Timer(true);

    public void sendAlarm(final AlarmInfo alarm) throws Exception {
        GuardedAction<Void> guardedAction = new GuardedAction<Void>(agentConnected) {
            @Override
            public Void call() throws Exception {
                doSendAlarm(alarm);
                return null;
            }
        };
        blocker.callWithGuarded(guardedAction);
    }

    private void doSendAlarm(AlarmInfo alarm) {
        LOG.info("sending alarm " + alarm);

        try {
            System.out.println("Sending alarm...");
            Thread.sleep(50);
        } catch (Exception e) {

        }
    }

    public void init() {

        System.out.println("initing...");

        Thread connectingThread = new Thread(new ConnectingTask());

        connectingThread.start();

        heartbeatTimer.schedule(new HeartbeatTask(), 60000, 2000);
    }

    public void disconnect() {
        LOG.info("disconnected from alarm server.");
        connectedToServer = false;
    }

    protected void onConnected() {
        try {
            blocker.signalAfter(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    connectedToServer = true;
                    LOG.info("connected to server");
                    return Boolean.TRUE;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onDisconnected() {
        connectedToServer = false;
    }

    private class ConnectingTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onConnected();
        }
    }

    private class HeartbeatTask extends TimerTask {

        @Override
        public void run() {
            if (!testConnection()) {
                onDisconnected();
                reconnect();
            }
        }

        private boolean testConnection() {
            return true;
        }

        private void reconnect() {
            ConnectingTask connectingTask = new ConnectingTask();

            connectingTask.run();
        }
    }

}
