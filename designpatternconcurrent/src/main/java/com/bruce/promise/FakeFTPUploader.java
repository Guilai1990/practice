package com.bruce.promise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @Author: Bruce
 * @Date: 2019/5/31 17:23
 * @Version 1.0
 */
public class FakeFTPUploader implements FTPUploader {

    private Logger LOG = LoggerFactory.getLogger(FakeFTPUploader.class);

    @Override
    public void init(String ftpServer, String ftpUserName, String password, String serverDir) throws Exception {

    }

    @Override
    public void upload(File file) throws Exception {
        LOG.info("uploading %s", file);
        Thread.sleep(1000);
    }

    @Override
    public void disconnect() {

    }
}
