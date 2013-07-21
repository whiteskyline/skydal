/**
 * [Copyright]
 * @author ianlin
 * @date 5:21:14 PM
 */

package com.horizon.dal.skydal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 反射工具类
 * 
 * @author ianlin
 */
public class ReflectUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * 表示在反射的使用过程中出现的误用情况
     * 
     * @author ianlin
     */
    public static final class ReflectException extends Exception {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public static final String DEF_SOLID_REFLE_MESSAGE = "solid reflect exception";
        public static final String DEF_SOFT_REFLE_MESSAGE = "soft reflect exception";

        public ReflectException(String message) {
            super(message);
        }
    }

    public static final void setValue(Object obj, Field field, Object value) throws ReflectException {
        try {
            field.set(obj, value);
        } catch (IllegalArgumentException e) {
            LOGGER.error("illegal argument exception", e);
            throw new ReflectException(ReflectException.DEF_SOFT_REFLE_MESSAGE);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
