package com.zml.qqserver.server;

import com.zml.qqcommon.Message;
import com.zml.qqcommon.MessageType;
import com.zml.qqcommon.User;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.HashMap;

import java.util.Set;

/**
 * @author 张民磊
 * @version 1.0
 * 这是服务器，在监听9999端口，等待客户端的连接，并保持通信
 */
public class QQServer implements Serializable {

    private static HashMap<String, User> validUser = new HashMap<>();
    private static HashMap<String, Message> offMess = new HashMap<>();
    private ServerSocket ss;


    public static void addOffMess(String userId, Message message) {
        offMess.put(userId, message);
    }

    public static HashMap<String, Message> getOffMess() {
        return offMess;
    }

    public static void removeOffMess(String userId) {
        QQServer.offMess.remove(userId);
    }

    static {
        validUser.put("100", new User("100", "123456"));
        validUser.put("200", new User("200", "123456"));
        validUser.put("300", new User("300", "123456"));
        validUser.put("至尊宝", new User("至尊宝", "123456"));
        validUser.put("紫霞仙子", new User("紫霞仙子", "123456"));
    }


    public boolean cheakUser(String userId, String password) {
        User user = validUser.get(userId);

        if (user == null) {
            return false;
        }

        if (user.getUserId().equals(userId) && user.getPaswd().equals(password)) {
            return true;
        }

        return false;
    }

    public QQServer() {
        System.out.println("正在监听9999端口...");
        try {
            new Thread(new SendNewsToAllService()).start();
            ss = new ServerSocket(9996);
            while (true) {
                Socket accept = ss.accept();

                ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream());
                User user = (User) objectInputStream.readObject();
                Message message = new Message();

                if (cheakUser(user.getUserId(), user.getPaswd())) {   //判断账号密码是否正确，
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);  //将type的登入状态置换为1的标志，即登入成功
                    objectOutputStream.writeObject(message);//
                    Message message1 = (Message) offMess.get(user.getUserId());

                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(accept, user.getUserId());
                    serverConnectClientThread.start();
//                        创建一个线程 和客户端保持通讯
                    ManagerServerThrand.addCientThrand(user.getUserId(), serverConnectClientThread);


                } else {
//                    登入失败也将信息传回去，
                    message.setMesType(MessageType.MESSAGE_LOGIN_FATL);
                    objectOutputStream.writeObject(message);
                    accept.close();
                }


            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
//如果服务端推出了while,说明服务器不在监听 也需要挂不必
            try {
                ss.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
