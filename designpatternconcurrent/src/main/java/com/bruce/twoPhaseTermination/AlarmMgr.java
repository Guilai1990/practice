package com.bruce.twoPhaseTermination;

import com.bruce.guardedSuspension.AlarmInfo;
import com.bruce.guardedSuspension.AlarmType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Bruce
 * @Date: 2019/5/31 0:05
 * @Version 1.0
 */
public class AlarmMgr {

    private Logger LOG = LoggerFactory.getLogger(AlarmMgr.class);

    private static final AlarmMgr INSTANCE = new AlarmMgr();

    private volatile boolean shutdownRequested = false;

    private final AlarmSendingThread alarmSendingThread;

    private AlarmMgr() {
        alarmSendingThread = new AlarmSendingThread();
    }

    public static AlarmMgr getInstance() {
        return INSTANCE;
    }

    public int sendAlarm(AlarmType type,String id, String extraInfo) {
        LOG.info("Trigger alarm " + type + "," + id + ',' + extraInfo);
        int duplicateSubmissionCount = 0;
        try {
            AlarmInfo alarmInfo = new AlarmInfo(id, type);
            alarmInfo.setExtraInfo(extraInfo);
            duplicateSubmissionCount = alarmSendingThread.sendAlarm(alarmInfo);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return duplicateSubmissionCount;
    }

    public void init() {
        alarmSendingThread.start();
    }

    public synchronized void shutdown() {
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested");
        }

        alarmSendingThread.terminate();
        shutdownRequested = true;
    }
}
