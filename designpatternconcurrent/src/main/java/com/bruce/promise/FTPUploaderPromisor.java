package com.bruce.promise;

import java.util.concurrent.*;

/**
 * @Author: Bruce
 * @Date: 2019/5/31 17:41
 * @Version 1.0
 */
public class FTPUploaderPromisor {

    public static Future<FTPUploader> newFTPUploaderPromise(String ftpServer, String ftpUserName, String password, String serverDir) {
        Executor helperExecutor = new Executor() {
            @Override
            public void execute(Runnable command) {
                Thread t = new Thread(command);
                t.start();
            }
        };
        return newFTPUploaderPromise(ftpServer, ftpUserName, password, serverDir, helperExecutor);
    }

    public static Future<FTPUploader> newFTPUploaderPromise(final String ftpServer, final String ftpUserName,
                                                            final String password, final String serverDir, Executor helperExecutor) {
        Callable<FTPUploader> callable = new Callable<FTPUploader>() {
            @Override
            public FTPUploader call() throws Exception {
                String implClazz = System.getProperty("ftp.client.impl");
                if (null == implClazz) {
                    implClazz = "com.bruce.promise.FTPClientUtil";
                }
                FTPUploader ftpUploader;
                ftpUploader = (FTPUploader) Class.forName(implClazz).newInstance();
                ftpUploader.init(ftpServer, ftpUserName, password, serverDir);
                return ftpUploader;
            }
        };

        final FutureTask<FTPUploader> task = new FutureTask<>(callable);
        helperExecutor.execute(task);
        return task;
    }
}
