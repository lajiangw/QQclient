package com.zml.qqserver.server;

import com.zml.qqcommon.Message;
import com.zml.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author 张民磊
 * @version 1.0
 * 该类的一个对象和客户端保持通讯
 */
public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String userId;//连接到服务端的id

    public ServerConnectClientThread() {

    }

    public String getUserId() {
        return userId;
    }

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        new ServerOffMessThrand().start();
        while (true) {
            System.out.println("服务端和客户端" + userId + "保持通讯，读取数据..");

            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject();
                Message message2 = QQServer.getOffMess().get(userId);

                if (message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    System.out.println(message.getSender() + "拉取在线用户列表");
                    String onlineUser = ManagerServerThrand.getOnlineUser();
                    Message message1 = new Message();
                    message1.setMesType(MessageType.MESS_RET_ONLINE_FRIEND);
                    message1.setContent(onlineUser);
                    message1.setGetSender(message.getSender());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message1);
                } else if (message.getMesType().equals(MessageType.MESS_CLIENT_EXIT)) {
                    System.out.println(message.getSender() + " 退 出 ");
                    ManagerServerThrand.removeServerConnectClientThrand(message.getSender());
                    socket.close();
                    break;
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    ServerConnectClientThread cilentThrand = ManagerServerThrand.getCilentThrand(message.getGetSender());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(cilentThrand.getSocket().getOutputStream());
                    objectOutputStream.writeObject(message);

                    //如果过客服不在线，可以将消息保存到数据库，这样可以实现。离线留言
                } else if (message.getMesType().equals(MessageType.MESS_OFFMES_)) {
                    System.out.println("离线消息 添加入offmes");
                    message.setMesType(MessageType.MESS_OFFCHAKAN);
                    QQServer.addOffMess(message.getGetSender(), message);

                } else if (message.getMesType().equals(MessageType.MESS_OFFCHAKAN)) {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message2);
                } else if (message.getMesType().equals(MessageType.MESS_TO_ALL_MES)) {
                    System.out.println(123);
                    HashMap<String, ServerConnectClientThread> hm = ManagerServerThrand.getHm();

                    Iterator iterator = hm.keySet().iterator();
                    while (iterator.hasNext()) {
                        String onLineUserId = iterator.next().toString();
                        if (!onLineUserId.equals(message.getSender())) {//排除取反消息的用户
                            ObjectOutputStream oos =
                                    new ObjectOutputStream(hm.get(onLineUserId).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                    }
                } else if (message.getMesType().equals(MessageType.MESS_FILS_MES)) {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManagerServerThrand.getCilentThrand(message.getGetSender()).getSocket().getOutputStream());
                    objectOutputStream.writeObject(message);
                } else {
                    System.out.println("其他类型的message");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
