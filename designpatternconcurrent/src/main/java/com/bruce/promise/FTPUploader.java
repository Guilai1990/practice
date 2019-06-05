package com.bruce.promise;

import java.io.File;

/**
 * @Author: Bruce
 * @Date: 2019/5/31 17:20
 * @Version 1.0
 */
public interface FTPUploader {

    void init(String ftpServer, String ftpUserName, String password, String serverDir) throws Exception;

    void upload(File file) throws Exception;

    void disconnect();
}
