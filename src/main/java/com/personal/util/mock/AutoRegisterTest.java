/**   
 * @Title: 						AutoRegisterTest.java 
 * @Package				com.personal.util.mock 
 * @Description:			该类给所有的继承类提供自动的Mock注册能力
 * @author					ming.horizon@gmail.com
 * @date						Aug 1, 2013 12:18:47 AM 
 * @version					V1.0   
 */

package com.personal.util.mock;



/**
 * @author ianlin
 */
public class AutoRegisterTest {
    public AutoRegisterTest() {
        SuperMockedAttributeFilter.register(this.getClass());
    }
}
