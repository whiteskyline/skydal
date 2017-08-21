/**   
* @Title: 						MapUtil.java 
* @Package				com.horizon.dal.skydal.util 
* @Description:			与Map的初始化等等相关的Util代码
* @author					ming.horizon@gmail.com
* @date						Aug 3, 2013 9:32:16 PM 
* @version					V1.0   
*/
package com.horizon.dal.skydal.util;

/**
 * @author ianlin
 *
 */
public class MapUtil {
    
    private static final int  DEF_MAP_CAPACITY_FOR_BAD = 10;   // 对于进来的错误的map的容量，那么直接返回10
    private static final float DEF_MAP_CAPACITY_RATIO = 1.4f; // 1.4倍的存儲量，儘量減少碰撞
    
    /**
     * 根据最后map的完整大小，推算应该获得的最佳的map容量
     * @param indicatedFullSize
     * @return
     */
    public static int getSuggestedMapSize(int indicatedFullSize) {
        if (indicatedFullSize <= 0) {
            return DEF_MAP_CAPACITY_FOR_BAD;
        }
        
        // 留下足够的空间富裕，减少碰撞可能发生的几率
        return (int) (DEF_MAP_CAPACITY_RATIO * indicatedFullSize);
    }
}
