/**
 * [Copyright]
 * @author ianlin
 * @date 8:38:09 PM
 */

package com.horizon.util.mock;

import com.personal.util.mock.MockDependencyMap;
import com.personal.util.mock.comp.BaseClass;
import com.personal.util.mock.comp.ImplClass;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContextException;

import java.util.HashMap;

/**
 * 针对MockDependencyMap这个类的所有接口进行测试
 * 
 * @author ianlin
 */
public class MockDependencyMapUT {
    private MockDependencyMap map = new MockDependencyMap();

    /**
     * 测试正常情况下的
     * 
     * @throws Exception
     */
    @Test
    public void testPositive() throws Exception {
        map.registerMockDependency(BaseClass.class, ImplClass.class);
        Assert.assertNotNull(map.createMockDependency(BaseClass.class));
        map.removeMockDependency(BaseClass.class);
    }

    /**
     * 测试
     * 
     * @throws Exception
     */
    @Test(expected = ApplicationContextException.class)
    public void testUnSuitableNeg() throws Exception {
        map.registerMockDependency(BaseClass.class, HashMap.class);
    }

    /**
     * 测试无法进行实例化的类
     * 
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCannotCreateImpl() throws Exception {
        map.createMockDependency(BaseClass.class);
    }
}
