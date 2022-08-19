package com.zml.qqserver.server;

/**
 * @author 张民磊
 * @version 1.0
 */
public class ServerOffMessThrand extends Thread {
    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread();

//    @Override
//    public void run() {
//        while (true) {
//
//            QQServer.ManoffMess(serverConnectClientThread.getUserId());
//        }
//    }
}
