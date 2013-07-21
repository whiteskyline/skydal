/**
 * [Copyright]
 * @author ianlin
 * @date 4:21:22 PM
 */

package com.horizon.dal.datasource;

import com.horizon.dal.config.Connections;
import com.horizon.dal.config.GeneratedConnections;
import com.horizon.dal.config.Pair;
import com.horizon.dal.config.Pairs;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ianlin
 */
@Element(name = "connection-pool")
public class ConnectionsConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionsConfig.class);

    private static final float DEF_MAP_CAPACITY_RATIO = 1.4f; // 1.4倍的存儲量，儘量減少碰撞

    @Element(name = "connections", required = false)
    private Connections connections;

    @ElementList(required = false, inline = true, entry = "pairs")
    private List<Pairs> pairs;

    @Element(name = "genConnections", required = false)
    private GeneratedConnections genConnections;

    /**
     * 解析动态标签的结果，并且生成Connections的配置目測使用TreeMap是比較好的選擇
     * 
     * @return
     */
    public HashMap<String, DataSourceConfig> getConnections() {

        // 計算最終的生成的集合的大小
        int capacity = 0;
        if (null != connections && null != connections.getConnections()) {
            capacity += connections.getConnections().size();
        }
        if (null != genConnections) {
            capacity += genConnections.getIdxRange();
        }

        HashMap<String, DataSourceConfig> dataSourceMap = new HashMap<String, DataSourceConfig>((int) (capacity * DEF_MAP_CAPACITY_RATIO));

        if (null != connections) {
            for (DataSourceConfig config : connections.getConnections()) {
                dataSourceMap.put(config.getName(), config);
            }
        }

        if (null != genConnections) {

        }

        return null;

    }

    /**
     * 獲取轉換之後的映射表 :TODO 繼續完成這部分的轉換
     * 
     * @return
     */
    protected Map<String, Map<Integer, Pair>> getKeyMap() {
        return null;
    }

    /**
     * @return
     */
    protected List<Pairs> getPairs() {
        return pairs;
    }

}
