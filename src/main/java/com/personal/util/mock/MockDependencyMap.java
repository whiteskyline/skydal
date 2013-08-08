/**
 * [Copyright]
 * @author ianlin
 * @date 7:49:06 PM
 */

package com.personal.util.mock;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContextException;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * 记录依赖的存在等等
 * 
 * <pre>
 *      thread unsafe, just desidned for junit test environment. if used in other concurrent environment, please be careful about concurrent!!!
 * </pre>
 * 
 * @author ianlin
 */
public class MockDependencyMap {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockDependencyMap.class);

    Map<Class<?>, Class<?>> classMap = new HashMap<Class<?>, Class<?>>();

    /**
     * 为某个类添加其Mock实现
     * 
     * @param targetClass 目标类
     * @param implClass 目标类的Mock实现类
     */
    public void registerMockDependency(Class<?> targetClass, Class<?> implClass) throws ApplicationContextException {
        Validate.notNull(targetClass);
        Validate.notNull(implClass);

        if (!targetClass.isAssignableFrom(implClass)) {
            LOGGER.error("impl class {} is not suitable for base class {}", implClass, targetClass);
            throw new ApplicationContextException("impl class is not suitable for base class.");
        }
        classMap.put(targetClass, implClass);
    }

    /**
     * 删除某个目标类的mock实现
     * 
     * @param targetClass 目标类
     */
    public void removeMockDependency(Class<?> targetClass) {
        if (classMap.containsValue(targetClass)) {
            
        }
    }

    /**
     * 创建目标对象的Mock实现
     * 
     * @param targetClass
     * @return
     */
    public Object createMockDependency(Class<?> targetClass) {
        Validate.notNull(targetClass);

        if (targetClass.getSuperclass() != null) {
            if (classMap.containsKey(targetClass.getSuperclass())) {
                return getMockObject(classMap.get(targetClass.getSuperclass()));
            }
        }

        Class<?>[] interfaces = targetClass.getInterfaces();
        for (Class<?> interFace : interfaces) {
            if (classMap.containsKey(interFace)) {
                return getMockObject(classMap.get(interFace));
            }
        }

        if (classMap.containsKey(targetClass)) {
            return getMockObject(classMap.get(targetClass));
        }

        return null;

    }

    /**
     * @param clazz 需要进行实例化的Mock类型
     * @return 使用对应的
     * @throws BeansException 如果对应的Mock类型没有默认构造函数，那么抛出异常
     */
    protected Object getMockObject(Class<? extends Object> clazz) throws BeansException {
        Validate.notNull(clazz, "cannot get mock for null object");
        Constructor<? extends Object>[] cons = clazz.getConstructors();

        for (Constructor<? extends Object> con : cons) {
            if (con.getParameterTypes() == null || con.getParameterTypes().length == 0) {
                try {
                    return con.newInstance(new Object[0]);
                } catch (Exception e) {
                    // 这里抛出的四种异常，结果都是一样的，因此没有必要分别捕捉
                    throw new BeanCreationException(e.getMessage(), e);
                }
            }
        }

        throw new BeanCreationException(clazz.getName() + " does not have a default constructor");

    }

}
