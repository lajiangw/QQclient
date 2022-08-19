package com.zml.qqclient.service;

import com.zml.qqcommon.Message;
import com.zml.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * @author 张民磊
 * @version 1.0
 * 提供和发送消息相关的方法
 */
public class MessageClientServer {


    public void seeOff(String userId){
        Message message = new Message();
        message.setMesType(MessageType.MESS_OFFCHAKAN);
        message.setGetSender(userId);
        System.out.println("查看离线");
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(ManageClientConnectServerThrand.getClientConnectServerThread(userId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendMEssgoOff(String content, String sendId, String getterId) {
        Message message = new Message();
        message.setMesType(MessageType.MESS_OFFMES_);
        message.setSender(sendId);
        message.setGetSender(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());//发送消息的时间
        System.out.println(sendId + "对" + getterId + "说：" + content);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThrand.getClientConnectServerThread(sendId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessageToOne(String content, String sendId, String getterId) {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        message.setSender(sendId);
        message.setGetSender(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());//发送消息的时间
        System.out.println(sendId + "对" + getterId + "说：" + content);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThrand.getClientConnectServerThread(sendId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param conter 发送的内容
     * @param userId 发送者
     */
    public void sendMessageAll(String conter, String userId) {
        Message message = new Message();
        message.setSendTime(new Date().toString());
        message.setContent(conter);
        message.setSender(userId);
        message.setMesType(MessageType.MESS_TO_ALL_MES);
        System.out.println(userId + "对大家说：“" + conter);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThrand.getClientConnectServerThread(userId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
