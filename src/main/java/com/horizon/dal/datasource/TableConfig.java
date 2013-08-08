/**   
 * @Title: 						TableConfig.java 
 * @Package				com.horizon.dal.datasource 
 * @Description:			表的配置
 * @author					ming.horizon@gmail.com
 * @date						Aug 4, 2013 2:37:22 PM 
 * @version					V1.0   
 */

package com.horizon.dal.datasource;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 * @author ianlin
 * 
 *         <pre>
 * 表的范例配置，和使用什么列进行hash的配置不要放在这里面
 * <table view="tablename"
 *             mod="10000">
 *             <partition id="0" start="0" end="99" tablename="tablename_0" connection-ref="connection_0"/>
 *             <partition id="1" start="100" end="199" tablename="tablename_1" connection-ref="connection_1"/>
 *             ...
 *             <partition id="9" start="900" end="999" tablename="tablename_9" connection-ref="connection_2"/>
 * </table>
 * </pre>
 * 
 *         对于如上的配置，说明如下:
 *         <ul>
 *         <li>mod表示对于key column进行的hash计算结果使用这个进行取模，取摸之后，和partition的start和end进行匹配</li>
 *         <li>view是表示语句当中使用的表的名称，这个名称在查找相应的Table配置的时候进行使用，和最后的partition中的table可以没有直接的映射关系</li>
 *         <li>所有的partition中的tablename可以不使用一样的规则</li>
 *         <li>connection-ref用来映射最终需要使用的DataSource</li>
 *         </ul>
 *         对于如上的配置方法，需要改进的有如下几个方面
 *         <ul>
 *         <li>tablename应该有可以生成的办法</li>
 *         <li>partition应该可以有连续生成的办法</li>
 *         <li>connection-ref应该可以使用正则表达式来进行生成</li>
 *         </ul>
 */
@Element(name = "table")
public class TableConfig {
    @Attribute(name = "view", required = true)
    private String view;
    @Attribute(name = "mod", required = true)
    private int mod;

    @ElementList(inline = true, entry = "partition")
    List<PartitionConfig> partitions;

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public int getMod() {
        return mod;
    }

    public void setMod(int mod) {
        this.mod = mod;
    }

    public List<PartitionConfig> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<PartitionConfig> partitions) {
        this.partitions = partitions;
    }

}
