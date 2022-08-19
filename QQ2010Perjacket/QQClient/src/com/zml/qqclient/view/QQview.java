package com.zml.qqclient.view;

import com.zml.qqclient.service.FileClientService;
import com.zml.qqclient.service.MessageClientServer;
import com.zml.qqclient.service.UserClientService;
import com.zml.qqclient.utiliy.*;

import java.io.FileInputStream;

/**
 * @author 张民磊
 * @version 1.0
 * 客户端的登入界面
 */
public class QQview {

    //    显示主菜单的方法
    private UserClientService userClientService = new UserClientService();//这个对象是用来登入服务器和注册用户
    private MessageClientServer messageClientServer = new MessageClientServer();//封装用户之间的私聊群聊群发的方法
    private FileClientService fileClientService = new FileClientService();
    private boolean loop = true;
    private String key = "";

    public static void main(String[] args) {
        new QQview().mainMeun();
    }

    public void mainMeun() {
        while (loop) {
            System.out.println("=====================QQ2010=====================");
            System.out.println("\t\t 1.登入系统");
            System.out.println("\t\t 9.退出系统");
            System.out.print("输入你的选择： ");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.println("请输入用户名");
                    String userId = Utility.readString(16);
                    System.out.println("请输入密码");
                    String passWrod = Utility.readString(16);
//                    验证密码是否正确
                    if (userClientService.checkUser(userId, passWrod)) {
                        while (loop) {
                            System.out.println("\"=====================欢迎登入QQ2010( 用户名" + userId + " ) =====================\"");
                            System.out.println("\t\t 1.显示在线用户列表");
                            System.out.println("\t\t 2.群发消息");
                            System.out.println("\t\t 3.私聊消息");
                            System.out.println("\t\t 4.发送文件");
                            System.out.println("\t\t 5.离线留言");
                            System.out.println("\t\t 9.系统退出");
                            System.out.print("请输入你的选择： ");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    userClientService.onlinFriendList();
                                    break;
                                case "2":
                                    System.out.println("请输入对大家说的话： ");
                                    String s = Utility.readString(100);
                                    messageClientServer.sendMessageAll(s, userId);
                                    break;
                                case "3":
                                    System.out.println("在线消息1  离线消息2");
                                    String string = Utility.readString(1);
                                    if (string.equals("1")) {
                                        System.out.println("输入想要私聊的用户（在线 ：）");
                                        String getuserId = Utility.readString(16);
                                        System.out.println("输入想说的话：");
                                        String getContent = Utility.readString(100);
                                        messageClientServer.sendMessageToOne(getContent, userId, getuserId);
                                    } else {
                                        System.out.println("输入想要私聊的用户（离线 ：）");
                                        String getuserId = Utility.readString(16);
                                        System.out.println("输入想说的话：");
                                        String getContent = Utility.readString(100);
                                        messageClientServer.sendMEssgoOff(getContent, userId, getuserId);
                                    }
                                    break;
                                case "4":
                                    System.out.println("请输入发送文件的用户(在线)：");
                                    String getuserId = Utility.readString(16);
                                    System.out.println("请输入发送文件的路径（格式：d\\xx.txt）：");
                                    String src = Utility.readString(100);
                                    System.out.println("请输入发送文件对方的路径：");
                                    String dest = Utility.readString(100);
                                    fileClientService.sendFileToOne(src, dest, userId, getuserId);
                                    break;
                                case "5":
                                    System.out.println("查看自己的离线留言");
                                    messageClientServer.seeOff(userId);
                                    break;
                                case "9":
                                    userClientService.logout();
                                    loop = false;
                                    break;
                            }

                        }
                    } else {
                        System.out.println("登入失败，请检查账号密码。");
                    }


                    break;

                case "9":
                    System.out.println("=====================系统退出..=====================");
                    loop = false;

            }
        }
    }
}
