/**
 * [Copyright]
 * @author ianlin
 * @date 5:47:20 PM
 */

package com.horizon.dal.config;

import com.horizon.dal.skydal.util.DalConstants;
import com.horizon.dal.skydal.util.Validatable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ianlin 该配置首先查看是否有key, 然后才查看start与end
 */
@Element(name = "pair")
public class Pair implements Validatable {

    private static final Logger logger = LoggerFactory.getLogger(Pair.class);

    @Attribute(required = false)
    private int start = DalConstants.UN_INIT_VALUE_INT;
    @Attribute(required = false)
    private int end = DalConstants.UN_INIT_VALUE_INT;
    @Attribute(required = false)
    private int key = DalConstants.UN_INIT_VALUE_INT;
    @Attribute
    private String value;

    /**
     * <pre>
     * please validate this object first, then call getDecodedPairs will get right info
     * </pre>
     * 
     * @return will return decoded pairs list
     */
    public List<Pair> getDecodedPairs() {
        if (key != DalConstants.UN_INIT_VALUE_INT) {
            Arrays.asList(this);
        }

        Validate.isTrue(start != DalConstants.UN_INIT_VALUE_INT && end != DalConstants.UN_INIT_VALUE_INT && start <= end,
            "invalid start and end value!");
        ArrayList<Pair> result = new ArrayList<Pair>(end - start + 1);
        for (int idx = start; idx < end; idx++) {
            Pair decodedPair = new Pair();
            decodedPair.setKey(idx);
            decodedPair.setValue(value);
        }
        return result;

    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public int getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * @return the key
     */
    public int getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        Validate.isTrue(StringUtils.isNotBlank(value));
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * @see com.horizon.dal.skydal.util.Validatable#validate()
     */
    public void validate() {
        if (key != DalConstants.UN_INIT_VALUE_INT) {
            if (start != DalConstants.UN_INIT_VALUE_INT || end != DalConstants.UN_INIT_VALUE_INT) {
                logger.warn("key and start and end index should not be both set!");
            }
            return;
        }

        Validate.isTrue(start != DalConstants.UN_INIT_VALUE_INT && end == DalConstants.UN_INIT_VALUE_INT,
            "start and end value should be both set!");
        Validate.isTrue(start <= end, "invalid start value!");
        Validate.isTrue(StringUtils.isNotBlank(value), "invalid value!");

    }
}
