/**   
 * @Title: 						TableConfigUnitTest.java 
 * @Package				com.horizon.dal.datasource 
 * @Description:			对于TableConfig进行读取测试
 * @author					ming.horizon@gmail.com
 * @date						Aug 4, 2013 6:40:17 PM 
 * @version					V1.0   
 */

package com.horizon.dal.datasource;

import com.horizon.dal.test.util.ConfigUtil;

import org.junit.Test;

import junit.framework.Assert;

/**
 * @author ianlin
 */
public class TableConfigUnitTest {
    /**
     * 测试读取相应的配置
     * 
     * @throws Exception
     */
    @Test
    public void testReadConfig() throws Exception {
        String tableConfigFilename = "src/test/resources/config_file/simple_table.xml";
        TableConfig config = new TableConfig();
        config = ConfigUtil.getFromFile(tableConfigFilename, config);
        Assert.assertNotNull(config);
        Assert.assertEquals(2, config.getPartitions().size());
    }
}
