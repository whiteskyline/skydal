/**
 * [Copyright]
 * @author ianlin
 * @date 3:32:44 PM
 */

package com.horizon.util.mock;

import com.personal.util.mock.AutoRegisterTest;
import com.personal.util.mock.MockInfo;
import com.personal.util.mock.Mocked;
import com.personal.util.mock.SuperMockedAttributeFilter;
import com.personal.util.mock.comp.MockClass;
import com.personal.util.mock.comp.UserClass;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 对于标签的作用进行合理地测试
 * 
 * @author ianlin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MockAnnotationUT extends AutoRegisterTest {

    private Logger LOGGER = LoggerFactory.getLogger(MockAnnotationUT.class);

    @Mocked(infos = {
        @MockInfo(name = "base", clazz = MockClass.class)
    })
    @Autowired
    private UserClass user;

    /**
     * 对于生成的DAO类型的interface实现的mock进行测试
     */
    @Test
    public void testGeneratedInterfaceImplTest() {
        LOGGER.warn("user`s value:{}", user.getValue());
    }
    
    
}
