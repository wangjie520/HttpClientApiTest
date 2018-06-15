package com.ccybase.cases;

import com.ccybase.config.TestConfig;
import com.ccybase.model.VcTest;
import com.ccybase.util.GetDataUtil;
import com.ccybase.util.GetWithParam;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Iterator;

public class QueryVcTestCase {
   //如果不执行beforeClass,则跟登录使用同一个httpclient，不用再设置cookies信息
    @BeforeClass
    public void beforeClass(){
            TestConfig.defaultHttpClient=new DefaultHttpClient();
            TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
    }
    @Test(dataProvider = "vcTestData")
    public  void testQueryVc(VcTest vcTest) throws IOException {

        //发起请求，获取响应
        String response=getResult(vcTest);

        //响应断言
        JSONObject rj=new JSONObject(response);
        String code=rj.getInt("code")+"";
        Assert.assertEquals(code,vcTest.getExpected());
}
    @DataProvider(name="vcTestData")
    public Iterator<Object[]> QueryVcData() throws IOException {
        return GetDataUtil.GetDataProvider("vcTest");
    }
    private String getResult(VcTest vcTest) throws IOException {

        GetWithParam get=new GetWithParam();
        get.setTESTURL(TestConfig.QueryVcUrl);
        get.addParam("vc_id",vcTest.getVcId());
        String result=get.getWithParamResult();
        return result;
    }
    }


