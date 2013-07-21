/**
 * [Copyright]
 * @author ianlin
 * @date 2:24:07 PM
 */

package com.horizon.dal.datasource;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * @author ianlin 数据源配置
 */
@Element(name = "connection")
public class DataSourceConfig {
    @Attribute
    private String name;
    @Attribute
    private String url;
    @Attribute
    private String usr;
    @Attribute
    private String pwd;

    // 这个字段暂时不进行实现
    @Attribute(required = false)
    private String role;

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

}
