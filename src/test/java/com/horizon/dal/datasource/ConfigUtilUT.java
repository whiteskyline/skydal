/**
 * [Copyright]
 * @author ianlin
 * @date 4:31:19 PM
 */

package com.horizon.dal.datasource;

import com.horizon.dal.test.util.ConfigUtil;

import org.junit.Test;

import junit.framework.Assert;

/**
 * @author ianlin
 */
public class ConfigUtilUT {

    @Test
    public void testConnectionsConfig() throws Exception {
        final String connectionsFilename = "src/test/resources/config_file/connections.xml";
        ConnectionsConfig config = new ConnectionsConfig();
        config = ConfigUtil.getFromFile(connectionsFilename, config);
        Assert.assertNotNull(config.getConnections());
        Assert.assertTrue(config.getConnections().size() != 0);
    }

    @Test
    public void testPairsConfig() throws Exception {
        final String connectionsFilename = "src/test/resources/config_file/pairs.xml";
        ConnectionsConfig config = new ConnectionsConfig();
        config = ConfigUtil.getFromFile(connectionsFilename, config);
        Assert.assertNotNull(config.getPairs());
    }
}
