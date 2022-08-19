package com.zml.qqcommon;

/**
 * @author 张民磊
 * @version 1.0
 */
public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1"; //登入成功
    String MESSAGE_LOGIN_FATL = "2";//登入失败
    String MESSAGE_COMM_MES = "3";//普通信息
    String MESSAGE_GET_ONLINE_FRIEND = "4";//要求返回在线用户列表
    String MESS_CLIENT_EXIT = "6";//客户端请求退出
    String MESS_RET_ONLINE_FRIEND = "5";//返回在线列表
    String MESS_TO_ALL_MES = "7";
    String MESS_FILS_MES = "8"; //发送文件
    String MESS_OFFMES_ = "9"; //
    String MESS_OFFCHAKAN = "10";
}
