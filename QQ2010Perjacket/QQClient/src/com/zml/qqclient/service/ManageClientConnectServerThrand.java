package com.zml.qqclient.service;

import java.util.HashMap;

/**
 * @author 张民磊
 * @version 1.0
 * 管理客户端连接到服务器端的线程
 */
public class ManageClientConnectServerThrand {
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();
//将多个线程放入到hashmap中，key是用户id value是线程


    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
        hm.put(userId,clientConnectServerThread);
//        将某线程放入hm集合中
    }
//通过对应id得到对应的线程
    public static ClientConnectServerThread getClientConnectServerThread(String userId){
        return hm.get(userId);
    }

}
