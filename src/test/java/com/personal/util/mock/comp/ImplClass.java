/**
 * [Copyright]
 * @author ianlin
 * @date 8:43:20 PM
 */

package com.personal.util.mock.comp;

import org.springframework.stereotype.Service;

/**
 * @author ianlin
 */
@Service
public class ImplClass implements BaseClass {
    public ImplClass() {
        System.err.println("Impl Class is created.");
    }
}
