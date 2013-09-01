/**   
 * @Title: 						NioTest.java 
 * @Package				com.horizon.nio.test 
 * @Description:			对于Nio的功能进行测试
 * @author					ming.horizon@gmail.com
 * @date						Aug 17, 2013 8:03:03 PM 
 * @version					V1.0   
 */

package com.horizon.nio.test;

import org.apache.commons.lang.Validate;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * @author ianlin
 */
public class BufferTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BufferTest.class);

    /**
     * ByteBuffer的方法列表 方法名 注释 （创建型方法） allocateDirect 申请直接缓冲区 allocate 申请间接缓冲区 wrap 使用外部申请的空间，作为缓冲区的数据存储地址，并创建缓冲区对象
     * （拷贝型方法）（都使用了原来缓冲区的存储空间） slice 将当前的有效空间拷贝成为一个缓冲区，使用一样的存储区域 duplicate 拷贝一个缓冲区 asReadOnlyBuffer
     * 创建一个新的缓冲区，但是这个缓冲区是只能读取的，并且相应的position等值，和我们的旧的缓冲区的值一样 （数据方法）能够用来对缓冲区中的数据进行操作 get put
     * get可以指定一个index，但是这个index不能够超出缓冲区的limit (状态方法)能够探知一下ByteBuffer的内部状态 hasArray isDirect等等
     */

    private byte[] data;

    /**
     * 准备其中的数据
     */
    @Before
    public void prepare() {
        data = new byte[128];
        for (int idx = 0; idx < 128; idx++) {
            data[idx] = (byte) idx;
        }
    }

    /**
     * 将某个缓冲区当中的所有数据都读出来
     * 
     * @param buffer
     * @param target
     */
    public static int readData(ByteBuffer buffer, byte[] target) {
        Validate.notNull(buffer);
        Validate.notNull(target);
        int idx = 0;
        while (buffer.hasRemaining()) {
            target[idx++] = buffer.get();
        }
        return idx;
    }

    /**
     * 
     */

    /**
     * 打印某个
     */

    /**
     * 对于所有普通的Buffer操作方法对于四个值的影响进行测试
     * 
     * @throws Exception
     */
    @Test
    public void testFunctions() throws Exception {
        byte content = (byte) 0xab;
        ByteBuffer bb = ByteBuffer.allocate(1280);
        printInnerState(bb);
        bb.put(content);
        printInnerState(bb);
        bb.flip();
        printInnerState(bb);
        bb.flip();
        printInnerState(bb);

        // 插入之后是否要反转
        bb.limit(1280);
        bb.put(data);
        printInnerState(bb);
        bb.flip();
        
        ByteBuffer newBb = bb.slice();
        printInnerState(newBb);
        bb.get();
        
        bb.flip();
        bb.put((byte)0x35);
        printInnerState(bb);
        printInnerState(newBb);
        System.out.println(newBb.get());
        
        bb.flip();
        bb.limit(1280);
        printInnerState(bb);
        bb.put(data);
        bb.flip();
        printInnerState(bb);
        bb.get();
        bb.compact();
        printInnerState(bb);
        
        ByteBuffer rdBuffer = bb.asReadOnlyBuffer();
        printInnerState(rdBuffer);
        
        bb.put(data);
        printInnerState(bb);
        printInnerState(rdBuffer);
        System.out.println(rdBuffer.get());
        printInnerState(bb);
        printInnerState(rdBuffer);

    }

    /**
     * 打印某个Buffer的内部的状态
     */
    public static void printInnerState(ByteBuffer buffer) throws Exception {
        LOGGER.debug("init value, position:{}, limit:{}, capa:{}, is direct:{}, readOnly:{}", new Object[] {
            buffer.position(), buffer.limit(), buffer.capacity(), buffer.isDirect(), buffer.isReadOnly()
        });
    }
}
