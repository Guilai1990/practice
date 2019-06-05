package com.bruce.promise;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Bruce
 * @Date: 2019/5/31 18:05
 * @Version 1.0
 */
public class FTPClientUtil implements FTPUploader {

    final FTPClient ftp = new FTPClient();
    final Map<String, Boolean> dirCreateMap = new HashMap<>();

    @Override
    public void init(String ftpServer, String ftpUserName, String password, String serverDir) throws Exception {
        FTPClientConfig config = new FTPClientConfig();
        ftp.configure(config);

        int reply;
        ftp.connect(ftpServer);

        System.out.println(ftp.getReplyString());

        reply = ftp.getReplyCode();

        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new RuntimeException("FTP server refused connection.");
        }
        boolean isOK = ftp.login(ftpUserName, password);
        if (isOK) {
            System.out.println(ftp.getReplyString());
        } else {
            throw new RuntimeException("Failed to login " + ftp.getReplyString());
        }

        reply = ftp.cwd(serverDir);
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new RuntimeException(
                    "Failed to change working directory. reply: " + reply
            );
        } else {
            System.out.println(ftp.getReplyString());
        }

        ftp.setFileType(FTP.ASCII_FILE_TYPE);
    }

    @Override
    public void upload(File file) throws Exception {
        InputStream dataIn = new BufferedInputStream(new FileInputStream(file), 1024 * 8);

        boolean isOk;
        String dirName = file.getParentFile().getName();
        String fileName = dirName + '/' + file.getName();
        ByteArrayInputStream checkFileInputStream = new ByteArrayInputStream("".getBytes());

        try {
            if (!dirCreateMap.containsKey(dirName)) {
                ftp.makeDirectory(dirName);
                dirCreateMap.put(dirName, null);
            }

            try {
                isOk = ftp.storeFile(fileName, dataIn);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload " + file, e);
            }
            if (isOk) {
                ftp.storeFile(fileName + ".c", checkFileInputStream);
            } else {
                throw new RuntimeException("Failed to upload " + file + ",reply:"
                + ftp.getReplyString());
            }
        } finally {
            dataIn.close();
        }
    }

    @Override
    public void disconnect() {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (IOException e) {

            }
        }
    }
}
