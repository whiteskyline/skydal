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
import com.horizon.dal.skydal.excp.DalConfigException;
import com.horizon.dal.skydal.util.MapUtil;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author ianlin
 */
@Element(name = "connection-pool")
public class ConnectionsConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionsConfig.class);

    // 固态的Connection的配置
    @Element(name = "connections", required = false)
    private Connections connections;

    
    @ElementList(required = false, inline = true, entry = "pairs")
    private List<Pairs> pairs;

    @Element(name = "genConnections", required = false)
    private GeneratedConnections genConnections;

    /**
     * 解析动态标签的结果，并且生成Connections的配置目測使用TreeMap是比較好的選擇
     * 
     * @return 返回对应的DataSource名称和DataSourceConfig的映射
     * @throws DalConfigException
     * 
     */
    public HashMap<String, DataSourceConfig> getConnections() throws DalConfigException {

        // 計算最終的生成的集合的大小
        int capacity = 0;
        if (null != connections && null != connections.getConnections()) {
            capacity += connections.getConnections().size();
        }
        if (null != genConnections) {
            capacity += genConnections.getIdxRange();
        }

        HashMap<String, DataSourceConfig> dataSourceMap = new HashMap<String, DataSourceConfig>(MapUtil.getSuggestedMapSize(capacity));

        if (null != connections) {
            for (DataSourceConfig config : connections.getConnections()) {
                dataSourceMap.put(config.getName(), config);
            }
        }

        // 进行代入值的替换，并且生成相应的Connections的信息
        if (null != genConnections) {
            for (DataSourceConfig config : genConnections.generateConnections(getKeyMap())) {
                
                // 检查一下是否已经有已经存在的相应DataSourceConfig, 如果已经存在了，那么应该抛出一个DalConfigException
                if (dataSourceMap.containsKey(config.getName())) {
                    LOGGER.error("duplicated data source config name in generated connections:{}", config.getName());
                    throw new DalConfigException("duplicated connection name.");
                }
                
                dataSourceMap.put(config.getName(), config);
            }
        }

        return dataSourceMap;

    }

    /**
     * 獲取轉換之後的映射表，也就是完成所有Pairs到对应Map的转换
     * 
     * @return
     */
    protected Map<String, TreeMap<Integer, Pair>> getKeyMap() {
        return Pairs.getPairsMap(getPairs());
    }
    
    /**
     * @return
     */
    protected List<Pairs> getPairs() {
        return pairs;
    }

}
