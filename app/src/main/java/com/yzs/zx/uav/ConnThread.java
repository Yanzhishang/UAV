package com.yzs.zx.uav;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by ZS on 17-3-13.
 */
//创建线程
public class ConnThread extends Thread {

    public void run() {
        try {
            System.out.print("开启线程");
            //连接服务端
            //调用 socket 类链接要访问的ip 地址和端口
            Socket socket = new Socket("192.168.4.1", 333);
            //调用输入输出流 传递指令
            OutputStream outputStream = socket.getOutputStream();//从socket里获取输出流
            //发送连接指令
            outputStream.write("GEC\r\n".getBytes());
            //关闭通信
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
