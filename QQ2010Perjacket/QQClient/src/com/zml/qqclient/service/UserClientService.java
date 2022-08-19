package com.zml.qqclient.service;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import com.zml.qqcommon.Message;
import com.zml.qqcommon.MessageType;
import com.zml.qqcommon.User;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author 张民磊
 * @version 1.0
 * 该类完成验证登入和注册等功能
 */
public class UserClientService {

    private User u = new User();
    private Socket socket;

    boolean lopp = false;

    public boolean checkUser(String userId, String passWord) {
        u.setUserId(userId);
        u.setPaswd(passWord);

        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9996);
            ObjectOutputStream oom = new ObjectOutputStream(socket.getOutputStream());
            oom.writeObject(u);
//            读取服务器回复的messge对象
            ObjectInputStream oim = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) oim.readObject();
            if (ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
                lopp = true;
//                登入成功
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                clientConnectServerThread.start();
//                将线程加入到集合中
                ManageClientConnectServerThrand.addClientConnectServerThread(userId, clientConnectServerThread);

            } else {
//                如果登入失败，我们需要将sockt关闭，
                socket.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lopp;
    }

    //向服务器请求在线用于列表
    public void onlinFriendList() {

        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(u.userId);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThrand.getClientConnectServerThread(u.userId).getSocket().getOutputStream());
            oos.writeObject(message);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logout() {
        Message message = new Message();

        message.setMesType(MessageType.MESS_CLIENT_EXIT);
        message.setSender(u.getUserId());
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageClientConnectServerThrand.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
            System.out.println(u.getUserId() + " 退出系统 ");
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
