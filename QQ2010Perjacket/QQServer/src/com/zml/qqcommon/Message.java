package com.zml.qqcommon;

import java.awt.*;
import java.io.Serializable;

/**
 * @author 张民磊
 * @version 1.0
 * 服务端和消息端通信的消息对象
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sender;  //发送者
    private String getSender; //接收者
    private String content;  //消息内容
    private String sendTime; // 发动时间
    private String mesType; //消息类型
    private byte[] fileByte;
    private int filelen = 0;
    private String data;
    private String src;

    public Message() {
    }

    public byte[] getFileByte() {
        return fileByte;
    }

    public void setFileByte(byte[] fileByte) {
        this.fileByte = fileByte;
    }

    public int getFilelen() {
        return filelen;
    }

    public void setFilelen(int filelen) {
        this.filelen = filelen;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Message(String sender, String getSender, String content, String sendTime, String mesType) {
        this.sender = sender;
        this.getSender = getSender;
        this.content = content;
        this.sendTime = sendTime;
        this.mesType = mesType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetSender() {
        return getSender;
    }

    public void setGetSender(String getSender) {
        this.getSender = getSender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }
}
