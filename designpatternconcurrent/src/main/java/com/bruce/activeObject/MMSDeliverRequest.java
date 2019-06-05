package com.bruce.activeObject;


import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Bruce
 * @Date: 2019/6/4 21:29
 * @Version 1.0
 */
public class MMSDeliverRequest implements Serializable {

    private String transactionID;
    private String messageType = "Delivery.req";
    private String senderAddress;

    private Recipient recipient = new Recipient();
    private String subject;
    private Attachment attachment = new Attachment();

    private long expiry;
    private Date timeStamp;

    public MMSDeliverRequest() {
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public long getExpiry() {
        return expiry;
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "MMSDeliverRequest {" +
                "transactionID='" + transactionID + '\'' +
                ", messageType='" + messageType + '\'' +
                ", senderAddress='" + senderAddress + '\'' +
                ", recipient=" + recipient +
                ", subject='" + subject + '\'' +
                ", attachment=" + attachment +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
