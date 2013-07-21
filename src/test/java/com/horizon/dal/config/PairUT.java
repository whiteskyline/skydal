/**
 * [Copyright]
 * @author ianlin
 * @date 6:01:46 PM
 */

package com.horizon.dal.config;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ianlin
 */
public class PairUT {

    /**
     * 测试正常的range型pair的生成
     * 
     * @throws Exception
     */
    @Test
    public void pairGenerateTest() throws Exception {

        Pair pair = new Pair();
        pair.setStart(0);
        pair.setEnd(4);
        pair.setValue("value");

        List<Pair> pairs = pair.getDecodedPairs();
        Assert.assertEquals(5, pairs.size());
        for (int idx = 0; idx < pairs.size(); idx++) {
            pair = pairs.get(idx);
            Assert.assertEquals(idx, pair.getKey());
            Assert.assertEquals("value", pair.getValue());
        }

        // 测试边缘条件生成
        pair = new Pair();
        pair.setStart(0);
        pair.setEnd(0);
        pair.setValue("value");
        pairs = pair.getDecodedPairs();
        Assert.assertEquals(1, pairs.size());
    }

    /**
     * 测试索引是负数的情况
     * 
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void pairGenerateInvalidIdxNegativeTest() throws Exception {
        Pair pair = new Pair();
        pair.setStart(-1);
        pair.setEnd(3);

        pair.getDecodedPairs();
    }

    /**
     * 测试开始索引和结束索引不正确的情况
     * 
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void pairGenerateInvalidStartNegativeTest() throws Exception {
        Pair pair = new Pair();
        pair.setStart(10);
        pair.setEnd(8);
        pair.setValue("value");
        pair.getDecodedPairs();

    }

    /**
     * 测试正常的生成条件的情况
     * 
     * @throws Exception
     */
    @Test
    public void pairsGenerateTest() throws Exception {

        Pairs pairs = new Pairs();

        pairs.setName("passwd");
        pairs.setRange(10);

        ArrayList<Pair> pairList = new ArrayList<Pair>();

        Pair pair = new Pair();
        pair.setStart(2);
        pair.setEnd(8);
        pair.setValue("valueof2");
        pairList.add(pair);

        pair = new Pair();
        pair.setStart(0);
        pair.setEnd(1);
        pair.setValue("valueof0");
        pairList.add(pair);

        pair = new Pair();
        pair.setStart(9);
        pair.setEnd(9);
        pair.setValue("valueof9");
        pairList.add(pair);

        pairs.setPairs(pairList);

        pairs.getPairs();

    }

    /**
     * 测试区域无法完全覆盖的情况
     * 
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void pairsGenerateUncoverNegativeTest() throws Exception {
        Pairs pairs = new Pairs();

        pairs.setName("name");
        pairs.setRange(5);

        ArrayList<Pair> pairList = new ArrayList<Pair>();
        Pair pair = new Pair();
        pair.setStart(0);
        pair.setEnd(1);
        pair.setValue("value");
        pairList.add(pair);

        pair = new Pair();
        pair.setStart(3);
        pair.setEnd(4);
        pair.setValue("value");
        pairList.add(pair);

        pairs.setPairs(pairList);

        pairs.getPairs();

    }

    /**
     * 测试区域的前端无法覆盖的情况
     * 
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void pairsGenerateFrontUncoverNagetiveTest() throws Exception {
        Pairs pairs = new Pairs();

        pairs.setName("name");
        pairs.setRange(5);

        ArrayList<Pair> pairList = new ArrayList<Pair>();
        Pair pair = new Pair();
        pair.setStart(1);
        pair.setEnd(1);
        pair.setValue("value");
        pairList.add(pair);

        pair = new Pair();
        pair.setStart(2);
        pair.setEnd(4);
        pair.setValue("value");
        pairList.add(pair);

        pairs.setPairs(pairList);

        pairs.getPairs();
    }

    /**
     * 測試區域的後端無法覆蓋的情況
     * 
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void pairsGenerateBackUncoverNegativeTest() throws Exception {
        Pairs pairs = new Pairs();

        pairs.setName("name");
        pairs.setRange(5);

        ArrayList<Pair> pairList = new ArrayList<Pair>();
        Pair pair = new Pair();
        pair.setStart(0);
        pair.setEnd(1);
        pair.setValue("value");
        pairList.add(pair);

        pair = new Pair();
        pair.setStart(2);
        pair.setEnd(3);
        pair.setValue("value");
        pairList.add(pair);

        pairs.setPairs(pairList);

        pairs.getPairs();
    }

    /**
     * 測試超出覆蓋區域的情況
     * 
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void pairGenerateOverRangeNegativeTest() throws Exception {
        Pairs pairs = new Pairs();

        pairs.setName("name");
        pairs.setRange(5);

        ArrayList<Pair> pairList = new ArrayList<Pair>();
        Pair pair = new Pair();
        pair.setStart(0);
        pair.setEnd(1);
        pair.setValue("value");
        pairList.add(pair);

        pair = new Pair();
        pair.setStart(2);
        pair.setEnd(8);
        pair.setValue("value");
        pairList.add(pair);

        pairs.setPairs(pairList);

        pairs.getPairs();
    }

}
