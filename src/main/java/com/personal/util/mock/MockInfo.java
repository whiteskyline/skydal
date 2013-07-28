/**
 * [Copyright]
 * @author ianlin
 * @date 2:02:02 PM
 */
package com.personal.util.mock;

/**
 * @author ianlin
 *
 */
public @interface MockInfo {
    String name();                      // 需要进行mock的属性的名称
    Class<? extends Object> clazz();    // 需要进行切入的mock实现类型
}
