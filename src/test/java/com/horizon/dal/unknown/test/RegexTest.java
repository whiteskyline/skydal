/**
 * [Copyright]
 * @author ianlin
 * @date 9:31:23 AM
 */

package com.horizon.dal.unknown.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ianlin 对正则表达式的内容进行测试
 */
public class RegexTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegexTest.class);

    @Test
    public void testRegex() throws Exception {
        Pattern pattern = Pattern.compile("\\$\\{([\\w\\d\\_]*)\\}");
        Matcher matcher = pattern.matcher("abcdefg${usr1},${pwd_}");
        // if (matcher.find()) {
        // LOGGER.info("find");
        // LOGGER.info(matcher.group(0));
        // LOGGER.info(matcher.group(1));
        // LOGGER.info("" + matcher.groupCount());
        // }

        // if (matcher.matches()) {
        // LOGGER.info("matched");
        // }

        Map<String, String> map = new HashMap<String, String>();
        map.put("usr1", "uuu");
        map.put("pwd_", "ppp");

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            LOGGER.info(matcher.group(1));
            String value = map.get(matcher.group(1));
            matcher.appendReplacement(sb, "$1:" + value);
            LOGGER.info(sb.toString());
        }

        matcher.appendTail(sb);

        LOGGER.info(sb.toString());
    }
}
