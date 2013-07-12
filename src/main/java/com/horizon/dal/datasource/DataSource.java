/**
 * [Copyright]
 * @author ianlin
 * @date 2:25:01 PM
 */

package com.horizon.dal.datasource;

/**
 * @author ianlin
 */
public class DataSource {
    private DataSourceConfig config;
    private DbConnPool pool;

    /**
     * @return the config
     */
    public DataSourceConfig getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public void setConfig(DataSourceConfig config) {
        this.config = config;
    }

    /**
     * @return the pool
     */
    public DbConnPool getPool() {
        return pool;
    }

    /**
     * @param pool the pool to set
     */
    public void setPool(DbConnPool pool) {
        this.pool = pool;
    }

}
