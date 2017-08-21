/**
 * [Copyright]
 * @author ianlin
 * @date 3:46:51 PM
 */

package com.horizon.dal.unknown.test;

import com.horizon.dal.datasource.DataSourceConfig;

import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * @author ianlin SimpleXML的过程
 */
public class SimpleXmlUT {

    /**
     * 测试字符串读取
     * 
     * @throws Exception
     */
    @Test
    public void testReadString() throws Exception {
        Serializer selializer = new Persister();
        String content = "<connection name=\"mms_db_47\" url=\"jdbc:mysql://192.168.1.58/mms_db_47?useUnicode=true&amp;characterEncoding=ascii\" usr=\"s8HHZ4usvD6nuNX8rc/fag==\" pwd=\"JCqcZ5NkKthFALVD0mhBkqA9eyrDv4W81wyp/X+sqB1WdZ9Bcwoye6HUpzxduK3C\" role=\"master\" />";
        DataSourceConfig config = new DataSourceConfig();
        selializer.read(config, content);
    }
}
