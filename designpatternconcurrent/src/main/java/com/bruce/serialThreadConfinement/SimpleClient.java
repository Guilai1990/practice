package com.bruce.serialThreadConfinement;

/**
 * @Author: Bruce
 * @Date: 2019/6/3 22:29
 * @Version 1.0
 */
public class SimpleClient {

    private static final MessageFileDownloader DOWNLOADER;

    static {
        MessageFileDownloader mfd = null;
        try {
            mfd = new MessageFileDownloader(getWorkingDir("serialThreadConfinement"), "192.168.8.201", "ftp",
                    "123456", "~/messages");
            mfd.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DOWNLOADER = mfd;
    }

    public static void main(String[] args) throws InterruptedException {
        DOWNLOADER.downloadFile("abc.xml");
        DOWNLOADER.downloadFile("123.xml");
        DOWNLOADER.downloadFile("xyz.xml");

        Thread.sleep(1000);

        DOWNLOADER.shutdown();
    }

    public static String getWorkingDir(String subDir) {
        if (null == subDir) {
            subDir = "";
        }
        String dir = System.getProperty("user.dir");
        dir = dir.replaceAll("\\\\", "/");
        dir = dir + subDir + "/";
        return dir;
    }
}
