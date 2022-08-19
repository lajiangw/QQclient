package com.zml.qqserver.server;

import com.zml.Utils.Utility;
import com.zml.qqcommon.Message;
import com.zml.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author 张民磊
 * @version 1.0
 */
public class SendNewsToAllService implements Runnable {


    @Override
    public void run() {
        while (true) {
            System.out.println("请输入要推送的新闻~[输入 exit退出退出服务~]");
            String news = Utility.readString(100);
            if(news.equals("exit")){
                System.out.println("新闻消息推送退出~");
                break;
            }
            Message message = new Message();
            message.setSender("服务器");
            message.setContent(news);
            message.setSendTime(new Date().toString());
            message.setMesType(MessageType.MESS_TO_ALL_MES);
            System.out.println("服务器 对 大家说：" + message.getContent());

            HashMap<String, ServerConnectClientThread> hm = ManagerServerThrand.getHm();
            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                String onLineuser = iterator.next().toString();
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(hm.get(onLineuser).getSocket().getOutputStream());
                    objectOutputStream.writeObject(message);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
