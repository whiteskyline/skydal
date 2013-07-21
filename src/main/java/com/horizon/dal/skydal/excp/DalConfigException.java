/**
 * [Copyright]
 * @author ianlin
 * @date 4:43:12 PM
 */

package com.horizon.dal.skydal.excp;

/**
 * @author ianlin 对于配置过程中出现的错误进行表示
 */
public class DalConfigException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MESSAGE = "invalid config.";

    public DalConfigException() {
        super(DEFAULT_MESSAGE);
    }

    public DalConfigException(String message) {
        super(message);
    }

    public DalConfigException(String message, Throwable excp) {
        super(message, excp);
    }

    public DalConfigException(Throwable excp) {
        super(excp);
    }

}
