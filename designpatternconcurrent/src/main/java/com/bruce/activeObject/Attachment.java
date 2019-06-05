package com.bruce.activeObject;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Author: Bruce
 * @Date: 2019/6/4 21:33
 * @Version 1.0
 */
public class Attachment implements Serializable {

    private static final long serialVersionUID = -3242951327009946609L;
    private String contentType;
    private byte[] content = new byte[0];

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Attachment {" +
                "contentType='" + contentType + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
