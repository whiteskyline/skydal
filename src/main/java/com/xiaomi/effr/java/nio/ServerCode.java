/**   
 * @Title: 						ServerCode.java 
 * @Package				com.xiaomi.effe.java.nio 
 * @Description:			TODO(用一句话描述该文件做什么) 
 * @author					ming.horizon@gmail.com
 * @date						Aug 25, 2013 4:10:49 PM 
 * @version					V1.0   
 */

package com.xiaomi.effr.java.nio;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Set;

/**
 * @author ianlin
 */
public class ServerCode {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerCode.class);

    /**
     * 服务器端接收请求
     * 
     * @param argv
     * @throws Exception
     */
    public static void main(String argv[]) throws Exception {

        ServerSocketChannel sChannel = ServerSocketChannel.open();
        SocketAddress address = new InetSocketAddress(8080);
        sChannel.socket().bind(address);

        // 开启选择器
        Selector select = Selector.open();
        sChannel.configureBlocking(false);
        sChannel.register(select, SelectionKey.OP_ACCEPT);

        ByteBuffer buffer = ByteBuffer.allocateDirect(1280);
        char content[] = new char[1280];

        while (true) {
            int selected = select.select();
            LOGGER.info("selected:{}", selected);
            Set<SelectionKey> keySet = select.selectedKeys();
            if (keySet.isEmpty()) {
                continue;
            }
            LOGGER.info("select return, set size:{}", keySet.size());
            if (keySet != null && !keySet.isEmpty()) {
                for (SelectionKey key : keySet) {
                    LOGGER.info("one key");
                    if (key.isAcceptable()) {

                        LOGGER.info("accept");
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel acceptedChannel = serverChannel.accept();
                        if (acceptedChannel != null) {
                            acceptedChannel.configureBlocking(false);
                            acceptedChannel.register(select, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        } else {
                            System.err.println("accept is null");
                        }

                    }

                    if (key.isReadable()) {

                        LOGGER.info("read");
                        SocketChannel readChannel = (SocketChannel) key.channel();
                        if (0 != readChannel.read(buffer)) {
                            buffer.flip();
                            CharBuffer charBuffer = buffer.asCharBuffer();
                            while (charBuffer.hasRemaining()) {
                                int readCount = charBuffer.remaining() > content.length ? content.length : charBuffer.remaining();
                                charBuffer.get(content, 0, readCount);
                                try {
                                    System.out.println(Arrays.copyOf(content, readCount));
                                } catch (IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                    System.out.println("readcount" + readCount);
                                }
                            }
                        }

                    }

                    if (key.isWritable()) {

                        LOGGER.info("channel is writable.");

                    }

                }

            } else {

            }
        }

    }

    public static void readContent(SelectionKey key, ByteBuffer buffer) {
        Validate.isTrue(key.isReadable(), "key is not readable!");
        Validate.isTrue(buffer != null, "buffer is null");

    }
}
