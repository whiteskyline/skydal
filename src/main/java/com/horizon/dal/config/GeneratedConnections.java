/**
 * [Copyright]
 * @author ianlin
 * @date 10:41:42 PM
 */

package com.horizon.dal.config;

import com.horizon.dal.datasource.DataSourceConfig;
import com.horizon.dal.skydal.anno.NeedDecode;
import com.horizon.dal.skydal.excp.DalConfigException;
import com.horizon.dal.skydal.util.ReflectUtil;
import com.horizon.dal.skydal.util.ReflectUtil.ReflectException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

/**
 * :TODO 將這個類的一部分屬性從Connection进行继承，这样会更容易维护
 * 
 * @author ianlin 生成的连接配置数据
 */
@Element(name = "genConnections")
public final class GeneratedConnections {

    private Logger LOGGER = LoggerFactory.getLogger(GeneratedConnections.class);
    private static final String EXPRESSION = "\\$\\{([\\w\\d\\_]*)\\}";
    private static final Pattern PATTERN = Pattern.compile(EXPRESSION); // :TODO pattern貌似不是线程安全的
    private static final int TARGET_GROUP_IDX = 1;
    private static final String ID_KEY = "id";

    @Attribute
    private int idxRange;

    @Attribute
    @NeedDecode
    private String name;

    @Attribute
    @NeedDecode
    private String usr;

    @Attribute
    @NeedDecode
    private String pwd;

    @Attribute
    @NeedDecode
    private String url;

    /**
     * @return the idxRange
     */
    public int getIdxRange() {
        return idxRange;
    }

    /**
     * @param idxRange the idxRange to set
     */
    public void setIdxRange(int idxRange) {
        this.idxRange = idxRange;
    }

    /**
     * @return the usr
     */
    public String getUsr() {
        return usr;
    }

    /**
     * @param usr the usr to set
     */
    public void setUsr(String usr) {
        this.usr = usr;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    // 为了扩展性，这个地方大量使用了反射，会引起一定的性能差异，但是由于是框架载入的过程中使用的，因此，没有必要进行特别的优化
    // :TODO 查询这个部分对于框架载入的性能影响
    /**
     * 生成的最终数据源是按照idx进行排序的
     * 
     * @param keySets
     * @return 这个部分一定会返回一个非Null的链表
     * @throws DalConfigException
     */
    public List<DataSourceConfig> generateConnections(Map<String, TreeMap<Integer, Pair>> keySets) throws DalConfigException {
        if (idxRange < 0) {
            throw new DalConfigException("invalid idx range");
        }

        List<DataSourceConfig> configList = new ArrayList<DataSourceConfig>(idxRange);

        for (int idx = 0; idx < idxRange; idx++) {

            // 首先获取當前Config各个字段的值，然后进行正则表达式匹配
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {

                // 對於需要進行解析并且生成的属性，我们进行正则表达式匹配，并且生成最终的值
                String targetFieldName = null;
                DataSourceConfig config = null;
                Field targetField = null;
                String valueExpre = null;

                try {

                    NeedDecode needDecodeAnno = field.getAnnotation(NeedDecode.class);
                    if (null == needDecodeAnno || !needDecodeAnno.need()) {
                        // 这个属性不需要进行解析设置
                        continue;
                    }

                    // 獲取其中的正則表達式的值
                    Object objValue = field.get(this);

                    if (null == objValue) {
                        LOGGER.error("need decode value:{} should not be null.", field.getName());
                        throw new DalConfigException("need decode value should not be null");
                    } else if (!(objValue instanceof String)) {
                        Assert.assertTrue("should decode value must be string.", false);
                    }

                    valueExpre = (String) objValue;
                    if (StringUtils.isBlank(valueExpre)) {
                        throw new DalConfigException("need decode value should not be blank.");
                    }

                    // 确定要设置的目标属性
                    config = new DataSourceConfig();
                    targetFieldName = StringUtils.isBlank(needDecodeAnno.targetName()) ? field.getName() : needDecodeAnno.targetName();
                    targetField = DataSourceConfig.class.getDeclaredField(targetFieldName);

                } catch (IllegalArgumentException e) {
                    // this never happen.
                    LOGGER.error("this error should never happen.", e);
                    throw e;
                } catch (IllegalAccessException e) {
                    // this never happer.
                    LOGGER.error("this error should never happen.", e);
                    throw new RuntimeException("cause should never happer.", e);
                } catch (SecurityException e) {
                    LOGGER.error("security exception.", e);
                    throw new RuntimeException("security exception", e);
                } catch (NoSuchFieldException e) {
                    LOGGER.error("cannot find target field:{}", targetFieldName, e);
                    throw new DalConfigException("cannot find target field:" + targetFieldName, e);
                }

                try {
                    ReflectUtil.setValue(config, targetField, getDecodedValue(valueExpre, idx, keySets));
                } catch (ReflectException e) {
                    LOGGER.error("cannot set value", e);
                    throw new DalConfigException(e);
                }

                configList.add(config);

            }

        }
        return configList;
    }

    /**
     * 獲取編譯之後的正則表達式的結果值
     * 
     * @param expression
     * @param idx
     * @param keySets
     * @return 最后的结果值不可能是null，但是可以是blank，需要的用户自己进行检查
     * @throws DalConfigException
     */
    protected String getDecodedValue(String valueExpe, int idx, Map<String, TreeMap<Integer, Pair>> keySets) throws DalConfigException {
        Validate.isTrue(StringUtils.isNotBlank(valueExpe), "value expressio cannot be blank");
        Validate.isTrue(null != keySets, "key set cannot be null");

        Matcher matcher = PATTERN.matcher(valueExpe);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(TARGET_GROUP_IDX);
            // 根据
            matcher.appendReplacement(buffer, getValueForId(idx, key, keySets));
        }

        matcher.appendTail(buffer);
        return buffer.toString();

    }

    /**
     * 獲取對應的id所對應的映射值, 如果所要的是id的值，将返回对应的id的string，不查询keySets
     * 
     * @param id
     * @param key
     * @param keySets
     * @return
     * @throws DalConfigException
     */
    protected String getValueForId(int id, String key, Map<String, TreeMap<Integer, Pair>> keySets) throws DalConfigException {
        Validate.isTrue(id >= 0, "id should not be smaller than 0");
        Validate.isTrue(StringUtils.isNotBlank(key), "key cannot be null");

        if (ID_KEY.equals(key)) {
            return String.valueOf(id);
        }

        TreeMap<Integer, Pair> idMap = null;
        if (null == (idMap = keySets.get(key))) {
            LOGGER.error("need for key set named:{}", key);
            throw new DalConfigException("keyset cannot get be queryed");
        }

        Pair pair = idMap.get(id);
        if (null == pair || StringUtils.isBlank(pair.getValue())) {
            LOGGER.error("{} for id:{} cannot be found or is blank.", key, id);
            throw new DalConfigException();
        }

        return pair.getValue();

    }
}
