/**
 * [Copyright]
 * @author ianlin
 * @date 2:24:07 PM
 */

package com.horizon.dal.datasource;

/**
 * @author ianlin 数据源配置
 */
public class DataSourceConfig {
    private String url;
    private String usr;
    private String pwd;

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
