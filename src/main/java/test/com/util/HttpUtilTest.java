package test.com.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.util.HttpUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * HttpUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class HttpUtilTest {
    private HttpUtil http;

    @Before
    public void before() throws Exception {
        http = new HttpUtil();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: doPost(String url, String parameterData)
     */
    @Test
    public void testDoPostForUrlParameterData() throws Exception {
        String url = "http://httpbin.org/post";
        String data = "user=yubing.zhu";
        String str;
        str = http.doPost(url, data);

        JSONObject jsonObject = JSON.parseObject(str);
        String res = (String) JSONPath.eval(jsonObject, "$.form.user");

        Assert.assertEquals("yubing.zhu", res);

    }

    /**
     * Method: doPost(String url, Object json)
     */
    @Test
    public void testDoPostForUrlJson() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: doGet(String url)
     */
    @Test
    public void testDoGet() throws Exception {
        String url = "http://httpbin.org/get?user=yubing.zhu";
        String str;
        str = http.doGet(url);

        JSONObject jsonObject = JSON.parseObject(str);
        String res = (String) JSONPath.eval(jsonObject, "$.args.user");

        Assert.assertEquals("yubing.zhu", res);
    }
} 
