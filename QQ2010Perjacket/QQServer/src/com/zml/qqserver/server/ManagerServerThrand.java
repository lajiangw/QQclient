package com.zml.qqserver.server;

import com.zml.qqcommon.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 张民磊
 * @version 1.0
 * 该类用于管理和客户端通信的线程
 */
public class ManagerServerThrand {

    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    public static HashMap<String, ServerConnectClientThread> getHm() {
        return hm;
    }

    //    添加线程到hm集合中
    public static void addCientThrand(String userId, ServerConnectClientThread serverConnectClientThread) {
        hm.put(userId, serverConnectClientThread);
    }

    public static ServerConnectClientThread getCilentThrand(String userId) {
        return hm.get(userId);
    }

    public static String getOnlineUser() {
        Iterator iterator = hm.keySet().iterator();
        String onlineUser = "";
        while (iterator.hasNext()) {
            onlineUser += iterator.next().toString() + " ";
        }
        return onlineUser;
    }

    public static void removeServerConnectClientThrand(String userId) {
        hm.remove(userId);
    }
}
