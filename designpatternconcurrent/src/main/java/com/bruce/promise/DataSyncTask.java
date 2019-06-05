package com.bruce.promise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author: Bruce
 * @Date: 2019/5/31 18:54
 * @Version 1.0
 */
public class DataSyncTask implements Runnable {

    private Logger LOG = LoggerFactory.getLogger(DataSyncTask.class);

    private final Map<String, String> taskParameters;

    public DataSyncTask(Map<String, String> taskParameters) {
        this.taskParameters = taskParameters;
    }

    @Override
    public void run() {
        String ftpServer = taskParameters.get("server");
        String ftpUserName = taskParameters.get("userName");
        String password = taskParameters.get("password");
        String serverDir = taskParameters.get("serverDir");

        Future<FTPUploader> ftpClientUtilPromise = FTPUploaderPromisor.newFTPUploaderPromise(ftpServer,
                ftpUserName, password, serverDir);

        try {
            generateFilesFromDB();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        FTPUploader ftpClientUtil = null;

        try {
            ftpClientUtil = ftpClientUtilPromise.get();
        } catch (InterruptedException e) {
            ;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        uploadFiles(ftpClientUtil);
    }

    private void generateFilesFromDB() throws InterruptedException {
        LOG.info("generating files from database...");

        Thread.sleep(1000);
    }

    private void uploadFiles(FTPUploader ftpClientUtil) {
        Set<File> files = retrieveGeneratedFiles();
        for (File file : files) {
            try {
                ftpClientUtil.upload(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected Set<File> retrieveGeneratedFiles() {
        Set<File> files = new HashSet<File>();

        File currDir = new File("filePath");

        for (File f : currDir.listFiles((dir, name) -> new File(dir, name).isFile() && name.endsWith(".class"))) {
            files.add(f);
        }

        return files;
    }
}
