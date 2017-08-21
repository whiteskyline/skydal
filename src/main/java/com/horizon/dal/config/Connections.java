/**
 * [Copyright]
 * @author ianlin
 * @date 6:08:28 PM
 */

package com.horizon.dal.config;

import com.horizon.dal.datasource.DataSourceConfig;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 * @author ianlin
 */
@Element(name = "connections")
public class Connections {
    @ElementList(inline = true, entry = "connection")
    private List<DataSourceConfig> connections;

    public List<DataSourceConfig> getConnections() {
        return connections;
    }
}
