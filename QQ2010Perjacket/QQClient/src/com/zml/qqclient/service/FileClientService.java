package com.zml.qqclient.service;

import com.zml.qqcommon.Message;
import com.zml.qqcommon.MessageType;

import java.io.*;

/**
 * @author 张民磊
 * @version 1.0
 * 完成对文件的传输
 */
public class FileClientService {

    public void sendFileToOne(String src, String dast, String sendId, String geterId) {
        Message message = new Message();
        message.setMesType(MessageType.MESS_FILS_MES);
        message.setSender(sendId);
        message.setGetSender(geterId);
        message.setSrc(src);
        message.setData(dast);
        FileInputStream fileInputStream = null;
        byte[] fileBytes = new byte[(int) new File(src).length()];
        try {
            fileInputStream = new FileInputStream(src);

            fileInputStream.read(fileBytes);
            message.setFileByte(fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println(sendId + " 给 " + geterId + "发送文件：" + src + "到对方电脑" + dast);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThrand.getClientConnectServerThread(sendId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
