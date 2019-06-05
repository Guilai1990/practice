package com.bruce.activeObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Bruce
 * @Date: 2019/6/4 21:47
 * @Version 1.0
 */
public class DiskbasedRequestPersistence implements RequestPersistence{

    final SectionBasedDiskStorage storage = new SectionBasedDiskStorage();

    private Logger LOG = LoggerFactory.getLogger(DiskbasedRequestPersistence.class);

    @Override
    public void store(MMSDeliverRequest request) {
        String[] fileNameParts = storage.apply4Filename(request);
        File file = new File(fileNameParts[0]);
        try (ObjectOutputStream objOutput = new ObjectOutputStream(new FileOutputStream(file));) {
            objOutput.writeObject(request);
        } catch (IOException e) {
            storage.decrementSectionFileCount(fileNameParts[1]);
            LOG.error("Failed to store request");
        }
    }

    @Override
    public void close() throws IOException {

    }

    class SectionBasedDiskStorage {

        private Deque<String> sectionNames = new LinkedList<String>();
        private Map<String/*Storage Directory*/, AtomicInteger/*Counter*/> sectionFileCountMap =
                new HashMap<>();
        private int maxFilesPerSection = 2000;
        private int maxSectionCount = 100;
        private String storageBaseDir = "C:\\Users";
        private final Object sectionLock = new Object();

        public SectionBasedDiskStorage() {
            File dir = new File(storageBaseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }

        public String[] apply4Filename(MMSDeliverRequest request) {
            String sectionName;
            int iFileCount;
            String oldestSectionName = null;
            String[] fileName = new String[2];
            synchronized (sectionLock) {
                sectionName = getSectionName();
                AtomicInteger fileCount;
                fileCount = sectionFileCountMap.get(sectionName);
                iFileCount = fileCount.get();
                if (iFileCount >= maxFilesPerSection) {
                    if (sectionNames.size() >= maxSectionCount) {
                        oldestSectionName = sectionNames.removeFirst();
                    }
                    sectionName = makeNewSectionDir();
                    fileCount = sectionFileCountMap.get(sectionName);
                }
                iFileCount = fileCount.addAndGet(1);
            }

            fileName[0] = storageBaseDir + "/" + sectionName + "/"
                    + new DecimalFormat("0000").format(iFileCount) + "-"
                    + request.getTimeStamp().getTime() / 1000 + "-"
                    + request.getExpiry() + ".rq";
            fileName[1] = sectionName;

            if (null != oldestSectionName) {
                this.removeSection(oldestSectionName);
            }
            return fileName;
        }

        public void decrementSectionFileCount(String sectionName) {
            AtomicInteger fileCount = sectionFileCountMap.get(sectionName);
            if (null != fileCount) {
                fileCount.decrementAndGet();
            }
        }

        private boolean removeSection(String sectionName) {
            boolean result = true;
            File dir = new File(storageBaseDir + "/" + sectionName);
            for (File file : dir.listFiles()) {
                result = result && file.delete();
            }
            result = result && dir.delete();
            return result;
        }

        private String getSectionName() {
            String sectionName;
            if (sectionNames.isEmpty()) {
                sectionName = this.makeNewSectionDir();
            } else {
                sectionName = sectionNames.getLast();
            }
            return sectionName;
        }

        private String makeNewSectionDir() {
            String sectionName;
            SimpleDateFormat sdf = new SimpleDateFormat("MMddMM HHmmss");
            sectionName = sdf.format(new Date());
            File dir = new File(storageBaseDir + "/" + sectionName);
            if (dir.mkdir()) {
                sectionNames.addLast(sectionName);
                sectionFileCountMap.put(sectionName, new AtomicInteger(0));
            } else {
                throw new RuntimeException("Cannot create section dir " + sectionName);
            }
            return sectionName;
        }
    }


}
