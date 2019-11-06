package test.com.util;

import com.util.Tool;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * Tool Tester.
 *
 * @author <yubing.zhu>
 * @version 1.0
 * @since
 */
public class ToolTest {
    private Tool tool;

    @Before
    public void before() throws Exception {
        tool = new Tool();

    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getRandom()
     */
    @Test
    public void testGetRandom() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getClientIp(HttpServletRequest request)
     */
    @Test
    public void testGetClientIp() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: getUUID()
     */
    @Test
    public void testGetUUID() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * 测试md5加密串
     * Method: MD5(String str)
     */
    @Test
    public void testMD5() throws Exception {
        String s = "123456";
        String md5String = "e10adc3949ba59abbe56e057f20f883e";
        Assert.assertEquals(md5String, tool.MD5(s));
    }


} 
