/**
 * [Copyright]
 * @author ianlin
 * @date 9:58:34 PM
 */

package com.horizon.dal.skydal.util;

import com.horizon.dal.skydal.anno.DAO;
import com.horizon.dal.skydal.anno.SQL;

import java.lang.reflect.Method;

/**
 * @author ianlin
 */
public class DAOGenUtil {
    /**
     * 判断一个类是否是DAO
     * 
     * @param targetClass
     * @return
     */
    public static boolean isDAO(Class<?> targetClass) {
        if (targetClass == null) {
            return false;
        }

        return targetClass.getAnnotation(DAO.class) != null;

    }

    /**
     * 判断一个方法是不是对应的SQL查询方法
     * 
     * @param method
     * @return
     */
    public static boolean isSQL(Method method) {
        if (method == null) {
            return false;
        }

        return method.getAnnotation(SQL.class) != null;
    }
}
