/**
 * [Copyright]
 * @author ianlin
 * @date 8:10:42 PM
 */

package com.horizon.dal.skydal;

import com.horizon.dal.skydal.util.DAOGenUtil;

import org.apache.commons.lang.Validate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author ianlin 拦截所有DAO的执行方法，并返回相应的结果
 */
public class DAOProxy implements InvocationHandler {

    public void init(Class<?> targetInterface) {
        Validate.isTrue(DAOGenUtil.isDAO(targetInterface));
        // 载入方法信息

    }

    /*
     * (non-Javadoc)
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return "proxy";
    }

}
