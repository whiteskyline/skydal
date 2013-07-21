/**
 * [Copyright]
 * @author ianlin
 * @date 5:44:49 PM
 */

package com.horizon.dal.config;

import com.horizon.dal.skydal.util.Validatable;

import org.apache.commons.lang.Validate;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author ianlin
 */
@Element(name = "pairs")
public class Pairs implements Validatable {
    @Attribute(name = "name")
    private String name;
    @Attribute(name = "range")
    private int range;
    @ElementList(inline = true)
    private List<Pair> pairs;

    /**
     * 获取聚合的Pair列表
     * 
     * @return
     */
    public TreeMap<Integer, Pair> getPairs() {
        validate();
        ArrayList<Pair> preResult = new ArrayList<Pair>(getRange());
        for (Pair pair : getPairsConfig()) {
            preResult.addAll(pair.getDecodedPairs());
        }

        // 排序检查range覆盖情况
        TreeMap<Integer, Pair> pairs = new TreeMap<Integer, Pair>();
        for (Pair pair : preResult) {
            pairs.put(pair.getKey(), pair);
        }

        // 取出，检查key的覆盖情况
        Validate.notEmpty(pairs, "pairs should not be empty!");
        Validate.isTrue(pairs.firstKey() == 0, "key set should cover 0 - range!");
        Validate.isTrue(pairs.lastKey() == getRange(), "key set should cover 0 - range!");
        Set<Integer> keySet = pairs.keySet();
        int preKey = -1;
        for (Integer currentKey : keySet) {
            Validate.isTrue(preKey == currentKey - 1);
            preKey = currentKey;
        }

        return pairs;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the range
     */
    public int getRange() {
        return range;
    }

    /**
     * @param range the range to set
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     * @return the pairs
     */
    protected List<Pair> getPairsConfig() {
        return pairs;
    }

    /**
     * @param pairs the pairs to set
     */
    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }

    /*
     * (non-Javadoc)
     * @see com.horizon.dal.skydal.util.Validatable#validate()
     */
    public void validate() {

        Validate.isTrue(range > 0, "range cannot be 0");

        for (Pair pair : getPairsConfig()) {
            pair.validate();
        }
    }

}
