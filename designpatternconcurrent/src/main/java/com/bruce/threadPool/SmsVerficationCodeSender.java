package com.bruce.threadPool;

import com.bruce.threadSpecificStorage.ThreadSpecificSecureRandom;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.concurrent.*;

/**
 * @Author: Bruce
 * @Date: 2019/6/4 13:09
 * @Version 1.0
 */
public class SmsVerficationCodeSender {

    private Logger LOG = LoggerFactory.getLogger(SmsVerficationCodeSender.class);

    public static void config() {
        PropertyConfigurator.configure("C:/Users/Bruce/Downloads/LearningJavaProject/practice/designpatternconcurrent/src/main/resource/log4j.properties");
    }

    private static final ExecutorService EXECUTOR =
            new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(), 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r, "VerfCodeSender");
                    t.setDaemon(true);
                    return t;
                }
            }, new ThreadPoolExecutor.DiscardPolicy());

    public static void main(String[] args) {

        config();

        SmsVerficationCodeSender client = new SmsVerficationCodeSender();

        client.sendVerificationSms("1223049442");
        client.sendVerificationSms("1234442343");
        client.sendVerificationSms("1988323232");

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void sendVerificationSms(final String msisdn) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                int verificationCode = ThreadSpecificSecureRandom.INSTANCE.nextInt(99999);
                DecimalFormat df = new DecimalFormat("00000");
                String txtVerCode = df.format(verificationCode);

                sendSms(msisdn, txtVerCode);
            }
        };
        EXECUTOR.submit(task);
    }

    private void sendSms(String msisdn, String verificationCode) {

        LOG.info("Sending verification code " + verificationCode + " to " + msisdn);
    }
}
