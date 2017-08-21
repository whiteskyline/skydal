/**   
 * @Title: 						PartitionConfig.java 
 * @Package				com.horizon.dal.datasource 
 * @Description:			TODO(用一句话描述该文件做什么) 
 * @author					ming.horizon@gmail.com
 * @date						Aug 4, 2013 3:19:32 PM 
 * @version					V1.0   
 */

package com.horizon.dal.datasource;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * @author ianlin 对于partition的配置部分进行设计，关于partition的说明，请看TableConfig中的范例
 */
@Element(name = "partition")
public class PartitionConfig {
    @Attribute(name = "id")
    private int id;
    @Attribute(name = "start")
    private int start;
    @Attribute(name = "end")
    private int end;
    @Attribute(name = "tablename")
    private String tablename;
    @Attribute(name = "connection-ref")
    private String connectionRef;

    /**
     * 判断某个hashid是否能够使用到这里来 判断的依据是hash_id是否能够落到start与end进行限制的空间里
     * 
     * @return
     */
    public boolean match(int hash_id) {
        return start <= hash_id && hash_id <= end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getConnectionRef() {
        return connectionRef;
    }

    public void setConnectionRef(String connectionRef) {
        this.connectionRef = connectionRef;
    }

}
