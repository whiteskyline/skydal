/**
 * [Copyright]
 * @author ianlin
 * @date 4:21:22 PM
 */

package com.horizon.dal.datasource;

import com.horizon.dal.config.Connections;
import com.horizon.dal.config.GeneratedConnections;
import com.horizon.dal.config.Pairs;

import org.simpleframework.xml.Element;

/**
 * @author ianlin
 */
@Element(name = "connection-pool")
public class ConnectionsConfig {
    @Element(name = "connections", required = false)
    private Connections connections;

    @Element(name = "pairs", required = false)
    private Pairs pairs;

    @Element(name = "genConnections", required = false)
    private GeneratedConnections genConnections;

    /**
     * 解析动态标签的结果，并且生成Connections的配置
     * 
     * @return
     */
    public Connections getConnections() {
        return connections;
    }

    /**
     * @return
     */
    protected Pairs getPairs() {
        return pairs;
    }

}
