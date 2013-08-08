/**   
 * @Title: 						CharSetTest.java 
 * @Package				com.horizon.dal.unknown.test 
 * @Description:			对于字符集的问题进行测试
 * @author					ming.horizon@gmail.com
 * @date						Aug 8, 2013 1:43:53 AM 
 * @version					V1.0   
 */

package com.horizon.dal.unknown.test;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

/**
 * @author ianlin
 */
public class CharSetTest {

    /**
     * 获得一个byte数组的字符表示
     * 
     * @return
     */
    public String getStringOfBytes(byte[] bytes) {
        if (null == bytes) {
            return "null";
        }

        return ArrayUtils.toString(bytes);
        
    }

    /**
     * 测试exist方法
     * 
     * @throws Exception
     */
    @Test
    public void testCharSet() throws Exception {
        String content = "中文";
        System.out.println("UTF-8" + getStringOfBytes(content.getBytes("UTF-8")));
        System.out.println("GBK" + getStringOfBytes(content.getBytes("GBK")));
        System.out.println("ISO-8859-1" + getStringOfBytes(content.getBytes("ISO-8859-1")));
        System.out.println(new String(content.getBytes("GBK"), "UTF-8"));
    }

}
