package com.bruce.serialThreadConfinement;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: Bruce
 * @Date: 2019/6/3 19:06
 * @Version 1.0
 */
public class MessageFileDownloader {

    private final WorkerThread workerThread;

    public MessageFileDownloader(String outputDir, final String ftpServer, final String userName, final String password,
                                 final String servWorkingDir) throws Exception {
        Path path = Paths.get(outputDir);
        if (!path.toFile().exists()) {
            Files.createDirectories(path);
        }

        workerThread = new WorkerThread(outputDir, ftpServer, userName, password, servWorkingDir);
    }

    public void init() {
        workerThread.start();
    }

    public void shutdown() {
        workerThread.terminate();
    }

    public void downloadFile(String file) {
        workerThread.download(file);
    }
}
