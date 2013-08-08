/**
 * [Copyright]
 * @author ianlin
 * @date 3:08:01 PM
 */

package com.horizon.dal.skydal.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 标记需要进行内容解析的属性,這個類專門用於Config當中，並不適用與其他的描述
 * 
 * @author ianlin
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedDecode {
    boolean need() default true; // true表示這個值需要進行正則表達式代替，false表示這個屬性不應該進行正則表達式代替

    String targetName() default ""; // 表示最终解析之后的目标属性名称
}
