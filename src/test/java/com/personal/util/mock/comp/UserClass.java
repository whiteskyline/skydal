/**
 * [Copyright]
 * @author ianlin
 * @date 3:34:57 PM
 */

package com.personal.util.mock.comp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 模拟DAO的使用者
 * 
 * @author ianlin
 */
@Service
public class UserClass {
    @Autowired
    public BaseClass base;

    /**
     * @return the base
     */
    public BaseClass getBase() {
        return base;
    }

    /**
     * @param base the base to set
     */
    public void setBase(BaseClass base) {
        this.base = base;
    }

    /**
     * 获得base的类型
     * 
     * @return
     */
    public String getValue() {
        return base.getClass().toString();
    }

}
