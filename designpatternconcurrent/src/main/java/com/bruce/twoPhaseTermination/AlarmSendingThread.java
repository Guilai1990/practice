package com.bruce.twoPhaseTermination;

import com.bruce.guardedSuspension.AlarmAgent;
import com.bruce.guardedSuspension.AlarmInfo;
import com.bruce.guardedSuspension.AlarmType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Bruce
 * @Date: 2019/5/31 0:07
 * @Version 1.0
 */
public class AlarmSendingThread extends AbstractTerminatableThread {

    private Logger LOG = LoggerFactory.getLogger(AlarmSendingThread.class);

    private final AlarmAgent alarmAgent = new AlarmAgent();

    private final BlockingQueue<AlarmInfo> alarmQueue;
    private final ConcurrentMap<String, AtomicInteger> submittedAlarmRegistry;

    public AlarmSendingThread() {
        alarmQueue = new ArrayBlockingQueue<AlarmInfo>(100);
        submittedAlarmRegistry = new ConcurrentHashMap<String, AtomicInteger>();
        alarmAgent.init();
    }

    @Override
    protected void doRun() throws Exception {
        AlarmInfo alarm;
        alarm = alarmQueue.take();
        terminationToken.reservations.decrementAndGet();

        try {
            alarmAgent.sendAlarm(alarm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (AlarmType.RESUME == alarm.type) {
            String key = AlarmType.FAULT.toString() + ':' + alarm.getId() + '@'
                    + alarm.getExtraInfo();
            submittedAlarmRegistry.remove(key);

            key = AlarmType.RESUME.toString() + ':' + alarm.getId() + '@'
                    + alarm.getExtraInfo();
            submittedAlarmRegistry.remove(key);
        }
    }

    public int sendAlarm(final AlarmInfo alarmInfo) {
        AlarmType type = alarmInfo.type;
        String id = alarmInfo.getId();
        String extraInfo = alarmInfo.getExtraInfo();

        if (terminationToken.isToShutdown()) {
            LOG.error("rejected alarm:" + id + "," + extraInfo);
            return -1;
        }

        int duplicateSubmissionCount = 0;
        try {
            AtomicInteger prevSubmittedCounter;

            prevSubmittedCounter = submittedAlarmRegistry.putIfAbsent(type.toString()
                    + ':' + id + '@' + extraInfo, new AtomicInteger(0));
            if (null == prevSubmittedCounter) {
                alarmQueue.put(alarmInfo);
                terminationToken.reservations.incrementAndGet();
            } else {
                duplicateSubmissionCount = prevSubmittedCounter.incrementAndGet();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return duplicateSubmissionCount;
    }

    @Override
    protected void doCleanup(Exception exp) {
        if (null != exp && !(exp instanceof InterruptedException)) {
            exp.printStackTrace();
        }
        alarmAgent.disconnect();
    }
}
