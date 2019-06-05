package com.bruce.masterSlave;

import com.bruce.twoPhaseTermination.AbstractTerminatableThread;
import java.io.*;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * @Author: Bruce
 * @Date: 2019/6/1 19:34
 * @Version 1.0
 */
public class TPSStat {

    public static void main(String[] args) throws Exception {

        String logBaseDir = args[0];

        String excludedOperationNames = "";

        String includedOperationNames = "sendSms,";

        String destinationSysName = "*";

        int argc = args.length;

        if (argc > 2) {
            excludedOperationNames = args[1];
        }
        if (argc > 3) {
            excludedOperationNames = args[2];
        }
        if (argc > 4) {
            destinationSysName = args[3];
        }
        Master processor = new Master(logBaseDir, excludedOperationNames, includedOperationNames, destinationSysName);
    }

    private static class Master {
        final String logFileBaseDir;
        final String excludedOperationNames;
        final String includedOperationNames;
        final String destinationSysNames;

        static final int NUMBER_OF_FILES_FOR_EACH_DISPATCH = 5;
        static final int WORKER_COUNT = Runtime.getRuntime().availableProcessors();

        public Master(String logFileBaseDir, String excludedOperationNames, String includedOperationNames, String destinationSysNames) {
            this.logFileBaseDir = logFileBaseDir;
            this.excludedOperationNames = excludedOperationNames;
            this.includedOperationNames = includedOperationNames;
            this.destinationSysNames = destinationSysNames;
        }

        public ConcurrentMap<String, AtomicInteger> calculate(BufferedReader fileNamesReader) throws IOException {
            ConcurrentMap<String, AtomicInteger> repository = new ConcurrentSkipListMap<>();
            TPSStat.Worker[] workers = createAndStartWorkers(repository);
            dispatchTask(fileNamesReader, workers);
            for (int i = 0; i < WORKER_COUNT; i++) {
                workers[i].terminate(true);
            }
            return repository;
        }

        private TPSStat.Worker[] createAndStartWorkers(ConcurrentMap<String, AtomicInteger> repository) {
            TPSStat.Worker[] workers = new TPSStat.Worker[WORKER_COUNT];
            TPSStat.Worker worker;
            Thread.UncaughtExceptionHandler en = new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    e.printStackTrace();
                }
            };

            for (int i = 0; i < WORKER_COUNT; i++) {
                worker = new TPSStat.Worker(repository, excludedOperationNames, includedOperationNames, destinationSysNames);
                workers[i] = worker;
                worker.setUncaughtExceptionHandler(en);
                worker.start();
            }
            return workers;
        }

        private void dispatchTask(BufferedReader fileNamesReader, TPSStat.Worker[] workers) throws IOException {
            String line;
            Set<String> fileNames = new HashSet<String>();
            int fileCount = 0;
            int workerIndex = -1;
            BufferedReader logFileReader;
            while ((line = fileNamesReader.readLine()) != null) {
                fileNames.add(line);
                fileCount++;
                if (0 == (fileCount % NUMBER_OF_FILES_FOR_EACH_DISPATCH)) {
                    workerIndex = (workerIndex + 1) % WORKER_COUNT;
                    logFileReader = makeReaderFrom(fileNames);
                }
            }
        }

        private BufferedReader makeReaderFrom(final Set<String> logFileNames) {

            BufferedReader logFileReader;

            InputStream in = new SequenceInputStream(
                    new Enumeration<InputStream>() {

                        private Iterator<String> iterator = logFileNames.iterator();

                        @Override
                        public boolean hasMoreElements() {
                            return iterator.hasNext();
                        }

                        @Override
                        public InputStream nextElement() {
                            String fileName = iterator.next();
                            InputStream in = null;
                            try {
                                in = new FileInputStream(logFileBaseDir + fileName);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            return in;
                        }
                    }
            );
            logFileReader = new BufferedReader(new InputStreamReader(in));
            return logFileReader;
        }

    }


    private static class Worker extends AbstractTerminatableThread {

        private static final Pattern SPLIT_PATTERN = Pattern.compile("\\|");
        private final ConcurrentMap<String, AtomicInteger> repository;
        private final BlockingQueue<BufferedReader> workQueue;
        private final String selfDevice = "ESB";
        private final String excludedOperationNames;
        private final String includedOperationNames;
        private final String destinationSysName;

        public Worker(ConcurrentMap<String, AtomicInteger> repository,
                      String excludedOperationNames,
                      String includedOperationNames, String destinationSysName) {
            this.repository = repository;
            workQueue = new ArrayBlockingQueue<BufferedReader>(100);
            this.excludedOperationNames = excludedOperationNames;
            this.includedOperationNames = includedOperationNames;
            this.destinationSysName = destinationSysName;
        }

        public void submitWorkload(BufferedReader taskWorkload) {
            try {
                workQueue.put(taskWorkload);
                terminationToken.reservations.incrementAndGet();
            } catch (InterruptedException e) {

            }
        }

        @Override
        protected void doRun() throws Exception {
            BufferedReader logFileReader = workQueue.take();

            String interfaceLogRecord;
            String[] recordParts;
            String timeStamp;
            AtomicInteger reqCounter;
            AtomicInteger existingReqCounter;
            int i = 0;

            try {
                while ((interfaceLogRecord = logFileReader.readLine()) != null) {
                    recordParts = SPLIT_PATTERN.split(interfaceLogRecord, 0);
                    if (0 == ((++i) % 100000)) {
                        Thread.sleep(80);
                        i = 0;
                    }

                    if (recordParts.length < 7) {
                        continue;
                    }

                    if (("request".equals(recordParts[2])) && (recordParts[6].startsWith(selfDevice))) {
                        timeStamp = recordParts[0];
                        timeStamp = new String(timeStamp.substring(0, 19).toCharArray());
                        String operName = recordParts[4];
                        reqCounter = repository.get(timeStamp);
                        if (null == reqCounter) {
                            reqCounter = new AtomicInteger(0);
                            existingReqCounter = repository.putIfAbsent(timeStamp, reqCounter);
                            if (null != existingReqCounter) {
                                reqCounter = existingReqCounter;
                            }
                        }

                        if (isSrcDeviceEligible(recordParts[5])) {
                            if (excludedOperationNames.contains(operName + ',')) {
                                continue;
                            }

                            if ("*".equals(includedOperationNames)) {
                                reqCounter.incrementAndGet();
                            } else {
                                if (includedOperationNames.contains(operName + ',')) {
                                    reqCounter.incrementAndGet();
                                }
                            }
                        }
                    }
                }
            } finally {
                terminationToken.reservations.decrementAndGet();
                logFileReader.close();
            }

        }

        private boolean isSrcDeviceEligible(String sourceNE) {
            boolean result = "*".equals(destinationSysName) ? true : destinationSysName.equals(sourceNE);
            return result;
        }
    }
}
