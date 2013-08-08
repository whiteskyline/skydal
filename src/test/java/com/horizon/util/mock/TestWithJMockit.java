/**   
 * @Title: 						TestWithJMockit.java 
 * @Package				com.horizon.util.mock 
 * @Description:			使用JMockit进行测试
 * @author					ming.horizon@gmail.com
 * @date						Aug 1, 2013 8:27:31 AM 
 * @version					V1.0   
 */

package com.horizon.util.mock;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

/**
 * @author ianlin
 */
public class TestWithJMockit {
    @Test
    public void testJMockit() throws Exception {
        @SuppressWarnings("unused")
        MockUp mock = new MockUp<MyServiceImpl>() {
            @Mock
            public String connectServer() {
                return "Mock";
            }
        };

        // 测试本地代码
        MyService myservice = new MyServiceImpl();
        System.out.println(myservice.connectServer());

    }
}
