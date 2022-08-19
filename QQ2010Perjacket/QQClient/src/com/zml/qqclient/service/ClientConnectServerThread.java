package com.zml.qqclient.service;

import com.sun.media.jfxmediaimpl.HostUtils;
import com.zml.qqclient.utiliy.Utility;
import com.zml.qqcommon.Message;
import com.zml.qqcommon.MessageType;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;

/**
 * @author 张民磊
 * @version 1.0
 * 线程管理
 */
public class ClientConnectServerThread extends Thread {
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {

//        因为需要一致持有socket 做成无线循环
        while (true) {
            try {

                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject();

                //如果没有服务器发送消息，将会在这里堵塞

                if (message.getMesType().equals(MessageType.MESS_RET_ONLINE_FRIEND)) {
                    String[] s = message.getContent().split(" ");
                    System.out.println("===========当前在线用户列表================");
                    for (int i = 0; i < s.length; i++) {
                        System.out.println("在线的用户：" + s[i]);
                    }
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    System.out.println("\n" + message.getSender() + " 对我说：“" + message.getContent() + "”"
                            + "\t 时间为" + message.getSendTime());
                } else if (message.getMesType().equals(MessageType.MESS_TO_ALL_MES)) {
                    System.out.println("\n" + message.getSender() + "对大家说 “" + message.getContent() + "“  时间为" + message.getSendTime());
                } else if (message.getMesType().equals(MessageType.MESS_FILS_MES)) {
                    System.out.println(message.getSender() + "给我发送文件到：" + message.getSrc() + "保存到我的电脑路径 ?");
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getData());
                    fileOutputStream.write(message.getFileByte());
                    fileOutputStream.close();
                    System.out.println("发送文件成功！");
                } else if (message.getMesType().equals(MessageType.MESS_OFFCHAKAN)) {
                    System.out.println("离线消息: \n " + message.getContent());
                } else {
                    System.out.println("是其他类型message，无法处理...");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}
