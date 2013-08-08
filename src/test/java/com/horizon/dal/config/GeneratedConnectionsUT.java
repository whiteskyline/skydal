/**
 * [Copyright]
 * @author ianlin
 * @date 8:52:09 PM
 */

package com.horizon.dal.config;

import com.horizon.dal.datasource.DataSourceConfig;
import com.horizon.dal.skydal.anno.NeedDecode;
import com.horizon.dal.test.util.ConfigUtil;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

/**
 * 为生成型数据连接配置进行测试
 * 
 * @author ianlin
 */
public class GeneratedConnectionsUT {

    /**
     * 测试类的反射机制中获取某个标签的方法
     */
    @Test
    public void testGetAnnotation() throws Exception {
        Field field = GeneratedConnections.class.getDeclaredField("pwd");
        NeedDecode need = field.getAnnotation(NeedDecode.class);
        System.out.println(need);
        Annotation annos[] = field.getAnnotations();
        for (Annotation anno : annos) {
            System.out.println(anno);
        }

        Annotation decAnnos[] = field.getDeclaredAnnotations();
        for (Annotation anno : decAnnos) {
            System.out.println(anno);
        }

    }

    /**
     * 測試正常生成的
     * 
     * @throws Exception
     */
    @Test
    public void generatedConnectionsTest() throws Exception {
        final String pairsFilename = "src/test/resources/config_file/simple_pairs.xml";
        Pairs pairs = new Pairs();
        pairs = ConfigUtil.getFromFile(pairsFilename, pairs);

        final String genConnFilename = "src/test/resources/config_file/simple_generated_connections.xml";
        GeneratedConnections genConn = new GeneratedConnections();
        genConn = ConfigUtil.getFromFile(genConnFilename, genConn);

        List<DataSourceConfig> dataSourceList = genConn.generateConnections(Pairs.getPairsMap(Arrays.asList(pairs)));
        Assert.assertTrue(0 != dataSourceList.size());
    }

}
