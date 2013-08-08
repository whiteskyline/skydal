/**
 * [Copyright]
 * @author ianlin
 * @date 1:22:30 PM
 */

package com.personal.util.mock;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * @author ianlin 检查目标类的属性，并且为其装配相应的对象目标 在初始化之前，将相应的Annotation的信息给取出掉，然后在结束之后，直接将相应的mock填上去
 */
@Component
public class SuperMockedAttributeFilter implements InstantiationAwareBeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuperMockedAttributeFilter.class);
    private static final String DOT_EXPRESSION = "\\.";
    private static final MockDependencyMap dependencyMap = new MockDependencyMap();

    public static void register(Class<?> beanClass) {
        new SuperMockedAttributeFilter().postProcessBeforeInstantiation(beanClass, null);
    }

    /**
     * 分析对应类的Mock配置，如果这个类本身是需要mock的，那么就直接生成对应的mock实现
     */
    public Object postProcessBeforeInstantiation(@SuppressWarnings("rawtypes") Class beanClass, String beanName) throws BeansException {

        Validate.notNull(beanClass, "Target Class Should Not Be Null.");

        // 查询这个类是否在对应的Mock类型依赖，如果是，常见其依赖对象，并删除
        Object obj = dependencyMap.createMockDependency(beanClass);
        if (obj != null) {

            // 删除依赖
            dependencyMap.removeMockDependency(obj.getClass());

            // 对于已经被mock掉的内容，它是不会存在mock状况的,因此不需要在进行mock分析了
            return obj;

        }

        // 筛选标记了进行Mock的属性，然后登记Mock的对象类型
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            Mocked anno = field.getAnnotation(Mocked.class);

            if (anno != null) {
                registerMockInfos(field, anno.infos());
            }
        }

        return null;

    }

    /**
     * 登记所进行的依赖的项目，产生的时候进行去除即可
     * 
     * @throws Exception
     */
    public void registerMockInfos(Field field, MockInfo[] infos) {
        Validate.notNull(infos);
        Validate.notNull(field);

        for (MockInfo info : infos) {
            Class<?> targetClass = getTargetClass(field, info.name());
            dependencyMap.registerMockDependency(targetClass, info.clazz());
        }

    }

    /**
     * 获取ognl表达式所导航到的属性的类型
     * 
     * @param field
     * @param ognlExpre
     * @return
     */
    public Class<? extends Object> getTargetClass(Field field, String ognlExpre) throws ApplicationContextException {

        Validate.isTrue(StringUtils.isNotBlank(ognlExpre));

        Class<?> parentObjectClass = field.getType();
        String[] dotSplits = ognlExpre.split(DOT_EXPRESSION);

        // 循环往下查找对应的属性
        for (String nextFieldName : dotSplits) {
            try {
                parentObjectClass = parentObjectClass.getDeclaredField(nextFieldName).getType();
            } catch (SecurityException e) {
                LOGGER.error("security exception.", e);
                throw new ApplicationContextException("security exception", e);
            } catch (NoSuchFieldException e) {
                LOGGER.error("field {} cannot be found in class {}.", nextFieldName, parentObjectClass.toString());
                throw new ApplicationContextException("no such field exception", e);
            }
        }

        return parentObjectClass;

    }

    /**
     * 为测试开放对于dependencyMap的访问权限
     */
    protected MockDependencyMap getDependencyMap() {
        return dependencyMap;
    }

    /**
     * 复写以下方法只是为了能够编译
     */
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return false;
    }

    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName)
                                                                                                                               throws BeansException {
        return pvs;
    }

    /**
     * 可能这里不需要做特别多的处理
     */
    public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException {
        return arg0;
    }

    /**
     * 在对象进行初始化之前，就更改相应的Mock属性
     */
    public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException {
        return arg0;
    }

}
