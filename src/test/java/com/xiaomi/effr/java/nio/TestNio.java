/**   
 * @Title: 						TestNio.java 
 * @Package				com.xiaomi.effr.java.nio 
 * @Description:			TODO(用一句话描述该文件做什么) 
 * @author					ming.horizon@gmail.com
 * @date						Aug 26, 2013 10:28:25 AM 
 * @version					V1.0   
 */

package com.xiaomi.effr.java.nio;

import org.junit.Test;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author ianlin
 */
public class TestNio {

    @Test
    public void goSend() throws Exception {
        Socket socket = new Socket();
        InetAddress inetAddress = InetAddress.getByName("localhost");
        SocketAddress address = new InetSocketAddress(inetAddress, 8080);
        socket.connect(address);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        for (int idx = 0; idx < 100; idx++) {
            char[] content = "a".toCharArray();
            out.print(content);
        }
        out.flush();

        Thread.sleep(1000);
        out.println("bbbbbb");
        out.flush();

        out.close();

    }
}
