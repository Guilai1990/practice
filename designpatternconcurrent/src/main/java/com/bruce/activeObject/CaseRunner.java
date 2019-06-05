package com.bruce.activeObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Bruce
 * @Date: 2019/6/5 2:12
 * @Version 1.0
 */
public class CaseRunner {

    public static void main(String[] args) {

        int numRequestThreads = Runtime.getRuntime().availableProcessors();
        int targetTPS = 50;
        float duration = 12;

        final RequestSender reqSender = new RequestSender(
                (int) duration * targetTPS
        );


    }

    static class RequestSender implements Runnable {

        final AtomicInteger totalCount = new AtomicInteger();
        private final RequestPersistence persistence;
        private final Attachment attachment;

        public RequestSender(int n) {
            totalCount.set(n);
            persistence = AsyncRequestPersistence.getInstance();
            attachment = new Attachment();
            try {
                URL url = CaseRunner.class.getClassLoader().getResource("C:\\Users\\Bruce\\Pictures\\Camera Roll\\0.jpg");
                attachment.setContentType("image/jpeg");
                attachment.setContent(Files.readAllBytes(Paths.get(url.toURI())));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            int remainingCount;
            while ((remainingCount = totalCount.getAndDecrement()) > 0) {
                MMSDeliverRequest request = new MMSDeliverRequest();
                request.setTransactionID(String.valueOf(remainingCount));
                request.setSenderAddress("1234566987");
                request.setTimeStamp(new Date());
                request.setExpiry((System.currentTimeMillis() + 3600000) / 1000);

                request.setSubject("Hi");
                request.getRecipient().addTo("776");
                request.setAttachment(attachment);
                persistence.store(request);
            }
        }

        public void shutdown() {
            try {
                persistence.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
