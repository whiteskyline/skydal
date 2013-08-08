/**
 * [Copyright]
 * @author ianlin
 * @date 4:36:30 PM
 */

package com.horizon.dal.test.util;

import com.horizon.dal.skydal.excp.DalConfigException;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author ianlin
 */
public class ConfigUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);

    public static <T> T getFromFile(String filename, T target) throws DalConfigException {
        Serializer selializer = new Persister();
        File file = new File(filename);
        if (!file.exists()) {
            LOGGER.error("fils {} does not exist.", file.getAbsolutePath());
            throw new DalConfigException("file does not exist:" + filename);
        }
        try {
            selializer.read(target, file);
            return target;
        } catch (Exception e) {
            LOGGER.error("cannot decode file:{}", filename);
            throw new DalConfigException("decode object error.", e);
        }
    }
}
