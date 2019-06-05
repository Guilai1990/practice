package com.bruce.producerConsumer;

import com.bruce.twoPhaseTermination.AbstractTerminatableThread;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author: Bruce
 * @Date: 2019/6/4 16:00
 * @Version 1.0
 */
public class AttachmentProcessor {

    public static final String ATTACHMENT_STORE_BASE_DIR = "C:/";

    private final Channel<File> channel = new BlockingQueueChannel<File>(
            new ArrayBlockingQueue<File>(200)
    );

    private final AbstractTerminatableThread indexingThread =
    new AbstractTerminatableThread() {
        @Override
        protected void doRun() throws Exception {
            File file = null;

            file = channel.take();
            try {
                indexFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                terminationToken.reservations.decrementAndGet();
            }
        }

        private void indexFile(File file) throws Exception {
            Thread.sleep(1000);
        }
    };

    public void init() {
        indexingThread.start();
    }

    public void shutdown() {
        indexingThread.terminate();
    }

    public void saveAttachment(InputStream in, String documentId, String originalFileName) throws IOException {
        File file = saveAsFile(in, documentId, originalFileName);
        try {
            channel.put(file);
            indexingThread.terminationToken.reservations.incrementAndGet();
        } catch (InterruptedException e) {
            ;
        }
    }

    private File saveAsFile(InputStream in, String documentId, String originalFileName) throws IOException {
        String dirName = ATTACHMENT_STORE_BASE_DIR + documentId;
        File dir = new File(dirName);
        dir.mkdirs();
        File file = new File(dirName + '/' + Normalizer.normalize(originalFileName, Normalizer.Form.NFC));

        if (!new File(dirName).equals(new File(file.getCanonicalFile().getParent()))) {
            throw new SecurityException("Invalid originalFileName:" + originalFileName);
        }
        try (InputStream dataIn = in) {
            Files.copy(dataIn, Paths.get(file.getCanonicalPath()), StandardCopyOption.REPLACE_EXISTING);
        }
        return file;
    }
}
