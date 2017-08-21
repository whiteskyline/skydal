/**
 * [Copyright]
 * @author ianlin
 * @date 1:15:45 PM
 */

package com.personal.util.mock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ianlin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.FIELD
})
public @interface Mocked {
    MockInfo[] infos();
}
