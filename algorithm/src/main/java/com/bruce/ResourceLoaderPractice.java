package com.bruce;

import org.springframework.core.io.*;

/**
 * @Author: Bruce
 * @Date: 2019/5/20 20:07
 * @Version 1.0
 */
public class ResourceLoaderPractice {

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();

        Resource fileResource1 = resourceLoader.getResource("C:\\logs\\tio-examples-helloworld-server\\info.log");

        System.out.println("fileResource1 is FileSystemResource:" + (fileResource1 instanceof FileSystemResource));

        Resource fileResource2 = resourceLoader.getResource("C:\\logs\\tio-examples-helloworld-server\\info.log");

        System.out.println("fileResource2 is classPathResource:" + (fileResource2 instanceof ClassPathResource));

        Resource urlResource2 = resourceLoader.getResource("http://www.baidu.com");

        System.out.println("urlResource is urlResource:" + (urlResource2 instanceof UrlResource));
    }




}
