/**
 * [Copyright]
 * @author ianlin
 * @date 2:25:21 PM
 */

package com.horizon.dal.datasource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author ianlin
 */
public class DbConnPool extends GenericObjectPool {

    private static final Logger logger = LoggerFactory.getLogger(DbConnPool.class);

    private static final int DEF_MAX_ACTIVE = 10;
    private static final byte DEX_EXHAUSTED = GenericObjectPool.WHEN_EXHAUSTED_GROW; // 当超出连接限制的时候，重新创建请求
    private static final int DEF_MAX_WAIT = 100; // 等待获取链接最大的超时时间
    private static final int DEF_MAX_IDLE = 10; // 连接池当中的最大空闲
    private static final int DEF_MIN_IDLE = 10; //
    private static final boolean DEF_TEST_ON_BORROW = false;
    private static final boolean DEF_TEST_ON_RETURN = false;
    private static final long DEF_EVICTION_MILLS = 10 * 1000; // 过期的检查间隔
    private static final int EVI_CHECK_PER_TEST = 5; // 每次检查多少个对象是否已经过期
    private static final int DEF_MIN_EVIT_IDLE_TIME = 2000; // 空闲多久时间可以过期
    private static final boolean DEF_TEST_ON_IDLE = true; // 实现对于空闲对象进行有效性检查

    private DataSourceConfig dataSourceConfig;

    public static GenericObjectPool.Config getDefConfig() {
        Config config = new Config();

        config.maxActive = DEF_MAX_ACTIVE;
        config.whenExhaustedAction = DEX_EXHAUSTED;
        config.maxWait = DEF_MAX_WAIT;
        config.maxIdle = DEF_MAX_IDLE;
        config.minIdle = DEF_MIN_IDLE;
        config.testOnBorrow = DEF_TEST_ON_BORROW;
        config.testOnReturn = DEF_TEST_ON_RETURN;
        config.timeBetweenEvictionRunsMillis = DEF_EVICTION_MILLS;
        config.numTestsPerEvictionRun = EVI_CHECK_PER_TEST;
        config.minEvictableIdleTimeMillis = DEF_MIN_EVIT_IDLE_TIME;
        config.testWhileIdle = DEF_TEST_ON_IDLE;

        return config;
    }

    @SuppressWarnings("deprecation")
    // 消除setFactory的警告
    public DbConnPool(Config config, DataSourceConfig dataSourceConfig) {
        Validate.notNull(config);
        Validate.notNull(dataSourceConfig);
        Validate.isTrue(!StringUtils.isEmpty(dataSourceConfig.getUrl()));
        Validate.isTrue(!StringUtils.isEmpty(dataSourceConfig.getUsr()));
        Validate.isTrue(StringUtils.isEmpty(dataSourceConfig.getPwd()));

        this.setConfig(config);

        this.setFactory(new ConnectionFactory());
    }

    private class ConnectionFactory implements PoolableObjectFactory {

        private static final String UserKey = "user";
        private static final String PasswordKey = "password";
        private static final String ConnectionTimtOutKey = "connectTimeout";
        private static final String SocketTimeOutKey = "socketTimeout";
        private static final String EnableQueryTimeOutKey = "enableQueryTimeouts";

        private static final int DEF_CONN_TIMEOUT = 100;
        private static final int DEF_QUERY_TIMEOUT = 2000;
        private static final int DEF_VALID_TIMEOUT = 100;

        public Object makeObject() throws Exception {
            Properties props = new Properties();

            props.put(UserKey, dataSourceConfig.getUsr());
            props.put(PasswordKey, dataSourceConfig.getPwd());
            props.put(EnableQueryTimeOutKey, "true");
            props.put(ConnectionTimtOutKey, String.valueOf(DEF_CONN_TIMEOUT));
            props.put(SocketTimeOutKey, String.valueOf(DEF_QUERY_TIMEOUT));

            return DriverManager.getConnection(dataSourceConfig.getUrl(), props);
        }

        public void destroyObject(Object obj) throws Exception {

            if (obj == null) {
                return;
            }

            if (obj instanceof Connection) {
                ((Connection) obj).close();
            } else {
                logger.error("return in connection object:{}", obj);
            }
        }

        public boolean validateObject(Object obj) {

            if (obj == null) {
                return false;
            }

            if (obj instanceof Connection) {
                try {
                    return ((Connection) obj).isValid(DEF_VALID_TIMEOUT);
                } catch (SQLException e) {
                    logger.error("cannot validate connection.", e);
                    return false;
                }
            } else {
                return false;
            }

        }

        public void activateObject(Object obj) throws Exception {
            // never active a connection
            if (!validateObject(obj)) {
                throw new Exception("invalid activete action.");
            }

        }

        public void passivateObject(Object obj) throws Exception {

        }

    }

}
